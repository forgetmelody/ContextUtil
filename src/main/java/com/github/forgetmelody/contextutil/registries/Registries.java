package com.github.forgetmelody.contextutil.registries;

import com.mojang.serialization.MapCodec;
import com.github.forgetmelody.contextutil.ContextUtil;
import com.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import com.github.forgetmelody.contextutil.functions.locationbaseds.LocationBasedFunction;
import com.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import com.github.forgetmelody.contextutil.providers.identifiers.IdentifierProvider;
import com.github.forgetmelody.contextutil.providers.iteminstances.ItemInstanceProvider;
import com.github.forgetmelody.contextutil.providers.positions.PositionProvider;
import com.github.forgetmelody.contextutil.providers.soundevents.SoundEventProvider;
import com.github.forgetmelody.contextutil.providers.soundsources.SoundSourceProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class Registries {
    // Fixed Registry Key
    public static final ResourceKey<Registry<MapCodec<? extends EntityFunction>>> ENTITY_FUNCTION_TYPE = key("entity_function_type");
    public static final ResourceKey<Registry<MapCodec<? extends LocationBasedFunction>>> LOCATION_BASED_FUNCTION_TYPE = key("location_based_function_type");
    public static final ResourceKey<Registry<MapCodec<? extends SoundEventProvider>>> SOUND_EVENT_PROVIDER_TYPE = key("sound_event_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends SoundSourceProvider>>> SOUND_SOURCE_PROVIDER_TYPE = key("sound_source_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends ItemInstanceProvider>>> ITEM_STACK_PROVIDER_TYPE = key("item_stack_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends EntityProvider>>> ENTITY_PROVIDER_TYPE = key("entity_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends PositionProvider>>> POSITION_PROVIDER_TYPE = key("position_provider_type");
    public static final ResourceKey<Registry<MapCodec<? extends IdentifierProvider>>> IDENTIFIER_PROVIDER_TYPE = key("identifier_provider_type");
    // Dynamic (DataPack Registry Key)
    public static final ResourceKey<Registry<EntityFunction>> ENTITY_FUNCTION = key("entity_function");
    public static final ResourceKey<Registry<LocationBasedFunction>> LOCATION_BASED_FUNCTION = key("location_based_function");
    public static final ResourceKey<Registry<SoundEventProvider>> SOUND_EVENT_PROVIDER = key("sound_event_provider");
    public static final ResourceKey<Registry<SoundSourceProvider>> SOUND_SOURCE_PROVIDER = key("sound_source_provider");
    public static final ResourceKey<Registry<ItemInstanceProvider>> ITEM_STACK_PROVIDER = key("item_stack_provider");
    public static final ResourceKey<Registry<EntityProvider>> ENTITY_PROVIDER = key("entity_provider");
    public static final ResourceKey<Registry<PositionProvider>> POSITION_PROVIDER = key("position_provider");
    public static final ResourceKey<Registry<IdentifierProvider>> IDENTIFIER_PROVIDER = key("identifier_provider");

    private Registries() {
    }

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ContextUtil.id(name));
    }
}
