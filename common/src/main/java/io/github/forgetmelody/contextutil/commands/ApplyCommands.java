package io.github.forgetmelody.contextutil.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public final class ApplyCommands {
    private ApplyCommands() {
    }

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext registries) {
        return Commands.literal("apply");
    }
}
