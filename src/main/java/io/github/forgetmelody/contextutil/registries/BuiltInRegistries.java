package io.github.forgetmelody.contextutil.registries;

import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import io.github.forgetmelody.contextutil.functions.locationbaseds.LocationBasedFunction;
import io.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import io.github.forgetmelody.contextutil.providers.identifiers.IdentifierProvider;
import io.github.forgetmelody.contextutil.providers.itemstacks.ItemStackProvider;
import io.github.forgetmelody.contextutil.providers.positions.PositionProvider;
import io.github.forgetmelody.contextutil.providers.soundevents.SoundEventProvider;
import io.github.forgetmelody.contextutil.providers.soundsources.SoundSourceProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class BuiltInRegistries {
    public static final Registry<MapCodec<? extends EntityFunction>> ENTITY_FUNCTION_TYPE = create(Registries.ENTITY_FUNCTION_TYPE);
    public static final Registry<MapCodec<? extends LocationBasedFunction>> LOCATION_BASED_FUNCTION_TYPE = create(Registries.LOCATION_BASED_FUNCTION_TYPE);
    public static final Registry<MapCodec<? extends SoundEventProvider>> SOUND_EVENT_PROVIDER_TYPE = create(Registries.SOUND_EVENT_PROVIDER_TYPE);
    public static final Registry<MapCodec<? extends SoundSourceProvider>> SOUND_SOURCE_PROVIDER_TYPE = create(Registries.SOUND_SOURCE_PROVIDER_TYPE);
    public static final Registry<MapCodec<? extends ItemStackProvider>> ITEM_STACK_PROVIDER_TYPE = create(Registries.ITEM_STACK_PROVIDER_TYPE);
    public static final Registry<MapCodec<? extends EntityProvider>> ENTITY_PROVIDER_TYPE = create(Registries.ENTITY_PROVIDER_TYPE);
    public static final Registry<MapCodec<? extends PositionProvider>> POSITION_PROVIDER_TYPE = create(Registries.POSITION_PROVIDER_TYPE);
    public static final Registry<MapCodec<? extends IdentifierProvider>> IDENTIFIER_PROVIDER_TYPE = create(Registries.IDENTIFIER_PROVIDER_TYPE);

    private BuiltInRegistries() {
    }

    public static <T> Registry<T> create(ResourceKey<Registry<T>> key) {
        return new RegistryBuilder<>(key).create();
    }
}
