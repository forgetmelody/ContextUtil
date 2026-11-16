package io.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.providers.positions.PositionProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.phys.Vec3;

public record SpawnParticles(
        ParticleOptions particle,
        NumberProvider count,
        PositionProvider velocity,
        NumberProvider speed
) implements LocationBasedFunction {
    public static final MapCodec<SpawnParticles> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ParticleTypes.CODEC.fieldOf("particle").forGetter(SpawnParticles::particle),
            NumberProviders.CODEC.optionalFieldOf("count", io.github.forgetmelody.contextutil.providers.numbers.NumberProviders.ZERO).forGetter(SpawnParticles::count),
            PositionProvider.DIRECT_CODEC.fieldOf("velocity").forGetter(SpawnParticles::velocity),
            NumberProviders.CODEC.optionalFieldOf("speed", io.github.forgetmelody.contextutil.providers.numbers.NumberProviders.ZERO).forGetter(SpawnParticles::speed)
    ).apply(instance, SpawnParticles::new));

    @Override
    public void apply(Vec3 position, LootContext context) {
        ServerLevel level = context.getLevel();
        for (int i = 0; i < Math.max(0, this.count.getInt(context)); i++) {
            Vec3 velocity = this.velocity.get(context);
            if (velocity != null) {
                level.sendParticles(
                        this.particle,
                        position.x,
                        position.y,
                        position.z,
                        0,
                        velocity.x,
                        velocity.y,
                        velocity.z,
                        this.speed.getFloat(context)
                );
            }
        }

    }

    @Override
    public MapCodec<? extends LocationBasedFunction> codec() {
        return MAP_CODEC;
    }
}
