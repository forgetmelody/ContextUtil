package com.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import com.github.forgetmelody.contextutil.providers.soundevents.SoundEventProvider;
import com.github.forgetmelody.contextutil.providers.soundsources.SoundSourceProvider;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.phys.Vec3;

public record PlaySound(
        EntityProvider except,
        SoundEventProvider sound,
        SoundSourceProvider source,
        NumberProvider volume,
        NumberProvider pitch
) implements LocationBasedFunction {
    public static final MapCodec<PlaySound> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EntityProvider.DIRECT_CODEC.optionalFieldOf("except", EntityProvider.empty()).forGetter(PlaySound::except),
            SoundEventProvider.DIRECT_CODEC.fieldOf("sound").forGetter(PlaySound::sound),
            SoundSourceProvider.DIRECT_CODEC.optionalFieldOf("source", SoundSourceProvider.constant(SoundSource.MASTER)).forGetter(PlaySound::source),
            NumberProviders.CODEC.optionalFieldOf("volume", new ConstantValue(1.0F)).forGetter(PlaySound::volume),
            NumberProviders.CODEC.optionalFieldOf("pitch", new ConstantValue(1.0F)).forGetter(PlaySound::pitch)
    ).apply(instance, PlaySound::new));

    @Override
    public void apply(Vec3 position, LootContext context) {
        ServerLevel level = context.getLevel();

        Holder<SoundEvent> sound = this.sound.get(context);
        if (sound != null) {
            level.playSound(
                    this.except.get(context),
                    position.x,
                    position.y,
                    position.z,
                    sound,
                    this.source.orElse(context, SoundSource.MASTER),
                    Math.min(this.volume.getFloat(context), 0.0F),
                    Math.clamp(this.pitch.getFloat(context), 0.0F, 2.0F)
            );
        }
    }

    @Override
    public MapCodec<? extends LocationBasedFunction> codec() {
        return MAP_CODEC;
    }
}
