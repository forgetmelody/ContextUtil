package io.github.forgetmelody.contextutil.providers.locations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public interface LocationProvider extends LootContextUser {
    Codec<LocationProvider> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.POSITION_PROVIDER_TYPE
                    .byNameCodec()
                    .dispatch(LocationProvider::codec, Function.identity())
    );
    Codec<LocationProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty::new);

    static LocationProvider empty() {
        return Empty.INSTANCE;
    }

    static LocationProvider origin() {
        return Origin.INSTANCE;
    }

    static LocationProvider origin(Vec3 offset) {
        return new Origin(Optional.of(offset));
    }

    static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends LocationProvider>> context) {
        context.register(ContextUtil.id("empty"), Empty.MAP_CODEC);
        context.register(ContextUtil.id("origin"), Origin.MAP_CODEC);
    }

    @Nullable Vec3 getPosition(LootContext context);

    MapCodec<? extends LocationProvider> codec();

    default @Nullable BlockPos getBlockPos(LootContext context) {
        Vec3 position = this.getPosition(context);
        return position != null ? BlockPos.containing(position) : null;
    }
}
