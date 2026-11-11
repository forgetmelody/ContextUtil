package io.github.forgetmelody.contextutil.providers.soundevents;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public interface ContextSoundEventProvider extends LootContextUser {
    Codec<ContextSoundEventProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<ContextSoundEventProvider> dispatched = BuiltInRegistries.CONTEXT_SOUND_EVENT_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(ContextSoundEventProvider::codec, Function.identity());
        return Codec.either(Constant.INLINE_CODEC, dispatched).xmap(Either::unwrap, ContextSoundEventProvider::wrap);
    });

    static ContextSoundEventProvider.Builder constant(Holder<SoundEvent> value) {
        return () -> new Constant(value);
    }

    static ContextSoundEventProvider.Builder constant(SoundEvent value) {
        return () -> new Constant(Holder.direct(value));
    }

    private static Either<Constant, ContextSoundEventProvider> wrap(ContextSoundEventProvider provider) {
        return provider instanceof Constant constant ? Either.left(constant) : Either.right(provider);
    }

    @Nullable Holder<SoundEvent> get(LootContext context);

    MapCodec<? extends ContextSoundEventProvider> codec();

    @FunctionalInterface
    interface Builder {
        ContextSoundEventProvider build();
    }
}
