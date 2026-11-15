package io.github.forgetmelody.contextutil.providers.itemstacks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public record Constant(ItemStack value) implements ContextItemStackProvider {
    public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStack.CODEC.fieldOf("value").forGetter(Constant::value)
    ).apply(instance, Constant::new));

    @Override
    public ItemStack get(LootContext context) {
        return value;
    }

    @Override
    public MapCodec<? extends ContextItemStackProvider> codec() {
        return MAP_CODEC;
    }
}
