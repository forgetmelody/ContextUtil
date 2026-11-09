package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;

public interface EntityFunction extends LootContextUser {

    void apply(Entity entity, LootContext context);

    MapCodec<? extends EntityFunction> codec();
}
