package io.github.forgetmelody.contextutil.registries;

import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.functions.entities.ContextEntityFunction;
import io.github.forgetmelody.contextutil.functions.locationbaseds.ContextLocationBasedFunction;
import io.github.forgetmelody.contextutil.providers.entities.ContextEntityProvider;
import io.github.forgetmelody.contextutil.providers.identifiers.ContextIdentifierProvider;
import io.github.forgetmelody.contextutil.providers.itemstacks.ContextItemStackProvider;
import io.github.forgetmelody.contextutil.providers.locations.ContextLocationProvider;
import io.github.forgetmelody.contextutil.providers.soundevents.ContextSoundEventProvider;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class BuiltInRegistries {
    public static final Registry<MapCodec<? extends ContextEntityFunction>> CONTEXT_ENTITY_FUNCTION_TYPE = create(Registries.CONTEXT_ENTITY_FUNCTION_TYPE);
    public static final Registry<MapCodec<? extends ContextLocationBasedFunction>> CONTEXT_LOCATION_BASED_FUNCTION_TYPE = create(Registries.CONTEXT_LOCATION_BASED_FUNCTION_TYPE);
    public static final Registry<MapCodec<? extends ContextSoundEventProvider>> CONTEXT_SOUND_EVENT_PROVIDER_TYPE = create(Registries.CONTEXT_SOUND_EVENT_PROVIDER_TYPE);
    public static final Registry<MapCodec<? extends ContextItemStackProvider>> CONTEXT_ITEM_STACK_PROVIDER_TYPE = create(Registries.CONTEXT_ITEM_STACK_PROVIDER_TYPE);
    public static final Registry<MapCodec<? extends ContextEntityProvider>> CONTEXT_ENTITY_PROVIDER_TYPE = create(Registries.CONTEXT_ENTITY_PROVIDER_TYPE);
    public static final Registry<MapCodec<? extends ContextLocationProvider>> CONTEXT_LOCATION_PROVIDER_TYPE = create(Registries.CONTEXT_LOCATION_PROVIDER_TYPE);
    public static final Registry<MapCodec<? extends ContextIdentifierProvider>> CONTEXT_IDENTIFIER_PROVIDER_TYPE = create(Registries.CONTEXT_IDENTIFIER_PROVIDER_TYPE);

    private BuiltInRegistries() {
    }

    public static <T> Registry<T> create(ResourceKey<Registry<T>> key) {
        return new MappedRegistry<>(key, Lifecycle.stable());
    }
}
