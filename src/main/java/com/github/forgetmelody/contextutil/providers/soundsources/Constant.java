package com.github.forgetmelody.contextutil.providers.soundsources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public record Constant(SoundSource value) implements SoundSourceProvider {
    public static final Codec<Constant> INLINE_CODEC = Codec.STRING.comapFlatMap(Constant::byName, SoundSource::getName).xmap(Constant::new, Constant::value);
    public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.comapFlatMap(Constant::byName, SoundSource::getName).fieldOf("value").forGetter(Constant::value)
    ).apply(instance, Constant::new));

    @Override
    public @Nullable SoundSource get(LootContext context) {
        return this.value;
    }

    @Override
    public MapCodec<? extends SoundSourceProvider> codec() {
        return MAP_CODEC;
    }

    private static DataResult<SoundSource> byName(String name) {
        for (SoundSource value : SoundSource.values()) {
            if (value.name().equals(name))
                return DataResult.success(value);
        }

        return DataResult.error(() -> "unknown sound source name: " + name);
    }
}
