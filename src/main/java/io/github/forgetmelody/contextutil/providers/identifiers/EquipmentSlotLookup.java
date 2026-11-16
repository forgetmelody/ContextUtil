package io.github.forgetmelody.contextutil.providers.identifiers;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public record EquipmentSlotLookup(Map<EquipmentSlot, Identifier> identifiers) implements IdentifierProvider {
    @Override
    public @Nullable Identifier get(LootContext context) {
        return null;
    }

    @Override
    public MapCodec<? extends IdentifierProvider> codec() {
        return null;
    }
}
