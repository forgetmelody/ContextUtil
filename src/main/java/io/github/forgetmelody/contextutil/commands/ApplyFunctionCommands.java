package io.github.forgetmelody.contextutil.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import io.github.forgetmelody.contextutil.functions.entities.ContextEntityFunction;
import io.github.forgetmelody.contextutil.parameters.LootContextParamSets;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Collection;

public final class ApplyFunctionCommands {
    private ApplyFunctionCommands() {
    }

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext registries) {
        return Commands.literal("apply_function").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(Commands.literal("entity")
                        .then(Commands.argument("entities", EntityArgument.entities())
                                .then(Commands.argument("function", ResourceArgument.resource(registries, Registries.CONTEXT_ENTITY_FUNCTION))
                                        .executes(context -> applyToEntity(
                                                        context.getSource(),
                                                        EntityArgument.getEntities(context, "entities"),
                                                        ResourceArgument.getResource(context, "function", Registries.CONTEXT_ENTITY_FUNCTION)
                                                )
                                        )
                                )

                        )
                );
    }

    private static int applyToEntity(
            CommandSourceStack source,
            Collection<? extends Entity> entities,
            Holder<ContextEntityFunction> function
    ) {
        ServerLevel level = source.getLevel();
        for (Entity entity : entities) {
            LootParams params = new LootParams.Builder(level)
                    .withParameter(LootContextParams.ORIGIN, entity.position())
                    .withParameter(LootContextParams.THIS_ENTITY, entity)
                    .create(LootContextParamSets.COMMAND_APPLY_ENTITY_FUNCTION);
            LootContext context = new LootContext.Builder(params).create(function.unwrapKey().map(ResourceKey::identifier));

            function.value().apply(entity, context);
        }

        return entities.size();
    }
}
