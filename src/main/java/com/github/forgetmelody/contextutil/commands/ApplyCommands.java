package com.github.forgetmelody.contextutil.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.github.forgetmelody.contextutil.functions.entities.EntityFunction;
import com.github.forgetmelody.contextutil.functions.locationbaseds.LocationBasedFunction;
import com.github.forgetmelody.contextutil.parameters.LootContextParamSets;
import com.github.forgetmelody.contextutil.registries.Registries;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;

public final class ApplyCommands {
    private ApplyCommands() {
    }

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext registries) {
        return Commands.literal("apply").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.literal("entity")
                        .then(Commands.argument("entities", EntityArgument.entities())
                                .then(Commands.argument("function", ResourceArgument.resource(registries, Registries.ENTITY_FUNCTION))
                                        .executes(context -> applyToEntity(
                                                        context.getSource(),
                                                        EntityArgument.getEntities(context, "entities"),
                                                        ResourceArgument.getResource(context, "function", Registries.ENTITY_FUNCTION)
                                                )
                                        )
                                )

                        )
                )
                .then(Commands.literal("location")
                        .then(Commands.argument("position", Vec3Argument.vec3())
                                .then(Commands.argument("function", ResourceArgument.resource(registries, Registries.LOCATION_BASED_FUNCTION))
                                        .executes(context -> applyToLocation(
                                                        context.getSource(),
                                                        Vec3Argument.getVec3(context, "position"),
                                                        ResourceArgument.getResource(context, "function", Registries.LOCATION_BASED_FUNCTION)
                                                )
                                        )
                                )
                        )
                );
    }

    private static int applyToEntity(
            CommandSourceStack source,
            Collection<? extends Entity> entities,
            Holder<EntityFunction> function
    ) {
        ServerLevel level = source.getLevel();
        for (Entity entity : entities) {
            LootParams params = new LootParams.Builder(level)
                    .withParameter(LootContextParams.ORIGIN, entity.position())
                    .withParameter(LootContextParams.THIS_ENTITY, entity)
                    .create(LootContextParamSets.COMMAND_ENTITY_FUNCTION);
            LootContext context = new LootContext.Builder(params).create(function.unwrapKey().map(ResourceKey::identifier));

            function.value().apply(entity, context);
        }

        return entities.size();
    }

    private static int applyToLocation(
            CommandSourceStack source,
            Vec3 position,
            Holder<LocationBasedFunction> function
    ) {
        ServerLevel level = source.getLevel();
        LootParams params = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, position)
                .create(LootContextParamSets.COMMAND_LOCATION_BASED_FUNCTION);
        LootContext context = new LootContext.Builder(params).create(function.unwrapKey().map(ResourceKey::identifier));
        function.value().apply(position, context);

        return 1;
    }
}
