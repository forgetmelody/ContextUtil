package io.github.forgetmelody.contextutil.providers.locations;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface LocationProvider extends LootContextUser {

    @Nullable Vec3 getPosition(LootContext context);

    MapCodec<? extends LocationProvider> codec();

    default @Nullable BlockPos getBlockPos(LootContext context) {
        Vec3 position = this.getPosition(context);
        return position != null ? BlockPos.containing(position) : null;
    }
}
