package io.github.forgetmelody.contextutil.providers.entities;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import io.github.forgetmelody.contextutil.providers.locations.LocationProvider;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface EntityProvider extends LootContextUser {
    Codec<EntityProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<EntityProvider> dispatched = BuiltInRegistries.ENTITY_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(EntityProvider::codec, Function.identity());
        return Codec.either(Summon.INLINE_CODEC, dispatched).xmap(Either::unwrap, EntityProvider::wrap);
    });
    Codec<EntityProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty::new);

    static EntityProvider empty() {
        return Empty.INSTANCE;
    }

    static EntityProvider summon(EntityType<?> entityType, LocationProvider position, HolderSet<EntityFunction> functions) {
        return new Summon(entityType, position, functions);
    }

    static EntityProvider thisEntity() {
        return ThisEntity.INSTANCE;
    }

    static Either<Summon, EntityProvider> wrap(EntityProvider provider) {
        return provider instanceof Summon summon ? Either.left(summon) : Either.right(provider);
    }

    static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends EntityProvider>> context) {
        context.register(ContextUtil.id("empty"), Empty.MAP_CODEC);
        context.register(ContextUtil.id("summon"), Summon.MAP_CODEC);
        context.register(ContextUtil.id("this_entity"), ThisEntity.MAP_CODEC);
    }

    @Nullable Entity get(LootContext context);

    MapCodec<? extends EntityProvider> codec();

}
