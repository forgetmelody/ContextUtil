package io.github.forgetmelody.contextutil.providers.soundsources;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public record FromEntity(
        EntityProvider entity
) implements SoundSourceProvider {
    public static final MapCodec<FromEntity> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EntityProvider.DIRECT_CODEC.fieldOf("entity").forGetter(FromEntity::entity)
    ).apply(instance, FromEntity::new));

    @Override
    public @Nullable SoundSource get(LootContext context) {
        Entity entity = this.entity.get(context);
        return entity != null ? entity.getSoundSource() : null;
    }

    @Override
    public MapCodec<? extends SoundSourceProvider> codec() {
        return MAP_CODEC;
    }
}
