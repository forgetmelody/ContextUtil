package com.github.forgetmelody.contextutil.data;

import com.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.core.RegistrySetBuilder;

public final class DataPackRegistryObjects {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENTITY_FUNCTION, EntityFunctions::boostrap)
            .add(Registries.LOCATION_BASED_FUNCTION, LocationBasedFunctions::boostrap);

    private DataPackRegistryObjects() {
    }
}
