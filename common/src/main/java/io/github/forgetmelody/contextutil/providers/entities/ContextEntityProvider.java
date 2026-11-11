package io.github.forgetmelody.contextutil.providers.entities;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface ContextEntityProvider extends LootContextUser {
    Codec<ContextEntityProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<ContextEntityProvider> dispatched = BuiltInRegistries.CONTEXT_ENTITY_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(ContextEntityProvider::codec, Function.identity());
        return Codec.either(Summon.INLINE_CODEC, dispatched).xmap(Either::unwrap, ContextEntityProvider::wrap);
    });

    static Summon.Builder summon(EntityType<?> entityType) {
        return new Summon.Builder(entityType);
    }

    static Either<Summon, ContextEntityProvider> wrap(ContextEntityProvider provider) {
        return provider instanceof Summon summon ? Either.left(summon) : Either.right(provider);
    }

    @Nullable Entity get(LootContext context);

    MapCodec<? extends ContextEntityProvider> codec();

    @FunctionalInterface
    interface Builder {
        ContextEntityProvider build();
    }
}
