package io.github.forgetmelody.contextutil.providers.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.functions.entities.ContextEntityFunction;
import io.github.forgetmelody.contextutil.providers.locations.ContextLocationProvider;
import io.github.forgetmelody.contextutil.providers.locations.Origin;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record Summon(
        EntityType<?> entityType,
        Optional<ContextLocationProvider> position,
        List<ContextEntityFunction> functions
) implements ContextEntityProvider {
    public static final Codec<Summon> INLINE_CODEC = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.byNameCodec().xmap(Summon::new, Summon::entityType);
    public static final MapCodec<Summon> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(Summon::entityType),
            ContextLocationProvider.DIRECT_CODEC.optionalFieldOf("position").forGetter(Summon::position),
            ContextEntityFunction.DIRECT_CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(Summon::functions)
    ).apply(instance, Summon::new));

    private Summon(EntityType<?> entityType) {
        this(entityType, Optional.empty(), List.of());
    }

    @Override
    public @Nullable Entity get(LootContext context) {
        ServerLevel level = context.getLevel();
        BlockPos blockPos = this.position
                .map(provider -> provider.getBlockPos(context))
                .orElseGet(() -> Origin.INSTANCE.getBlockPos(context));
        if (blockPos != null) {
            return this.entityType.spawn(
                    level,
                    entity -> {
                        for (ContextEntityFunction function : this.functions) {
                            function.apply(entity, context);
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

    public static class Builder implements ContextEntityProvider.Builder {
        private final EntityType<?> entityType;
        private ContextLocationProvider position = null;
        private List<ContextEntityFunction> functions = List.of();

        public Builder(EntityType<?> entityType) {
            this.entityType = entityType;
        }

        public Builder position(ContextLocationProvider position) {
            this.position = position;
            return this;
        }

        public Builder position(ContextEntityFunction... functions) {
            this.functions = Arrays.asList(functions);
            return this;
        }

        @Override
        public ContextEntityProvider build() {
            return new Summon(
                    this.entityType,
                    Optional.ofNullable(this.position),
                    this.functions
            );
        }
    }
}
