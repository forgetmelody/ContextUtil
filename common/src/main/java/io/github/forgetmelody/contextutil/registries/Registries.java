package io.github.forgetmelody.contextutil.registries;

import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import io.github.forgetmelody.contextutil.functions.locationbaseds.LocationBasedFunction;
import io.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import io.github.forgetmelody.contextutil.providers.itemstacks.ItemStackProvider;
import io.github.forgetmelody.contextutil.providers.locations.LocationProvider;
import io.github.forgetmelody.contextutil.providers.soundevents.SoundEventProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class Registries {
    public static final ResourceKey<Registry<MapCodec<? extends EntityFunction>>> ENTITY_FUNCTION_TYPE = key("entity_function_type");
    public static final ResourceKey<Registry<MapCodec<? extends LocationBasedFunction>>> LOCATION_BASED_FUNCTION_TYPE = key("location_based_function_type");
    public static final ResourceKey<Registry<MapCodec<? extends SoundEventProvider>>> SOUND_EVENT_PROVIDER_TYPE = key("sound_event_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends ItemStackProvider>>> ITEM_STACK_PROVIDER_TYPE = key("item_stack_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends EntityProvider>>> ENTITY_PROVIDER_TYPE = key("entity_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends LocationProvider>>>LOCATION_PROVIDER_TYPE = key("location_provider_type");
    public static final ResourceKey<Registry<EntityFunction>> ENTITY_FUNCTION = key("entity_function");
    public static final ResourceKey<Registry<LocationBasedFunction>> LOCATION_BASED_FUNCTION = key("location_based_function");
    public static final ResourceKey<Registry<SoundEventProvider>> SOUND_EVENT_PROVIDER = key("sound_event_provider");
    public static final ResourceKey<Registry<ItemStackProvider>> ITEM_STACK_PROVIDER = key("item_stack_provider");
    public static final ResourceKey<Registry<EntityProvider>> ENTITY_PROVIDER = key("entity_provider");
    public static final ResourceKey<Registry<LocationProvider>> LOCATION_PROVIDER = key("location_provider");

    private Registries() {
    }

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ContextUtil.id(name));
    }
}
