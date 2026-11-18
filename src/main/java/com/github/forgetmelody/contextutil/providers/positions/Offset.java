package com.github.forgetmelody.contextutil.providers.positions;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record Offset(Vec3 offset, PositionProvider position) implements PositionProvider {

    @Override
    public @Nullable Vec3 get(LootContext context) {
        return null;
    }

    @Override
    public MapCodec<? extends PositionProvider> codec() {
        return null;
    }
}
