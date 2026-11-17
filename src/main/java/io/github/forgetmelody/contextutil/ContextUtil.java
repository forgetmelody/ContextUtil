package io.github.forgetmelody.contextutil;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import io.github.forgetmelody.contextutil.commands.ApplyCommands;
import io.github.forgetmelody.contextutil.data.DataPackRegistryObjects;
import io.github.forgetmelody.contextutil.functions.entities.*;
import io.github.forgetmelody.contextutil.functions.locationbaseds.LocationBasedFunction;
import io.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import io.github.forgetmelody.contextutil.providers.identifiers.IdentifierProvider;
import io.github.forgetmelody.contextutil.providers.iteminstances.ItemInstanceProvider;
import io.github.forgetmelody.contextutil.providers.numbers.NumberProviders;
import io.github.forgetmelody.contextutil.providers.positions.PositionProvider;
import io.github.forgetmelody.contextutil.providers.soundevents.SoundEventProvider;
import io.github.forgetmelody.contextutil.providers.soundsources.SoundSourceProvider;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import io.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod(ContextUtil.MOD_ID)
public class ContextUtil {
    public static final String MOD_ID = "context_util";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ContextUtil(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(ContextUtil::registerRegistries);
        modEventBus.addListener(ContextUtil::registerDataPackRegistries);
        modEventBus.addListener(ContextUtil::registerContents);
        modEventBus.addListener(ContextUtil::registerDataProviders);
        NeoForge.EVENT_BUS.addListener(ContextUtil::registerCommands);
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }

    private static void registerRegistries(NewRegistryEvent event) {
        event.register(BuiltInRegistries.ENTITY_FUNCTION_TYPE);
        event.register(BuiltInRegistries.LOCATION_BASED_FUNCTION_TYPE);
        event.register(BuiltInRegistries.SOUND_EVENT_PROVIDER_TYPE);
        event.register(BuiltInRegistries.SOUND_SOURCE_PROVIDER_TYPE);
        event.register(BuiltInRegistries.ITEM_STACK_PROVIDER_TYPE);
        event.register(BuiltInRegistries.ENTITY_PROVIDER_TYPE);
        event.register(BuiltInRegistries.POSITION_PROVIDER_TYPE);
        event.register(BuiltInRegistries.IDENTIFIER_PROVIDER_TYPE);
    }

    private static void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Registries.ENTITY_FUNCTION, EntityFunction.DIRECT_CODEC, EntityFunction.NETWORK_CODEC);
        event.dataPackRegistry(Registries.LOCATION_BASED_FUNCTION, LocationBasedFunction.DIRECT_CODEC, LocationBasedFunction.NETWORK_CODEC);
        event.dataPackRegistry(Registries.SOUND_EVENT_PROVIDER, SoundEventProvider.DIRECT_CODEC, SoundEventProvider.NETWORK_CODEC);
        event.dataPackRegistry(Registries.SOUND_SOURCE_PROVIDER, SoundSourceProvider.DIRECT_CODEC, SoundSourceProvider.NETWORK_CODEC);
        event.dataPackRegistry(Registries.ITEM_STACK_PROVIDER, ItemInstanceProvider.DIRECT_CODEC, ItemInstanceProvider.NETWORK_CODEC);
        event.dataPackRegistry(Registries.ENTITY_PROVIDER, EntityProvider.DIRECT_CODEC, EntityProvider.NETWORK_CODEC);
        event.dataPackRegistry(Registries.POSITION_PROVIDER, PositionProvider.DIRECT_CODEC, PositionProvider.NETWORK_CODEC);
        event.dataPackRegistry(Registries.IDENTIFIER_PROVIDER, IdentifierProvider.DIRECT_CODEC, IdentifierProvider.NETWORK_CODEC);
    }

    private static void registerContents(RegisterEvent event) {
        event.register(Registries.ENTITY_FUNCTION_TYPE, EntityFunction::bootstrap);
        event.register(Registries.LOCATION_BASED_FUNCTION_TYPE, LocationBasedFunction::bootstrap);
        event.register(Registries.SOUND_EVENT_PROVIDER_TYPE, SoundEventProvider::bootstrap);
        event.register(Registries.SOUND_SOURCE_PROVIDER_TYPE, SoundSourceProvider::bootstrap);
        event.register(Registries.ENTITY_PROVIDER_TYPE, EntityProvider::bootstrap);
        event.register(Registries.IDENTIFIER_PROVIDER_TYPE, IdentifierProvider::bootstrap);
        event.register(Registries.ITEM_STACK_PROVIDER_TYPE, ItemInstanceProvider::bootstrap);
        event.register(Registries.POSITION_PROVIDER_TYPE, PositionProvider::bootstrap);
        event.register(net.minecraft.core.registries.Registries.LOOT_NUMBER_PROVIDER_TYPE, NumberProviders::bootstrap);
    }

    private static void registerDataProviders(GatherDataEvent.Client event) {
        event.createDatapackRegistryObjects(DataPackRegistryObjects.BUILDER);
    }

    private static void registerCommands(RegisterCommandsEvent event) {
        CommandBuildContext registries = event.getBuildContext();
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal(ContextUtil.MOD_ID)
                        .then(ApplyCommands.register(registries))
        );
    }
}
