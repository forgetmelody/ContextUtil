package io.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

public record SpawnSpreadParticles() implements LocationBasedFunction {
    @Override
    public void apply(Vec3 position, LootContext context) {

    }

    @Override
    public MapCodec<? extends LocationBasedFunction> codec() {
        return null;
    }
}
