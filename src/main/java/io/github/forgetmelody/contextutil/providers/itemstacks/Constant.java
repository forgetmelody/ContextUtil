package io.github.forgetmelody.contextutil.providers.itemstacks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.storage.loot.LootContext;

public record Constant(ItemStackTemplate value) implements ItemStackProvider {
    public static final Codec<Constant> INLINE_CODEC = ItemStackTemplate.CODEC.xmap(Constant::new, Constant::value);
    public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStackTemplate.CODEC.fieldOf("value").forGetter(Constant::value)
    ).apply(instance, Constant::new));

    @Override
    public ItemStack get(LootContext context) {
        return this.value.create();
    }

    @Override
    public MapCodec<? extends ItemStackProvider> codec() {
        return MAP_CODEC;
    }
}
