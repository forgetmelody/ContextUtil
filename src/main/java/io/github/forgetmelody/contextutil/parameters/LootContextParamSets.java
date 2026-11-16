package io.github.forgetmelody.contextutil.parameters;

import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public final class LootContextParamSets {
    public static final ContextKeySet COMMAND_ENTITY_FUNCTION = new ContextKeySet.Builder()
            .required(LootContextParams.ORIGIN)
            .required(LootContextParams.THIS_ENTITY)
            .build();
    public static final ContextKeySet COMMAND_LOCATION_BASED_FUNCTION = new ContextKeySet.Builder()
            .required(LootContextParams.ORIGIN)
            .build();
    private LootContextParamSets() {
    }
}
