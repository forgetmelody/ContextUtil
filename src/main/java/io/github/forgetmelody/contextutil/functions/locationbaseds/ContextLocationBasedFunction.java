package io.github.forgetmelody.contextutil.functions.locationbaseds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public interface ContextLocationBasedFunction extends LootContextUser {
    Codec<ContextLocationBasedFunction> DIRECT_CODEC = Codec.lazyInitialized(
            () -> BuiltInRegistries.CONTEXT_LOCATION_BASED_FUNCTION_TYPE
                    .byNameCodec()
                    .dispatch(ContextLocationBasedFunction::codec, Function.identity())
    );
    Codec<HolderSet<ContextLocationBasedFunction>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.CONTEXT_LOCATION_BASED_FUNCTION, DIRECT_CODEC);
    Codec<Holder<ContextLocationBasedFunction>> REFERENCE_CODEC = RegistryFileCodec.create(Registries.CONTEXT_LOCATION_BASED_FUNCTION, DIRECT_CODEC);
    Codec<ContextLocationBasedFunction> NETWORK_CODEC = MapCodec.unitCodec(Empty.INSTANCE);

    void apply(Vec3 position, LootContext context);

    MapCodec<? extends ContextLocationBasedFunction> codec();
}
