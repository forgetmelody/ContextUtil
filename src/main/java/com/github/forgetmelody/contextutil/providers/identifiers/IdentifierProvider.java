package com.github.forgetmelody.contextutil.providers.identifiers;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.github.forgetmelody.contextutil.ContextUtil;
import com.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public interface IdentifierProvider {
    Codec<IdentifierProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<IdentifierProvider> dispatched = BuiltInRegistries.IDENTIFIER_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(IdentifierProvider::codec, Function.identity());
        return Codec.either(Constant.INLINE_CODEC, dispatched).xmap(Either::unwrap, IdentifierProvider::wrap);
    });
    Codec<IdentifierProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty::new);

    static IdentifierProvider empty() {
        return Empty.INSTANCE;
    }

    static IdentifierProvider constant(Identifier id) {
        return new Constant(id);
    }

    private static Either<Constant, IdentifierProvider> wrap(IdentifierProvider provider) {
        return provider instanceof Constant constant ? Either.left(constant) : Either.right(provider);
    }

    static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends IdentifierProvider>> context) {
        context.register(ContextUtil.id("empty"), Empty.MAP_CODEC);
        context.register(ContextUtil.id("constant"), Constant.MAP_CODEC);
    }

    @Nullable Identifier get(LootContext context);

    MapCodec<? extends IdentifierProvider> codec();

}
