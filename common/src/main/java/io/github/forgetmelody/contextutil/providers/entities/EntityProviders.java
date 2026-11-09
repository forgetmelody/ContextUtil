package io.github.forgetmelody.contextutil.providers.entities;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunctions;
import io.github.forgetmelody.contextutil.providers.locations.LocationProvider;
import io.github.forgetmelody.contextutil.providers.locations.LocationProviders;
import io.github.forgetmelody.contextutil.providers.locations.Origin;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public final class EntityProviders {
    public static final Codec<EntityProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<EntityProvider> dispatched = BuiltInRegistries.ENTITY_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(EntityProvider::codec, Function.identity());
        return Codec.either(Constant.INLINE_CODEC, dispatched).xmap(Either::unwrap, EntityProviders::wrap);
    });
    public static final Codec<EntityProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    private EntityProviders() {
    }

    private static Either<Constant, EntityProvider> wrap(EntityProvider provider) {
        return provider instanceof Constant constant ? Either.left(constant) : Either.right(provider);
    }

    private record Empty() implements EntityProvider {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public @Nullable Entity get(LootContext context) {
            return null;
        }

        @Override
        public MapCodec<? extends EntityProvider> codec() {
            return MAP_CODEC;
        }
    }

    private record Constant(
            EntityType<?> entityType,
            Optional<LocationProvider> position,
            HolderSet<EntityFunction> functions
    ) implements EntityProvider {
        public static final Codec<Constant> INLINE_CODEC = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.byNameCodec().xmap(Constant::new, Constant::entityType);
        public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(Constant::entityType),
                LocationProviders.DIRECT_CODEC.optionalFieldOf("position").forGetter(Constant::position),
                EntityFunctions.LIST_CODEC.optionalFieldOf("functions", HolderSet.empty()).forGetter(Constant::functions)
        ).apply(instance, Constant::new));

        private Constant(EntityType<?> entityType) {
            this(entityType, Optional.empty(), HolderSet.empty());
        }

        @Override
        public @Nullable Entity get(LootContext context) {
            ServerLevel level = context.getLevel();
            BlockPos blockPos = this.position
                    .map(provider -> provider.getBlockPos(context))
                    .orElseGet(() -> Origin.EMPTY.getBlockPos(context));
            if (blockPos != null) {
                return this.entityType.spawn(
                        level,
                        entity -> {
                            for (Holder<EntityFunction> function : this.functions) {
                                function.value().apply(entity, context);
                            }
                        },
                        blockPos,
                        EntitySpawnReason.TRIGGERED,
                        false,
                        false
                );
            }
            return null;
        }

        @Override
        public MapCodec<? extends EntityProvider> codec() {
            return MAP_CODEC;
        }
    }
}
