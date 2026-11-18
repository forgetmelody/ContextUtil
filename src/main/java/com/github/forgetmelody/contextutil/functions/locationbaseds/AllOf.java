package com.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public record AllOf(List<LocationBasedFunction> functions) implements LocationBasedFunction {
    public static final MapCodec<AllOf> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LocationBasedFunction.DIRECT_CODEC.listOf().fieldOf("functions").forGetter(AllOf::functions)
    ).apply(instance, AllOf::new));

    @Override
    public void apply(Vec3 position, LootContext context) {
        for (LocationBasedFunction function : this.functions) {
            function.apply(position, context);
        }
    }

    @Override
    public MapCodec<? extends LocationBasedFunction> codec() {
        return MAP_CODEC;
    }
}
