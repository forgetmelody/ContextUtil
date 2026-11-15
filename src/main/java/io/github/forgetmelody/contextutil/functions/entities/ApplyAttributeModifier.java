package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.providers.identifiers.ContextIdentifierProvider;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public record ApplyAttributeModifier(
        ContextIdentifierProvider id,
        Holder<Attribute> attribute,
        NumberProvider amount,
        AttributeModifier.Operation operation
) implements ContextEntityFunction {
    public static final MapCodec<ApplyAttributeModifier> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ContextIdentifierProvider.DIRECT_CODEC.fieldOf("id").forGetter(ApplyAttributeModifier::id),
            Attribute.CODEC.fieldOf("attribute").forGetter(ApplyAttributeModifier::attribute),
            NumberProviders.CODEC.fieldOf("amount").forGetter(ApplyAttributeModifier::amount),
            AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(ApplyAttributeModifier::operation)
    ).apply(instance, ApplyAttributeModifier::new));

    @Override
    public void apply(Entity entity, LootContext context) {
        if (entity instanceof LivingEntity livingEntity) {
            AttributeMap attributes = livingEntity.getAttributes();
            AttributeInstance instance = attributes.getInstance(this.attribute);
            if (instance != null) {
                Identifier id = this.id.get(context);
                if (id != null) {
                    instance.removeModifier(id);
                    instance.addTransientModifier(
                            new AttributeModifier(
                                    id,
                                    this.amount.getFloat(context),
                                    this.operation
                            )
                    );
                }
            }
        }
    }

    @Override
    public MapCodec<? extends ContextEntityFunction> codec() {
        return MAP_CODEC;
    }
}
