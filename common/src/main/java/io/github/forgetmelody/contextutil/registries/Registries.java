package io.github.forgetmelody.contextutil.registries;

import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import io.github.forgetmelody.contextutil.functions.locationbaseds.LocationBasedFunction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class Registries {
    public static final ResourceKey<Registry<MapCodec<? extends EntityFunction>>> ENTITY_FUNCTION_TYPE = key("entity_function_type");
    public static final ResourceKey<Registry<MapCodec<? extends LocationBasedFunction>>> LOCATION_BASED_FUNCTION_TYPE = key("location_based_function_type");
    public static final ResourceKey<Registry<EntityFunction>> ENTITY_FUNCTION = key("entity_function");
    public static final ResourceKey<Registry<LocationBasedFunction>> LOCATION_BASED_FUNCTION = key("location_based_function");

    private Registries() {
    }

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ContextUtil.id(name));
    }
}
