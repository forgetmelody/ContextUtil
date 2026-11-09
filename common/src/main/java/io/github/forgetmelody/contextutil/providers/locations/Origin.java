package io.github.forgetmelody.contextutil.providers.locations;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record Origin(Optional<Vec3> offset) implements LocationProvider {
    public static final Origin EMPTY = new Origin(Optional.empty());
    public static final MapCodec<Origin> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Vec3.CODEC.optionalFieldOf("offset").forGetter(Origin::offset)
    ).apply(instance, Origin::new));

    @Override
    public @Nullable Vec3 getPosition(LootContext context) {
        Vec3 origin = context.getOptionalParameter(LootContextParams.ORIGIN);
        return origin != null ? this.offset.map(origin::add).orElse(origin) : null;
    }

    @Override
    public MapCodec<? extends LocationProvider> codec() {
        return MAP_CODEC;
    }
}
