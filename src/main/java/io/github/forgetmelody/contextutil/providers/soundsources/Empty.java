package io.github.forgetmelody.contextutil.providers.soundsources;

import com.mojang.serialization.MapCodec;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public record Empty() implements SoundSourceProvider {
    public static final Empty INSTANCE = new Empty();
    public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public @Nullable SoundSource get(LootContext context) {
        return null;
    }

    @Override
    public MapCodec<? extends SoundSourceProvider> codec() {
        return MAP_CODEC;
    }
}
