package com.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.github.forgetmelody.contextutil.ContextUtil;
import com.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import com.github.forgetmelody.contextutil.providers.identifiers.IdentifierProvider;
import com.github.forgetmelody.contextutil.providers.iteminstances.ItemInstanceProvider;
import com.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import com.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Function;

public interface EntityFunction extends LootContextUser {
    Codec<EntityFunction> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.ENTITY_FUNCTION_TYPE
                    .byNameCodec()
                    .dispatch(EntityFunction::codec, Function.identity())
    );
    Codec<HolderSet<EntityFunction>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.ENTITY_FUNCTION, DIRECT_CODEC);
    Codec<Holder<EntityFunction>> REFERENCE_CODEC = RegistryFileCodec.create(Registries.ENTITY_FUNCTION, DIRECT_CODEC);
    Codec<EntityFunction> NETWORK_CODEC = MapCodec.unitCodec(Empty::new);

    static EntityFunction empty() {
        return Empty.INSTANCE;
    }

    static EntityFunction ignite(NumberProvider duration) {
        return new Ignite(duration);
    }

    static EntityFunction setEquipmentSlot(EquipmentSlot slot, ItemInstanceProvider item) {
        return new SetEquipmentSlot(slot, item);
    }

    static EntityFunction setVehicle(EntityProvider vehicle) {
        return new SetVehicle(vehicle);
    }

    static EntityFunction addPassenger(EntityProvider passenger) {
        return new AddPassenger(passenger);
    }

    static EntityFunction applyAttributeModifier(
            IdentifierProvider id,
            Holder<Attribute> attribute,
            NumberProvider amount,
            AttributeModifier.Operation operation
    ) {
        return new ApplyAttributeModifier(id, attribute, amount, operation);
    }

    static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends EntityFunction>> context) {
        context.register(ContextUtil.id("empty"), Empty.MAP_CODEC);
        context.register(ContextUtil.id("all_of"), AllOf.MAP_CODEC);
        context.register(ContextUtil.id("add_passenger"), AddPassenger.MAP_CODEC);
        context.register(ContextUtil.id("apply_attribute_modifier"), ApplyAttributeModifier.MAP_CODEC);
        context.register(ContextUtil.id("apply_mob_effect"), ApplyMobEffects.MAP_CODEC);
        context.register(ContextUtil.id("ignite"), Ignite.MAP_CODEC);
        context.register(ContextUtil.id("set_equipment_slot"), SetEquipmentSlot.MAP_CODEC);
        context.register(ContextUtil.id("set_vehicle"), SetVehicle.MAP_CODEC);
    }

    void apply(Entity entity, LootContext context);

    MapCodec<? extends EntityFunction> codec();

}
