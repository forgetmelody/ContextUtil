package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Validatable;

import java.util.List;

public final class ContextEntityFunctions {
    private static final Empty EMPTY = new Empty();
    public static final Codec<ContextEntityFunction> NETWORK_CODEC = MapCodec.unitCodec(EMPTY);

    private ContextEntityFunctions() {
    }

    public static Codec<List<ContextEntityFunction>> validatedListCodec(ContextKeySet paramSet) {
        return ContextEntityFunction.DIRECT_CODEC.listOf().validate(Validatable.listValidatorForContext(paramSet));
    }

    public static Codec<ContextEntityFunction> validatedCodec(ContextKeySet paramSet) {
        return ContextEntityFunction.DIRECT_CODEC.validate(Validatable.validatorForContext(paramSet));
    }

    private record Empty() implements ContextEntityFunction {
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(EMPTY);

        @Override
        public void apply(Entity entity, LootContext context) {

        }

        @Override
        public MapCodec<? extends ContextEntityFunction> codec() {
            return MAP_CODEC;
        }
    }
}
