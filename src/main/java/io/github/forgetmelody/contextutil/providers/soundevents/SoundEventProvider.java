package io.github.forgetmelody.contextutil.providers.soundevents;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public interface SoundEventProvider extends LootContextUser {
    Codec<SoundEventProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<SoundEventProvider> dispatched = BuiltInRegistries.SOUND_EVENT_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(SoundEventProvider::codec, Function.identity());
        return Codec.either(Constant.INLINE_CODEC, dispatched).xmap(Either::unwrap, SoundEventProvider::wrap);
    });
    Codec<SoundEventProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty::new);

    static SoundEventProvider empty() {
        return Empty.INSTANCE;
    }

    static SoundEventProvider constant(Holder<SoundEvent> value) {
        return new Constant(value);
    }

    static SoundEventProvider constant(SoundEvent value) {
        return new Constant(Holder.direct(value));
    }

    private static Either<Constant, SoundEventProvider> wrap(SoundEventProvider provider) {
        return provider instanceof Constant constant ? Either.left(constant) : Either.right(provider);
    }

    static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends SoundEventProvider>> context) {
        context.register(ContextUtil.id("empty"), Empty.MAP_CODEC);
        context.register(ContextUtil.id("constant"), Constant.MAP_CODEC);
    }

    @Nullable Holder<SoundEvent> get(LootContext context);

    MapCodec<? extends SoundEventProvider> codec();

}
