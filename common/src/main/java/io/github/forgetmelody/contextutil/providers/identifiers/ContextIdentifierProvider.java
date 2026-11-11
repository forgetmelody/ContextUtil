package io.github.forgetmelody.contextutil.providers.identifiers;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public interface ContextIdentifierProvider {
    Codec<ContextIdentifierProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<ContextIdentifierProvider> dispatched = BuiltInRegistries.CONTEXT_IDENTIFIER_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(ContextIdentifierProvider::codec, Function.identity());
        return Codec.either(Constant.INLINE_CODEC, dispatched).xmap(Either::unwrap, ContextIdentifierProvider::wrap);
    });

    static ContextIdentifierProvider.Builder constant(Identifier id) {
        return () -> new Constant(id);
    }

    private static Either<Constant, ContextIdentifierProvider> wrap(ContextIdentifierProvider provider) {
        return provider instanceof Constant constant ? Either.left(constant) : Either.right(provider);
    }

    @Nullable Identifier get(LootContext context);

    MapCodec<? extends ContextIdentifierProvider> codec();

    @FunctionalInterface
    interface Builder {
        ContextIdentifierProvider build();
    }
}
