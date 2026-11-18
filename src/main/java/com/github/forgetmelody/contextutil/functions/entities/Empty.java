package com.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public record Empty() implements EntityFunction {
    public static final Empty INSTANCE = new Empty();
    public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public void apply(Entity entity, LootContext context) {

    }

    @Override
    public MapCodec<? extends EntityFunction> codec() {
        return MAP_CODEC;
    }
}
