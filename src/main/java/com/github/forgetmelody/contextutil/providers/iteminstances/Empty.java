package com.github.forgetmelody.contextutil.providers.iteminstances;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public record Empty() implements ItemInstanceProvider {
    public static final Empty INSTANCE = new Empty();
    public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public @Nullable ItemInstance get(LootContext context) {
        return null;
    }

    @Override
    public MapCodec<? extends ItemInstanceProvider> codec() {
        return MAP_CODEC;
    }
}
