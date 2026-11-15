package io.github.forgetmelody.contextutil.registries;

import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.functions.entities.ContextEntityFunction;
import io.github.forgetmelody.contextutil.functions.locationbaseds.ContextLocationBasedFunction;
import io.github.forgetmelody.contextutil.providers.entities.ContextEntityProvider;
import io.github.forgetmelody.contextutil.providers.identifiers.ContextIdentifierProvider;
import io.github.forgetmelody.contextutil.providers.itemstacks.ContextItemStackProvider;
import io.github.forgetmelody.contextutil.providers.locations.ContextLocationProvider;
import io.github.forgetmelody.contextutil.providers.soundevents.ContextSoundEventProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class Registries {
    // Fixed Registry Key
    public static final ResourceKey<Registry<MapCodec<? extends ContextEntityFunction>>> CONTEXT_ENTITY_FUNCTION_TYPE = key("context_entity_function_type");
    public static final ResourceKey<Registry<MapCodec<? extends ContextLocationBasedFunction>>> CONTEXT_LOCATION_BASED_FUNCTION_TYPE = key("context_location_based_function_type");
    public static final ResourceKey<Registry<MapCodec<? extends ContextSoundEventProvider>>> CONTEXT_SOUND_EVENT_PROVIDER_TYPE = key("context_sound_event_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends ContextItemStackProvider>>> CONTEXT_ITEM_STACK_PROVIDER_TYPE = key("context_item_stack_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends ContextEntityProvider>>> CONTEXT_ENTITY_PROVIDER_TYPE = key("context_entity_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends ContextLocationProvider>>> CONTEXT_LOCATION_PROVIDER_TYPE = key("context_location_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends ContextIdentifierProvider>>> CONTEXT_IDENTIFIER_PROVIDER_TYPE = key("context_identifier_provider_type");
    // Dynamic (DataPack Registry Key)
    public static final ResourceKey<Registry<ContextEntityFunction>> CONTEXT_ENTITY_FUNCTION = key("context_entity_function");
    public static final ResourceKey<Registry<ContextLocationBasedFunction>> CONTEXT_LOCATION_BASED_FUNCTION = key("context_location_based_function");
    public static final ResourceKey<Registry<ContextSoundEventProvider>> CONTEXT_SOUND_EVENT_PROVIDER = key("context_sound_event_provider");
    public static final ResourceKey<Registry<ContextItemStackProvider>> CONTEXT_ITEM_STACK_PROVIDER = key("context_item_stack_provider");
    public static final ResourceKey<Registry<ContextEntityProvider>> CONTEXT_ENTITY_PROVIDER = key("context_entity_provider");
    public static final ResourceKey<Registry<ContextLocationProvider>> CONTEXT_LOCATION_PROVIDER = key("context_location_provider");
    public static final ResourceKey<Registry<ContextIdentifierProvider>> CONTEXT_IDENTIFIER_PROVIDER = key("context_identifier_provider");

    private Registries() {
    }

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ContextUtil.id(name));
    }
}
