package io.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

public final class ContextLocationBasedFunctions {
    public static final Codec<ContextLocationBasedFunction> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    private ContextLocationBasedFunctions() {
    }

    private record Empty() implements ContextLocationBasedFunction {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public void apply(Vec3 position, LootContext context) {

        }

        @Override
        public MapCodec<? extends ContextLocationBasedFunction> codec() {
            return MAP_CODEC;
        }
    }
}
