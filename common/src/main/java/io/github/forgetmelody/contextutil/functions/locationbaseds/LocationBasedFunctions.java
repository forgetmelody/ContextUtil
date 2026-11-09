package io.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.Codec;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import io.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;

import java.util.function.Function;

public final class LocationBasedFunctions {
    public static final Codec<LocationBasedFunction> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.LOCATION_BASED_FUNCTION_TYPE
                    .byNameCodec()
                    .dispatch(LocationBasedFunction::codec, Function.identity())
    );
    public static final Codec<Holder<LocationBasedFunction>> REFERENCE_CODEC = RegistryFileCodec.create(Registries.LOCATION_BASED_FUNCTION, DIRECT_CODEC);
    public static final Codec<HolderSet<LocationBasedFunction>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.LOCATION_BASED_FUNCTION, DIRECT_CODEC);

    private LocationBasedFunctions() {
    }


}
