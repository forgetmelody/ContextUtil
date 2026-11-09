package io.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import io.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public final class LocationBasedFunctions {
    public static final Codec<LocationBasedFunction> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.LOCATION_BASED_FUNCTION_TYPE
                    .byNameCodec()
                    .dispatch(LocationBasedFunction::codec, Function.identity())
    );
    public static final Codec<LocationBasedFunction> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);
    public static final Codec<Holder<LocationBasedFunction>> REFERENCE_CODEC = RegistryFileCodec.create(Registries.LOCATION_BASED_FUNCTION, DIRECT_CODEC);
    public static final Codec<HolderSet<LocationBasedFunction>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.LOCATION_BASED_FUNCTION, DIRECT_CODEC);

    private LocationBasedFunctions() {
    }

    private record Empty() implements LocationBasedFunction {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public void apply(Vec3 position, LootContext context) {

        }

        @Override
        public MapCodec<? extends LocationBasedFunction> codec() {
            return MAP_CODEC;
        }
    }
}
