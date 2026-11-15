package io.github.forgetmelody.contextutil.providers.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.functions.entities.ContextEntityFunction;
import io.github.forgetmelody.contextutil.providers.locations.ContextLocationProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public record Summon(
        EntityType<?> entityType,
        ContextLocationProvider position,
        HolderSet<ContextEntityFunction> functions
) implements ContextEntityProvider {
    public static final Codec<Summon> INLINE_CODEC = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.byNameCodec().xmap(Summon::new, Summon::entityType);
    public static final MapCodec<Summon> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(Summon::entityType),
            ContextLocationProvider.DIRECT_CODEC.optionalFieldOf("position", ContextLocationProvider.origin()).forGetter(Summon::position),
            ContextEntityFunction.LIST_CODEC.optionalFieldOf("functions", HolderSet.empty()).forGetter(Summon::functions)
    ).apply(instance, Summon::new));

    private Summon(EntityType<?> entityType) {
        this(entityType, ContextLocationProvider.origin(), HolderSet.empty());
    }

    @Override
    public @Nullable Entity get(LootContext context) {
        ServerLevel level = context.getLevel();
        BlockPos blockPos = this.position.getBlockPos(context);
        if (blockPos != null) {
            return this.entityType.spawn(
                    level,
                    entity -> {
                        for (Holder<ContextEntityFunction> function : this.functions) {
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
    public MapCodec<? extends ContextEntityProvider> codec() {
        return MAP_CODEC;
    }

    public static class Builder {
        private final EntityType<?> entityType;
        private ContextLocationProvider position = ContextLocationProvider.origin();
        private HolderSet<ContextEntityFunction> functions = HolderSet.empty();

        public Builder(EntityType<?> entityType) {
            this.entityType = entityType;
        }

        public Builder position(ContextLocationProvider position) {
            this.position = position;
            return this;
        }

        public Builder functions(ContextEntityFunction... functions) {
            this.functions = HolderSet.direct(Arrays.stream(functions).map(Holder::direct).toList());
            return this;
        }

        public Builder functions(HolderSet<ContextEntityFunction> functions) {
            this.functions = functions;
            return this;
        }

        @SafeVarargs
        public final Builder functions(Holder<ContextEntityFunction>... functions) {
            this.functions = HolderSet.direct(Arrays.asList(functions));
            return this;
        }

        public Summon build() {
            return new Summon(
                    this.entityType,
                    this.position,
                    this.functions
            );
        }
    }
}
