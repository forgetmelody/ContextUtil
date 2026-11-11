package io.github.forgetmelody.contextutil.neoforge;

import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.functions.entities.ContextEntityFunction;
import io.github.forgetmelody.contextutil.functions.entities.ContextEntityFunctions;
import io.github.forgetmelody.contextutil.functions.locationbaseds.ContextLocationBasedFunction;
import io.github.forgetmelody.contextutil.functions.locationbaseds.ContextLocationBasedFunctions;
import io.github.forgetmelody.contextutil.providers.entities.ContextEntityProvider;
import io.github.forgetmelody.contextutil.providers.entities.ContextEntityProviders;
import io.github.forgetmelody.contextutil.providers.identifiers.ContextIdentifierProvider;
import io.github.forgetmelody.contextutil.providers.identifiers.ContextIdentifierProviders;
import io.github.forgetmelody.contextutil.providers.itemstacks.ContextItemStackProvider;
import io.github.forgetmelody.contextutil.providers.itemstacks.ContextItemStackProviders;
import io.github.forgetmelody.contextutil.providers.locations.ContextLocationProvider;
import io.github.forgetmelody.contextutil.providers.locations.ContextLocationProviders;
import io.github.forgetmelody.contextutil.providers.soundevents.ContextSoundEventProvider;
import io.github.forgetmelody.contextutil.providers.soundevents.ContextSoundEventProviders;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import io.github.forgetmelody.contextutil.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@Mod(ContextUtil.MOD_ID)
public final class ContextUtilNeoForge {
    public ContextUtilNeoForge(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(ContextUtilNeoForge::registerRegistries);
        modEventBus.addListener(ContextUtilNeoForge::registerDataPackRegistries);
    }

    private static void registerRegistries(NewRegistryEvent event) {
        event.register(BuiltInRegistries.CONTEXT_ENTITY_FUNCTION_TYPE);
        event.register(BuiltInRegistries.CONTEXT_LOCATION_BASED_FUNCTION_TYPE);
        event.register(BuiltInRegistries.CONTEXT_SOUND_EVENT_PROVIDER_TYPE);
        event.register(BuiltInRegistries.CONTEXT_ITEM_STACK_PROVIDER_TYPE);
        event.register(BuiltInRegistries.CONTEXT_ENTITY_PROVIDER_TYPE);
        event.register(BuiltInRegistries.CONTEXT_LOCATION_PROVIDER_TYPE);
        event.register(BuiltInRegistries.CONTEXT_IDENTIFIER_PROVIDER_TYPE);
    }

    private static void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Registries.CONTEXT_ENTITY_FUNCTION, ContextEntityFunction.DIRECT_CODEC, ContextEntityFunctions.NETWORK_CODEC);
        event.dataPackRegistry(Registries.CONTEXT_LOCATION_BASED_FUNCTION, ContextLocationBasedFunction.DIRECT_CODEC, ContextLocationBasedFunctions.NETWORK_CODEC);
        event.dataPackRegistry(Registries.CONTEXT_SOUND_EVENT_PROVIDER, ContextSoundEventProvider.DIRECT_CODEC, ContextSoundEventProviders.NETWORK_CODEC);
        event.dataPackRegistry(Registries.CONTEXT_ITEM_STACK_PROVIDER, ContextItemStackProvider.DIRECT_CODEC, ContextItemStackProviders.NETWORK_CODEC);
        event.dataPackRegistry(Registries.CONTEXT_ENTITY_PROVIDER, ContextEntityProvider.DIRECT_CODEC, ContextEntityProviders.NETWORK_CODEC);
        event.dataPackRegistry(Registries.CONTEXT_LOCATION_PROVIDER, ContextLocationProvider.DIRECT_CODEC, ContextLocationProviders.NETWORK_CODEC);
        event.dataPackRegistry(Registries.CONTEXT_IDENTIFIER_PROVIDER, ContextIdentifierProvider.DIRECT_CODEC, ContextIdentifierProviders.NETWORK_CODEC);
    }

}
