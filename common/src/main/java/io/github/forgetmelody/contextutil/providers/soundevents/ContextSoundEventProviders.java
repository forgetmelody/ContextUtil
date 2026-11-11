package io.github.forgetmelody.contextutil.providers.soundevents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public final class ContextSoundEventProviders {
    public static final Codec<ContextSoundEventProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    private ContextSoundEventProviders() {
    }

    private record Empty() implements ContextSoundEventProvider {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public @Nullable Holder<SoundEvent> get(LootContext context) {
            return null;
        }

        @Override
        public MapCodec<? extends ContextSoundEventProvider> codec() {
            return MAP_CODEC;
        }
    }

}
