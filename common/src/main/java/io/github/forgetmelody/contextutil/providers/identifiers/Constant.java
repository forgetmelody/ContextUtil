package io.github.forgetmelody.contextutil.providers.identifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jspecify.annotations.Nullable;

public record Constant(Identifier value) implements ContextIdentifierProvider {
    public static final Codec<Constant> INLINE_CODEC = Identifier.CODEC.xmap(Constant::new, Constant::value);
    public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("value").forGetter(Constant::value)
    ).apply(instance, Constant::new));

    @Override
    public @Nullable Identifier get(LootContext context) {
        return this.value;
    }

    @Override
    public MapCodec<? extends ContextIdentifierProvider> codec() {
        return MAP_CODEC;
    }
}
