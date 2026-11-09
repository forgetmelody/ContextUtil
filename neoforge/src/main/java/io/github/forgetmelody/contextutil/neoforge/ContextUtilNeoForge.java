package io.github.forgetmelody.contextutil.neoforge;

import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunctions;
import io.github.forgetmelody.contextutil.functions.locationbaseds.LocationBasedFunctions;
import io.github.forgetmelody.contextutil.providers.entities.EntityProviders;
import io.github.forgetmelody.contextutil.providers.itemstacks.ItemStackProvider;
import io.github.forgetmelody.contextutil.providers.itemstacks.ItemStackProviders;
import io.github.forgetmelody.contextutil.providers.locations.LocationProvider;
import io.github.forgetmelody.contextutil.providers.locations.LocationProviders;
import io.github.forgetmelody.contextutil.providers.soundevents.SoundEventProvider;
import io.github.forgetmelody.contextutil.providers.soundevents.SoundEventProviders;
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
        event.register(BuiltInRegistries.ENTITY_FUNCTION_TYPE);
        event.register(BuiltInRegistries.LOCATION_BASED_FUNCTION_TYPE);
        event.register(BuiltInRegistries.SOUND_EVENT_PROVIDER_TYPE);
        event.register(BuiltInRegistries.ITEM_STACK_PROVIDER_TYPE);
        event.register(BuiltInRegistries.ENTITY_PROVIDER_TYPE);
        event.register(BuiltInRegistries.LOCATION_PROVIDER_TYPE);
    }

    private static void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Registries.ENTITY_FUNCTION, EntityFunctions.DIRECT_CODEC, EntityFunctions.NETWORK_CODEC);
        event.dataPackRegistry(Registries.LOCATION_BASED_FUNCTION, LocationBasedFunctions.DIRECT_CODEC, LocationBasedFunctions.NETWORK_CODEC);
        event.dataPackRegistry(Registries.SOUND_EVENT_PROVIDER, SoundEventProviders.DIRECT_CODEC, SoundEventProviders.NETWORK_CODEC);
        event.dataPackRegistry(Registries.ITEM_STACK_PROVIDER, ItemStackProviders.DIRECT_CODEC, ItemStackProviders.NETWORK_CODEC);
        event.dataPackRegistry(Registries.ENTITY_PROVIDER, EntityProviders.DIRECT_CODEC, EntityProviders.NETWORK_CODEC);
        event.dataPackRegistry(Registries.LOCATION_PROVIDER, LocationProviders.DIRECT_CODEC, LocationProviders.NETWORK_CODEC);
    }

}
