package io.github.forgetmelody.contextutil.providers.locations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class ContextLocationProviders {
    public static final Codec<ContextLocationProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    private ContextLocationProviders() {
    }

    private record Empty() implements ContextLocationProvider {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public @Nullable Vec3 getPosition(LootContext context) {
            return null;
        }

        @Override
        public MapCodec<? extends ContextLocationProvider> codec() {
            return MAP_CODEC;
        }
    }
}
