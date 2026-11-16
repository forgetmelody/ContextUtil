package io.github.forgetmelody.contextutil.providers.iteminstances;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jspecify.annotations.Nullable;

import java.util.Set;

public record Tool() implements ItemInstanceProvider {
    public static final Tool INSTANCE = new Tool();
    public static final MapCodec<Tool> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public @Nullable ItemInstance get(LootContext context) {
        return context.getOptionalParameter(LootContextParams.TOOL);
    }

    @Override
    public MapCodec<? extends ItemInstanceProvider> codec() {
        return MAP_CODEC;
    }

    @Override
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.TOOL);
    }
}
