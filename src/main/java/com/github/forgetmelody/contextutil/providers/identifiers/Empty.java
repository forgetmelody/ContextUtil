package com.github.forgetmelody.contextutil.providers.identifiers;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public record Empty() implements IdentifierProvider {
    public static final Empty INSTANCE = new Empty();
    public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public @Nullable Identifier get(LootContext context) {
        return null;
    }

    @Override
    public MapCodec<? extends IdentifierProvider> codec() {
        return MAP_CODEC;
    }
}
