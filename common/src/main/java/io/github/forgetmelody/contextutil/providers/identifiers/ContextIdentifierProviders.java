package io.github.forgetmelody.contextutil.providers.identifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public final class ContextIdentifierProviders {
    private static final Empty EMPTY = new Empty();
    public static final Codec<ContextIdentifierProvider> NETWORK_CODEC = MapCodec.unitCodec(EMPTY);

    private ContextIdentifierProviders() {
    }

    private record Empty() implements ContextIdentifierProvider {
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(EMPTY);

        @Override
        public @Nullable Identifier get(LootContext context) {
            return null;
        }

        @Override
        public MapCodec<? extends ContextIdentifierProvider> codec() {
            return MAP_CODEC;
        }
    }
}
