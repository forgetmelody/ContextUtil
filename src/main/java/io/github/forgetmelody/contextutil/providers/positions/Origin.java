package io.github.forgetmelody.contextutil.providers.positions;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public record Origin() implements PositionProvider {
    public static final Origin INSTANCE = new Origin();
    public static final MapCodec<Origin> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public Vec3 get(LootContext context) {
        Vec3 vec3 = context.getOptionalParameter(LootContextParams.ORIGIN);
        return vec3 != null ? vec3 : Vec3.ZERO;
    }

    @Override
    public MapCodec<? extends PositionProvider> codec() {
        return MAP_CODEC;
    }
}
