package com.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

public record Empty() implements LocationBasedFunction {
    public static final Empty INSTANCE = new Empty();
    public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public void apply(Vec3 position, LootContext context) {

    }

    @Override
    public MapCodec<? extends LocationBasedFunction> codec() {
        return MAP_CODEC;
    }
}
