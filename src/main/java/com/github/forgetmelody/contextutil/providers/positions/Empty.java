package com.github.forgetmelody.contextutil.providers.positions;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

public record Empty() implements PositionProvider {
    public static final Empty INSTANCE = new Empty();
    public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public Vec3 get(LootContext context) {
        return Vec3.ZERO;
    }

    @Override
    public MapCodec<? extends PositionProvider> codec() {
        return MAP_CODEC;
    }
}
