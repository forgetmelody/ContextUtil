package io.github.forgetmelody.contextutil.providers.itemstacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public interface ContextItemStackProvider extends LootContextUser {
    Codec<ContextItemStackProvider> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.CONTEXT_ITEM_STACK_PROVIDER_TYPE
                    .byNameCodec()
                    .dispatch(ContextItemStackProvider::codec, Function.identity())
    );

    static ContextItemStackProvider.Builder constant(ItemStack item) {
        ItemStack copiedItem = item.copy();
        return () -> new Constant(copiedItem);
    }

    static ContextItemStackProvider.Builder constant(Item item) {
        return () -> new Constant(new ItemStack(item));
    }

    @Nullable ItemStack get(LootContext context);

    MapCodec<? extends ContextItemStackProvider> codec();

    @FunctionalInterface
    interface Builder {
        ContextItemStackProvider build();
    }
}
