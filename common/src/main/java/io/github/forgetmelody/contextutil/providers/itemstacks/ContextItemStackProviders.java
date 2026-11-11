package io.github.forgetmelody.contextutil.providers.itemstacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public final class ContextItemStackProviders {
    public static final Codec<ContextItemStackProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    private ContextItemStackProviders() {
    }

    private record Empty() implements ContextItemStackProvider {
        private static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public ItemStack get(LootContext context) {
            return ItemStack.EMPTY;
        }

        @Override
        public MapCodec<? extends ContextItemStackProvider> codec() {
            return MAP_CODEC;
        }
    }

}
