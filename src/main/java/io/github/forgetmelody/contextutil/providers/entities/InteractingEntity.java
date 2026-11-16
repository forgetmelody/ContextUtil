package io.github.forgetmelody.contextutil.providers.entities;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public record InteractingEntity() implements EntityProvider {
    public static final InteractingEntity INSTANCE = new InteractingEntity();
    public static final MapCodec<InteractingEntity> MAP_CODEC = MapCodec.unit(InteractingEntity.INSTANCE);

    @Override
    public @Nullable Entity get(LootContext context) {
        return context.getOptionalParameter(LootContextParams.INTERACTING_ENTITY);
    }

    @Override
    public MapCodec<? extends EntityProvider> codec() {
        return MAP_CODEC;
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.INTERACTING_ENTITY);
    }
}
