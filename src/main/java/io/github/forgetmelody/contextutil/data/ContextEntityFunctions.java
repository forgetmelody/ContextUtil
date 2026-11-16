package io.github.forgetmelody.contextutil.data;

import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import io.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import io.github.forgetmelody.contextutil.providers.itemstacks.ItemStackProvider;
import io.github.forgetmelody.contextutil.providers.locations.LocationProvider;
import io.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;

public final class ContextEntityFunctions {
    public static final ResourceKey<EntityFunction> EQUIP_IRON_HELMET = key("equip_iron_helmet");
    public static final ResourceKey<EntityFunction> RIDE_SKELETON_HORSE = key("ride_skeleton_horse");

    private ContextEntityFunctions() {
    }

    public static void boostrap(BootstrapContext<EntityFunction> context) {
        context.register(
                EQUIP_IRON_HELMET,
                EntityFunction.setEquipmentSlot(
                                EquipmentSlot.HEAD,
                                ItemStackProvider.constant(Items.IRON_HELMET)
                        )
        );
        context.register(
                RIDE_SKELETON_HORSE,
                EntityFunction.setVehicle(
                        EntityProvider.summon(
                                EntityType.SKELETON_HORSE,
                                LocationProvider.origin(),
                                HolderSet.empty()
                        )
                )
        );
    }

    private static ResourceKey<EntityFunction> key(String name) {
        return ResourceKey.create(Registries.ENTITY_FUNCTION, ContextUtil.id(name));
    }
}
