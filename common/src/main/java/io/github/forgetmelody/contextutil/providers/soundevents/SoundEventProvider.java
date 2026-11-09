package io.github.forgetmelody.contextutil.providers.soundevents;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import org.jspecify.annotations.Nullable;

public interface SoundEventProvider extends LootContextUser {

    @Nullable Holder<SoundEvent> get(LootContext context);

    MapCodec<? extends SoundEventProvider> codec();
}
