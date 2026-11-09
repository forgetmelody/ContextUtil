package io.github.forgetmelody.contextutil.providers.locations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public final class LocationProviders {
    public static final Codec<LocationProvider> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.LOCATION_PROVIDER_TYPE
                    .byNameCodec()
                    .dispatch(LocationProvider::codec, Function.identity())
    );
    public static final Codec<LocationProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    private LocationProviders() {
    }

    private record Empty() implements LocationProvider {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public @Nullable Vec3 getPosition(LootContext context) {
            return null;
        }

        @Override
        public MapCodec<? extends LocationProvider> codec() {
            return MAP_CODEC;
        }
    }
}
