package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.coloredglowlib.command.CGLCommand;
import me.emafire003.dev.coloredglowlib.command.ConfigCommand;
import me.emafire003.dev.coloredglowlib.command.SetGlowColorCommand;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class FoxGlowCommands {


    //Based on Factions' code https://github.com/ickerio/factions
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralCommandNode<ServerCommandSource> fox_commands = CommandManager
                .literal("foxglow")
                .requires(serverCommandSource -> {
                    return serverCommandSource.hasPermissionLevel(2);
                })
                .build();

        dispatcher.getRoot().addChild(fox_commands);

        FoxGlowCommand[] commands = new FoxGlowCommand[] {
                new AddGlowFoodCommand(registryAccess),
                new RemoveGlowFoodCommand(registryAccess),
                new DataReloadCommand()
        };

        for (FoxGlowCommand command : commands) {
            fox_commands.addChild(command.getNode());
        }
    }
}
