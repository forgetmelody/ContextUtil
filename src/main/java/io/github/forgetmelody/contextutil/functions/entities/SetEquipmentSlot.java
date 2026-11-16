package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.providers.itemstacks.ItemStackProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public record SetEquipmentSlot(
        EquipmentSlot slot,
        ItemStackProvider item
) implements EntityFunction {
    public static final MapCodec<SetEquipmentSlot> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EquipmentSlot.CODEC.fieldOf("slot").forGetter(SetEquipmentSlot::slot),
            ItemStackProvider.DIRECT_CODEC.fieldOf("item").forGetter(SetEquipmentSlot::item)
    ).apply(instance, SetEquipmentSlot::new));

    @Override
    public void apply(Entity entity, LootContext context) {
        ItemStack item = this.item.getCopied(context);
        if (item != null && !item.isEmpty() && entity instanceof LivingEntity livingEntity) {
            livingEntity.setItemSlot(this.slot, item);
        }
    }

    @Override
    public MapCodec<? extends EntityFunction> codec() {
        return MAP_CODEC;
    }
}
