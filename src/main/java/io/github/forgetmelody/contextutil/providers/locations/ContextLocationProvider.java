package io.github.forgetmelody.contextutil.providers.locations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public interface ContextLocationProvider extends LootContextUser {
    Codec<ContextLocationProvider> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.CONTEXT_LOCATION_PROVIDER_TYPE
                    .byNameCodec()
                    .dispatch(ContextLocationProvider::codec, Function.identity())
    );
    Codec<ContextLocationProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    static ContextLocationProvider empty() {
        return Empty.INSTANCE;
    }

    static ContextLocationProvider origin() {
        return Origin.INSTANCE;
    }

    static ContextLocationProvider origin(Vec3 offset) {
        return new Origin(Optional.of(offset));
    }

    @Nullable Vec3 getPosition(LootContext context);

    MapCodec<? extends ContextLocationProvider> codec();

    default @Nullable BlockPos getBlockPos(LootContext context) {
        Vec3 position = this.getPosition(context);
        return position != null ? BlockPos.containing(position) : null;
    }
}
