package io.github.forgetmelody.contextutil.providers.numbers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public record Random(float min, float max) implements NumberProvider {
    public Random(float bound) {
        if (bound <= 0) throw new IllegalArgumentException("bound must be greater than zero");
        this(0, bound);
    }

    public static final MapCodec<Random> MAP_CODEC = RecordCodecBuilder.<Random>mapCodec(instance -> instance.group(
            Codec.FLOAT.fieldOf("min").forGetter(Random::min),
            Codec.FLOAT.fieldOf("max").forGetter(Random::max)
    ).apply(instance, Random::new)).validate(random -> {
        if (random.max <= random.min) {
            return DataResult.error(() -> "max must be greater than min");
        }

        return DataResult.success(random);
    });

    @Override
    public float getFloat(LootContext context) {
        float bound = this.max - this.min;
        return context.getRandom().nextFloat() * bound + this.min;
    }

    @Override
    public MapCodec<? extends NumberProvider> codec() {
        return MAP_CODEC;
    }
}
