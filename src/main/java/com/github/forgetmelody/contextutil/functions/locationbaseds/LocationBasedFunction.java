package com.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.github.forgetmelody.contextutil.ContextUtil;
import com.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import com.github.forgetmelody.contextutil.providers.positions.PositionProvider;
import com.github.forgetmelody.contextutil.providers.soundevents.SoundEventProvider;
import com.github.forgetmelody.contextutil.providers.soundsources.SoundSourceProvider;
import com.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import com.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Function;

public interface LocationBasedFunction extends LootContextUser {
    Codec<LocationBasedFunction> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.LOCATION_BASED_FUNCTION_TYPE
                    .byNameCodec()
                    .dispatch(LocationBasedFunction::codec, Function.identity())
    );
    Codec<HolderSet<LocationBasedFunction>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.LOCATION_BASED_FUNCTION, DIRECT_CODEC);
    Codec<Holder<LocationBasedFunction>> REFERENCE_CODEC = RegistryFileCodec.create(Registries.LOCATION_BASED_FUNCTION, DIRECT_CODEC);
    Codec<LocationBasedFunction> NETWORK_CODEC = MapCodec.unitCodec(Empty::new);

    static LocationBasedFunction spawnParticles(
            ParticleOptions particle,
            NumberProvider count,
            PositionProvider velocity,
            NumberProvider speed
    ) {
        return new SpawnParticles(particle, count, velocity, speed);
    }

    static LocationBasedFunction playSound(
            EntityProvider except,
            SoundEventProvider sound,
            SoundSourceProvider source,
            NumberProvider volume,
            NumberProvider pitch
    ) {
        return new PlaySound(except, sound, source, volume, pitch);
    }

    static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends LocationBasedFunction>> context) {
        context.register(ContextUtil.id("empty"), Empty.MAP_CODEC);
        context.register(ContextUtil.id("all_of"), AllOf.MAP_CODEC);
        context.register(ContextUtil.id("spawn_particles"), SpawnParticles.MAP_CODEC);
        context.register(ContextUtil.id("play_sound"), PlaySound.MAP_CODEC);
    }

    void apply(Vec3 position, LootContext context);

    MapCodec<? extends LocationBasedFunction> codec();
}
