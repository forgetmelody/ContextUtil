package io.github.forgetmelody.contextutil.data;

import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.functions.entities.ContextEntityFunction;
import io.github.forgetmelody.contextutil.providers.entities.ContextEntityProvider;
import io.github.forgetmelody.contextutil.providers.itemstacks.ContextItemStackProvider;
import io.github.forgetmelody.contextutil.providers.locations.ContextLocationProvider;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;

public final class ContextEntityFunctions {
    public static final ResourceKey<ContextEntityFunction> EQUIP_IRON_HELMET = key("equip_iron_helmet");
    public static final ResourceKey<ContextEntityFunction> RIDE_SKELETON_HORSE = key("ride_skeleton_horse");

    private ContextEntityFunctions() {
    }

    public static void boostrap(BootstrapContext<ContextEntityFunction> context) {
        context.register(
                EQUIP_IRON_HELMET,
                ContextEntityFunction.setEquipmentSlot(
                                EquipmentSlot.HEAD,
                                ContextItemStackProvider.constant(Items.IRON_HELMET)
                        )
        );
        context.register(
                RIDE_SKELETON_HORSE,
                ContextEntityFunction.setVehicle(
                        ContextEntityProvider.summon(
                                EntityType.SKELETON_HORSE,
                                ContextLocationProvider.origin(),
                                HolderSet.empty()
                        )
                )
        );
    }

    private static ResourceKey<ContextEntityFunction> key(String name) {
        return ResourceKey.create(Registries.CONTEXT_ENTITY_FUNCTION, ContextUtil.id(name));
    }
}
