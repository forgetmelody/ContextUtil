package io.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.phys.Vec3;

public interface LocationBasedFunction extends LootContextUser {
    void apply(Vec3 position, LootContext context);

    MapCodec<? extends LocationBasedFunction> codec();
}
