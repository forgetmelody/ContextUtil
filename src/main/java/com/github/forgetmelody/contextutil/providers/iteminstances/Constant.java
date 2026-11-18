package com.github.forgetmelody.contextutil.providers.iteminstances;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public record Constant(ItemStackTemplate value) implements ItemInstanceProvider {
    public static final Codec<Constant> INLINE_CODEC = ItemStackTemplate.CODEC.xmap(Constant::new, Constant::value);
    public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStackTemplate.CODEC.fieldOf("value").forGetter(Constant::value)
    ).apply(instance, Constant::new));

    @Override
    public @Nullable ItemInstance get(LootContext context) {
        return this.value;
    }

    @Override
    public MapCodec<? extends ItemInstanceProvider> codec() {
        return MAP_CODEC;
    }
}
