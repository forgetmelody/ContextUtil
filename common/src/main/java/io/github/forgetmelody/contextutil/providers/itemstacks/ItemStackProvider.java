package io.github.forgetmelody.contextutil.providers.itemstacks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;

public interface ItemStackProvider extends LootContextUser {
    ItemStack get(LootContext context);

    MapCodec<? extends ItemStackProvider> codec();
}
