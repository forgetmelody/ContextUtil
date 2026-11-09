package io.github.forgetmelody.contextutil.registries;

import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import io.github.forgetmelody.contextutil.functions.locationbaseds.LocationBasedFunction;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class BuiltInRegistries {
    public static final Registry<MapCodec<? extends EntityFunction>> ENTITY_FUNCTION_TYPE = create(Registries.ENTITY_FUNCTION_TYPE);
    public static final Registry<MapCodec<? extends LocationBasedFunction>> LOCATION_BASED_FUNCTION_TYPE = create(Registries.LOCATION_BASED_FUNCTION_TYPE);

    private BuiltInRegistries() {
    }

    public static <T> Registry<T> create(ResourceKey<Registry<T>> key) {
        return new MappedRegistry<>(key, Lifecycle.stable());
    }
}
