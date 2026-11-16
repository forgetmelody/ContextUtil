package io.github.forgetmelody.contextutil.providers.numbers;

import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.neoforged.neoforge.registries.RegisterEvent;

public final class NumberProviders {
    public static final NumberProvider ZERO = new ConstantValue(0);

    private NumberProviders() {
    }

    public static NumberProvider constant(float value) {
        return new ConstantValue(value);
    }

    public static NumberProvider random(float min, float max) {
        return new Random(min, max);
    }

    public static NumberProvider random(float bound) {
        return new Random(bound);
    }

    public static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends NumberProvider>> context) {
        context.register(ContextUtil.id("random"), Random.MAP_CODEC);
    }
}
