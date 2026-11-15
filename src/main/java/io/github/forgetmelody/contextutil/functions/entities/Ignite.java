package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public record Ignite(
        NumberProvider duration
) implements ContextEntityFunction {
    public static final MapCodec<Ignite> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            NumberProviders.CODEC.fieldOf("duration").forGetter(Ignite::duration)
    ).apply(instance, Ignite::new));

    @Override
    public void apply(Entity entity, LootContext context) {
        entity.igniteForSeconds(Math.max(0.0F, this.duration.getFloat(context)));
    }

    @Override
    public MapCodec<? extends ContextEntityFunction> codec() {
        return MAP_CODEC;
    }
}
