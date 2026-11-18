package com.github.forgetmelody.contextutil.data;

import com.github.forgetmelody.contextutil.ContextUtil;
import com.github.forgetmelody.contextutil.functions.locationbaseds.LocationBasedFunction;
import com.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import com.github.forgetmelody.contextutil.providers.numbers.NumberProviders;
import com.github.forgetmelody.contextutil.providers.positions.PositionProvider;
import com.github.forgetmelody.contextutil.providers.soundevents.SoundEventProvider;
import com.github.forgetmelody.contextutil.providers.soundsources.SoundSourceProvider;
import com.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public final class LocationBasedFunctions {
    public static final ResourceKey<LocationBasedFunction> SPAWN_FIRE_PARTICLES = key("spawn_fire_particles");
    public static final ResourceKey<LocationBasedFunction> PLAY_HORN_SOUND = key("play_horn_sound");

    private LocationBasedFunctions() {
    }

    public static void boostrap(BootstrapContext<LocationBasedFunction> context) {
        context.register(
                SPAWN_FIRE_PARTICLES,
                LocationBasedFunction.spawnParticles(
                        ParticleTypes.SMALL_FLAME,
                        NumberProviders.constant(100),
                        PositionProvider.random(
                                2.0,
                                2.0,
                                2.0
                        ),
                        NumberProviders.constant(0.2F)
                )
        );
        context.register(
                PLAY_HORN_SOUND,
                LocationBasedFunction.playSound(
                        EntityProvider.empty(),
                        SoundEventProvider.constant(SoundEvents.RAID_HORN),
                        SoundSourceProvider.constant(SoundSource.MASTER),
                        NumberProviders.constant(1.0F),
                        NumberProviders.constant(1.0F)
                )
        );
    }

    private static ResourceKey<LocationBasedFunction> key(String name) {
        return ResourceKey.create(Registries.LOCATION_BASED_FUNCTION, ContextUtil.id(name));
    }
}
