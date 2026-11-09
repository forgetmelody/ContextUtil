package io.github.forgetmelody.contextutil.providers.itemstacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.function.Function;

public final class ItemStackProviders {
    public static final Codec<ItemStackProvider> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.ITEM_STACK_PROVIDER_TYPE
                    .byNameCodec()
                    .dispatch(ItemStackProvider::codec, Function.identity())
    );
    public static final Codec<ItemStackProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    private ItemStackProviders() {
    }

    private record Empty() implements ItemStackProvider {
        private static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public ItemStack get(LootContext context) {
            return ItemStack.EMPTY;
        }

        @Override
        public MapCodec<? extends ItemStackProvider> codec() {
            return MAP_CODEC;
        }
    }

    private record Constant(ItemStack value) implements ItemStackProvider {
        public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemStack.CODEC.fieldOf("value").forGetter(Constant::value)
        ).apply(instance, Constant::new));

        @Override
        public ItemStack get(LootContext context) {
            return value;
        }

        @Override
        public MapCodec<? extends ItemStackProvider> codec() {
            return MAP_CODEC;
        }
    }
}
