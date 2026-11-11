package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.providers.entities.ContextEntityProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public record SetVehicle(
        ContextEntityProvider vehicle
) implements ContextEntityFunction {
    public static final MapCodec<SetVehicle> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ContextEntityProvider.DIRECT_CODEC.fieldOf("vehicle").forGetter(SetVehicle::vehicle)
    ).apply(instance, SetVehicle::new));

    @Override
    public void apply(Entity entity, LootContext context) {
        Entity vehicle = this.vehicle.get(context);
        if (vehicle != null) {
            entity.startRiding(vehicle);
        }
    }

    @Override
    public MapCodec<? extends ContextEntityFunction> codec() {
        return MAP_CODEC;
    }
}
