package io.github.forgetmelody.contextutil.data;

import net.minecraft.core.RegistrySetBuilder;

public final class DataPackRegistryObjects {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONTEXT_ENTITY_FUNCTION, ContextEntityFunctions::boostrap);

    private DataPackRegistryObjects() {
    }
}
