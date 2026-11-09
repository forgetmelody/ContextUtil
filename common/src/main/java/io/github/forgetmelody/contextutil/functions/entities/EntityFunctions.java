package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import io.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Validatable;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.List;
import java.util.function.Function;

public final class EntityFunctions {
    public static final Codec<EntityFunction> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.ENTITY_FUNCTION_TYPE
                    .byNameCodec()
                    .dispatch(EntityFunction::codec, Function.identity())
    );
    public static final Codec<EntityFunction> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);
    public static final Codec<Holder<EntityFunction>> REFERENCE_CODEC = RegistryFileCodec.create(Registries.ENTITY_FUNCTION, DIRECT_CODEC);
    public static final Codec<HolderSet<EntityFunction>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.ENTITY_FUNCTION, DIRECT_CODEC);

    private EntityFunctions() {
    }

    public static Codec<List<EntityFunction>> listValidatedCodec(ContextKeySet paramSet) {
        return DIRECT_CODEC.listOf().validate(Validatable.listValidatorForContext(paramSet));
    }

    public static Codec<EntityFunction> validatedCodec(ContextKeySet paramSet) {
        return DIRECT_CODEC.validate(Validatable.validatorForContext(paramSet));
    }

    public static EntityFunction ignite(NumberProvider duration) {
        return new Ignite(duration);
    }

    private record Empty() implements EntityFunction {
        public static final Empty INSTANCE = new Empty();
        public static final MapCodec<Empty> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public void apply(Entity entity, LootContext context) {

        }

        @Override
        public MapCodec<? extends EntityFunction> codec() {
            return MAP_CODEC;
        }
    }
}
