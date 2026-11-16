package io.github.forgetmelody.contextutil.providers.positions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Validatable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record FromEntity(EntityProvider entity) implements PositionProvider {
    public static final MapCodec<FromEntity> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EntityProvider.DIRECT_CODEC.fieldOf("entity").forGetter(FromEntity::entity)
    ).apply(instance, FromEntity::new));

    @Override
    public @Nullable Vec3 get(LootContext context) {
        Entity source = this.entity.get(context);
        return source != null ? source.position() : null;
    }

    @Override
    public MapCodec<? extends PositionProvider> codec() {
        return MAP_CODEC;
    }

    @Override
    public void validate(ValidationContext context) {
        Validatable.validate(context, "entity", this.entity);
    }
}
