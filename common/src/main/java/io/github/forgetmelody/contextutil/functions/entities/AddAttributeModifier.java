package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public record AddAttributeModifier(
        Identifier id,
        Holder<Attribute> attribute,
        NumberProvider amount,
        AttributeModifier.Operation operation
) implements ContextEntityFunction {

    @Override
    public void apply(Entity entity, LootContext context) {

    }

    @Override
    public MapCodec<? extends ContextEntityFunction> codec() {
        return null;
    }
}
