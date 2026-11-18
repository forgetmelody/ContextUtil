package com.github.forgetmelody.contextutil.providers.positions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

public record Constant(Vec3 value) implements PositionProvider {
    public static final Codec<Constant> INLINE_CODEC = Vec3.CODEC.xmap(Constant::new, Constant::value);
    public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Vec3.CODEC.fieldOf("value").forGetter(Constant::value)
    ).apply(instance, Constant::new));

    @Override
    public Vec3 get(LootContext context) {
        return this.value;
    }

    @Override
    public MapCodec<? extends PositionProvider> codec() {
        return MAP_CODEC;
    }
}
