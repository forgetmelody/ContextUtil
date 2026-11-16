package io.github.forgetmelody.contextutil.providers.positions;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public record Origin() implements PositionProvider {
    public static final Origin INSTANCE = new Origin();
    public static final MapCodec<Origin> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public Vec3 get(LootContext context) {
        return context.getOptionalParameter(LootContextParams.ORIGIN);
    }

    @Override
    public MapCodec<? extends PositionProvider> codec() {
        return MAP_CODEC;
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.ORIGIN);
    }
}
