package com.github.forgetmelody.contextutil.providers.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import com.github.forgetmelody.contextutil.providers.positions.PositionProvider;
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
        PositionProvider position,
        HolderSet<EntityFunction> functions
) implements EntityProvider {
    public static final Codec<Summon> INLINE_CODEC = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.byNameCodec().xmap(Summon::new, Summon::entityType);
    public static final MapCodec<Summon> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(Summon::entityType),
            PositionProvider.DIRECT_CODEC.optionalFieldOf("position", PositionProvider.origin()).forGetter(Summon::position),
            EntityFunction.LIST_CODEC.optionalFieldOf("functions", HolderSet.empty()).forGetter(Summon::functions)
    ).apply(instance, Summon::new));

    private Summon(EntityType<?> entityType) {
        this(entityType, PositionProvider.origin(), HolderSet.empty());
    }

    @Override
    public @Nullable Entity get(LootContext context) {
        ServerLevel level = context.getLevel();
        BlockPos blockPos = this.position.getBlockPos(context);
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

    public static class Builder {
        private final EntityType<?> entityType;
        private PositionProvider position = PositionProvider.origin();
        private HolderSet<EntityFunction> functions = HolderSet.empty();

        public Builder(EntityType<?> entityType) {
            this.entityType = entityType;
        }

        public Builder position(PositionProvider position) {
            this.position = position;
            return this;
        }

        public Builder functions(EntityFunction... functions) {
            this.functions = HolderSet.direct(Arrays.stream(functions).map(Holder::direct).toList());
            return this;
        }

        public Builder functions(HolderSet<EntityFunction> functions) {
            this.functions = functions;
            return this;
        }

        @SafeVarargs
        public final Builder functions(Holder<EntityFunction>... functions) {
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
