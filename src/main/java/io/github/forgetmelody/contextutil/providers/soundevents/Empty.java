package io.github.forgetmelody.contextutil.providers.soundevents;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public record Empty() implements SoundEventProvider {
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
