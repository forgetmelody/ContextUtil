package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.providers.entities.ContextEntityProvider;
import io.github.forgetmelody.contextutil.providers.itemstacks.ContextItemStackProvider;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import io.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.function.Function;

public interface ContextEntityFunction extends LootContextUser {
    Codec<ContextEntityFunction> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.CONTEXT_ENTITY_FUNCTION_TYPE
                    .byNameCodec()
                    .dispatch(ContextEntityFunction::codec, Function.identity())
    );
    Codec<HolderSet<ContextEntityFunction>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.CONTEXT_ENTITY_FUNCTION, DIRECT_CODEC);
    Codec<Holder<ContextEntityFunction>> REFERENCE_CODEC = RegistryFileCodec.create(Registries.CONTEXT_ENTITY_FUNCTION, DIRECT_CODEC);

    static ContextEntityFunction.Builder ignite(NumberProvider duration) {
        return () -> new Ignite(duration);
    }

    static ContextEntityFunction.Builder setEquipmentSlot(EquipmentSlot slot, ContextItemStackProvider item) {
        return () -> new SetEquipmentSlot(slot, item);
    }

    static ContextEntityFunction.Builder setVehicle(ContextEntityProvider vehicle) {
        return () -> new SetVehicle(vehicle);
    }

    static ContextEntityFunction.Builder addPassenger(ContextEntityProvider passenger) {
        return () -> new AddPassenger(passenger);
    }

    void apply(Entity entity, LootContext context);

    MapCodec<? extends ContextEntityFunction> codec();

    @FunctionalInterface
    interface Builder {
        ContextEntityFunction build();
    }
}
