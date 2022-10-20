package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class FoxGlowCommands {

    //Originally based on Factions' code https://github.com/ickerio/factions, modified by me (Emafire003) a lot to make it compatible with forge
    public FoxGlowCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        LiteralCommandNode<CommandSourceStack> fox_commands = Commands
                .literal("foxglow")
                .requires(serverCommandSource -> {
                    return serverCommandSource.hasPermission(2);
                })
                .build();

        dispatcher.getRoot().addChild(fox_commands);

        FoxGlowCommand[] commands = new FoxGlowCommand[] {
                new AddGlowFoodCommand(buildContext),
                new RemoveGlowFoodCommand(buildContext),
                new ConfigCommand()
        };

        for (FoxGlowCommand command : commands) {
            fox_commands.addChild(command.getNode());
        }

    }

}
