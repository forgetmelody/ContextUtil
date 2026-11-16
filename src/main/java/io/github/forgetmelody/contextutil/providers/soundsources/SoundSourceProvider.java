package io.github.forgetmelody.contextutil.providers.soundsources;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public interface SoundSourceProvider extends LootContextUser {
    Codec<SoundSourceProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<SoundSourceProvider> dispatched = BuiltInRegistries.SOUND_SOURCE_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(SoundSourceProvider::codec, Function.identity());
        return Codec.either(Constant.INLINE_CODEC, dispatched).xmap(Either::unwrap, SoundSourceProvider::wrap);
    });
    Codec<SoundSourceProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty::new);

    static SoundSourceProvider empty() {
        return Empty.INSTANCE;
    }

    static SoundSourceProvider constant(SoundSource source) {
        return new Constant(source);
    }

    static SoundSourceProvider fromEntity(EntityProvider entity) {
        return new FromEntity(entity);
    }

    static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends SoundSourceProvider>> context) {
        context.register(ContextUtil.id("empty"), Empty.MAP_CODEC);
        context.register(ContextUtil.id("constant"), Constant.MAP_CODEC);
        context.register(ContextUtil.id("from_entity"), FromEntity.MAP_CODEC);
    }

    @Nullable SoundSource get(LootContext context);

    MapCodec<? extends SoundSourceProvider> codec();

    default SoundSource orElse(LootContext context, SoundSource source) {
        SoundSource soundSource = this.get(context);
        return soundSource != null ? soundSource : source;
    }

    private static Either<Constant, SoundSourceProvider> wrap(SoundSourceProvider provider) {
        return provider instanceof Constant constant ? Either.left(constant) : Either.right(provider);
    }
}
