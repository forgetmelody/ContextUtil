package io.github.forgetmelody.contextutil.providers.itemstacks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public record Empty() implements ItemStackProvider {
    public static final Empty INSTANCE = new Empty();
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
