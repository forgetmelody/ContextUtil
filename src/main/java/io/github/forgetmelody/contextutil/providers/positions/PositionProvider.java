package io.github.forgetmelody.contextutil.providers.positions;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface PositionProvider extends LootContextUser {
    Codec<PositionProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<PositionProvider> dispatched = BuiltInRegistries.POSITION_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(PositionProvider::codec, Function.identity());
        return Codec.either(Constant.INLINE_CODEC, dispatched).xmap(Either::unwrap, PositionProvider::wrap);
    });

    Codec<PositionProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty::new);

    static PositionProvider empty() {
        return Empty.INSTANCE;
    }

    static PositionProvider origin() {
        return Origin.INSTANCE;
    }

    static PositionProvider constant(Vec3 value) {
        return new Constant(value);
    }

    static PositionProvider constant(double x, double y, double z) {
        return new Constant(new Vec3(x, y, z));
    }

    static PositionProvider random(double boundX, double boundY, double boundZ) {
        return Random.of(boundX, boundY, boundZ);
    }

    static PositionProvider random(Random.PositionSource x, Random.PositionSource y, Random.PositionSource z) {
        return new Random(x, y, z);
    }

    static PositionProvider fromEntity(EntityProvider entity) {
        return new FromEntity(entity);
    }

    static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends PositionProvider>> context) {
        context.register(ContextUtil.id("empty"), Empty.MAP_CODEC);
        context.register(ContextUtil.id("constant"), Constant.MAP_CODEC);
        context.register(ContextUtil.id("origin"), Origin.MAP_CODEC);
        context.register(ContextUtil.id("random"), Random.MAP_CODEC);
        context.register(ContextUtil.id("from_entity"), FromEntity.MAP_CODEC);
    }

    private static Either<Constant, PositionProvider> wrap(PositionProvider provider) {
        return provider instanceof Constant constant ? Either.left(constant) : Either.right(provider);
    }

    @Nullable Vec3 get(LootContext context);

    MapCodec<? extends PositionProvider> codec();

    default @Nullable BlockPos getBlockPos(LootContext context) {
        Vec3 position = this.get(context);
        return position != null ? BlockPos.containing(position) : null;
    }
}
