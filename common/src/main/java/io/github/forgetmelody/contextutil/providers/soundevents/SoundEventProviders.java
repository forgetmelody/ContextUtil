package io.github.forgetmelody.contextutil.providers.soundevents;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public final class SoundEventProviders {
    public static final Codec<SoundEventProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<SoundEventProvider> dispatched = BuiltInRegistries.SOUND_EVENT_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(SoundEventProvider::codec, Function.identity());
        return Codec.either(Constant.INLINE_CODEC, dispatched).xmap(Either::unwrap, SoundEventProviders::wrap);
    });
    public static final Codec<SoundEventProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    private SoundEventProviders() {
    }

    private static Either<Constant, SoundEventProvider> wrap(SoundEventProvider provider) {
        return provider instanceof Constant constant ? Either.left(constant) : Either.right(provider);
    }

    private record Empty() implements SoundEventProvider {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public @Nullable Holder<SoundEvent> get(LootContext context) {
            return null;
        }

        @Override
        public MapCodec<? extends SoundEventProvider> codec() {
            return MAP_CODEC;
        }
    }

    private record Constant(Holder<SoundEvent> value) implements SoundEventProvider {
        public static final Codec<Constant> INLINE_CODEC = SoundEvent.CODEC.xmap(Constant::new, Constant::value);
        public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                SoundEvent.CODEC.fieldOf("value").forGetter(Constant::value)
        ).apply(instance, Constant::new));

        @Override
        public @Nullable Holder<SoundEvent> get(LootContext context) {
            return value;
        }

        @Override
        public MapCodec<? extends SoundEventProvider> codec() {
            return MAP_CODEC;
        }
    }
}
