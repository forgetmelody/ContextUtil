package io.github.forgetmelody.contextutil.functions.positions;

import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.phys.Vec3;

public interface ContextPositionFunction extends LootContextUser {
    Vec3 get(LootContext context);

    MapCodec<? extends EntityFunction> codec();
}
