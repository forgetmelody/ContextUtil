package io.github.forgetmelody.contextutil.providers.entities;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import org.jetbrains.annotations.Nullable;

public interface EntityProvider extends LootContextUser {
    @Nullable Entity get(LootContext context);

    MapCodec<? extends EntityProvider> codec();
}
