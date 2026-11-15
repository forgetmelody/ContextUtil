package io.github.forgetmelody.contextutil.providers.soundevents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public record Constant(Holder<SoundEvent> value) implements ContextSoundEventProvider {
    public static final Codec<Constant> INLINE_CODEC = SoundEvent.CODEC.xmap(Constant::new, Constant::value);
    public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            SoundEvent.CODEC.fieldOf("value").forGetter(Constant::value)
    ).apply(instance, Constant::new));

    @Override
    public @Nullable Holder<SoundEvent> get(LootContext context) {
        return value;
    }

    @Override
    public MapCodec<? extends ContextSoundEventProvider> codec() {
        return MAP_CODEC;
    }
}
