package com.github.forgetmelody.contextutil.providers.positions;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

public record Random(
        PositionSource x,
        PositionSource y,
        PositionSource z
) implements PositionProvider {
    public static final MapCodec<Random> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            PositionSource.CODEC.optionalFieldOf("x", PositionSource.ZERO).forGetter(Random::x),
            PositionSource.CODEC.optionalFieldOf("y", PositionSource.ZERO).forGetter(Random::y),
            PositionSource.CODEC.optionalFieldOf("z", PositionSource.ZERO).forGetter(Random::z)
    ).apply(instance, Random::new));

    public static Random of(double boundX, double boundY, double boundZ) {
        return new Random(
                new PositionSource(boundX),
                new PositionSource(boundY),
                new PositionSource(boundZ)
        );
    }

    @Override
    public Vec3 get(LootContext context) {
        RandomSource random = context.getRandom();
        double x = this.x.get(random);
        double y = this.y.get(random);
        double z = this.z.get(random);
        return new Vec3(x, y, z);
    }

    @Override
    public MapCodec<? extends PositionProvider> codec() {
        return MAP_CODEC;
    }

    public record PositionSource(double min, double max) {
        public static final PositionSource ZERO = new PositionSource(0.0, 0.0);

        private PositionSource(double bound) {
            if (bound <= 1) throw new IllegalArgumentException("bound must be greater than 1");
            this(0, bound);
        }

        public static final Codec<PositionSource> INLINE_CODEC = Codec.DOUBLE.xmap(PositionSource::new, PositionSource::max);
        public static final Codec<PositionSource> FULL_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.DOUBLE.fieldOf("min").forGetter(PositionSource::min),
                Codec.DOUBLE.fieldOf("max").forGetter(PositionSource::max)
        ).apply(instance, PositionSource::new));
        public static final Codec<PositionSource> CODEC = Codec.either(INLINE_CODEC, FULL_CODEC).xmap(Either::unwrap, PositionSource::wrap);

        private static Either<PositionSource, PositionSource> wrap(PositionSource source) {
            return source.min == 0 ? Either.left(source) : Either.right(source);
        }

        public double get(RandomSource random) {
            double bound = this.max - this.min;
            return (random.nextDouble() * bound + this.min) * (random.nextBoolean() ? 1 : -1);
        }
    }
}
