package io.github.forgetmelody.contextutil.providers.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;

public final class ContextEntityProviders {
    public static final Codec<ContextEntityProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    private ContextEntityProviders() {
    }

    private record Empty() implements ContextEntityProvider {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public @Nullable Entity get(LootContext context) {
            return null;
        }

        @Override
        public MapCodec<? extends ContextEntityProvider> codec() {
            return MAP_CODEC;
        }
    }

}
