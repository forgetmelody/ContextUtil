package com.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.List;

public record AllOf(List<EntityFunction> functions) implements EntityFunction {
    public static final MapCodec<AllOf> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EntityFunction.DIRECT_CODEC.listOf().fieldOf("functions").forGetter(AllOf::functions)
    ).apply(instance, AllOf::new));

    @Override
    public void apply(Entity entity, LootContext context) {
        for (EntityFunction function : this.functions) {
            function.apply(entity, context);
        }
    }

    @Override
    public MapCodec<? extends EntityFunction> codec() {
        return MAP_CODEC;
    }
}
