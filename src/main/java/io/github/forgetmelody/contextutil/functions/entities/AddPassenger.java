package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public record AddPassenger(
        EntityProvider passenger
) implements EntityFunction {
    public static final MapCodec<AddPassenger> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EntityProvider.DIRECT_CODEC.fieldOf("passengers").forGetter(AddPassenger::passenger)
    ).apply(instance, AddPassenger::new));

    @Override
    public void apply(Entity entity, LootContext context) {
        Entity passengerEntity = this.passenger.get(context);
        if (passengerEntity != null) {
            passengerEntity.startRiding(entity);
        }
    }

    @Override
    public MapCodec<? extends EntityFunction> codec() {
        return MAP_CODEC;
    }
}
