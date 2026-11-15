package io.github.forgetmelody.contextutil.providers.entities;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public record ThisEntity() implements ContextEntityProvider {
    public static final ThisEntity INSTANCE = new ThisEntity();
    public static final MapCodec<ThisEntity> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public @Nullable Entity get(LootContext context) {
        return context.getOptionalParameter(LootContextParams.THIS_ENTITY);
    }

    @Override
    public MapCodec<? extends ContextEntityProvider> codec() {
        return MAP_CODEC;
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.THIS_ENTITY);
    }
}
