package io.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.providers.locations.LocationProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.phys.Vec3;

public record SpawnParticles(
        ParticleOptions particle,
        NumberProvider count,
        LocationProvider velocity,
        NumberProvider speed
) implements LocationBasedFunction {
    public static final MapCodec<SpawnParticles> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ParticleTypes.CODEC.fieldOf("particle").forGetter(SpawnParticles::particle),
            NumberProviders.CODEC.optionalFieldOf("count", new ConstantValue(0)).forGetter(SpawnParticles::count),
            LocationProvider.DIRECT_CODEC.fieldOf("velocity").forGetter(SpawnParticles::velocity),
            NumberProviders.CODEC.fieldOf("speed").forGetter(SpawnParticles::speed)
    ).apply(instance, SpawnParticles::new));

    @Override
    public void apply(Vec3 position, LootContext context) {
        ServerLevel level = context.getLevel();
        Vec3 velocity = this.velocity.getPosition(context);
        level.sendParticles(
                this.particle,
                position.x,
                position.y,
                position.z,
                Math.max(0, this.count.getInt(context)),
                velocity != null ? velocity.x : 0,
                velocity != null ? velocity.y : 0,
                velocity != null ? velocity.z : 0,
                this.speed.getFloat(context)
        );
    }

    @Override
    public MapCodec<? extends LocationBasedFunction> codec() {
        return MAP_CODEC;
    }
}
