package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.util.DataSaver;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class DataReloadCommand implements FoxGlowCommand {



    private int reload(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        DataSaver.getGlowFoodsList();
        source.sendFeedback(() -> Text.literal("§6[FoxGlow] §fThe data has been reloaded!"), true);
        return 1;
    }

    private int getGlowItems(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        DataSaver.getGlowFoodsList();
        source.sendFeedback(() -> Text.literal("§6[FoxGlow] §fHere is the list of items that currently make foxes/players glow:"), true);
        for(Identifier id : FoxGlow.getGlowFoodsList()){
            source.sendFeedback(() -> Text.literal("§b"+id.toString()), false);
        }
        return 1;
    }

    public LiteralCommandNode<ServerCommandSource> getNode() {
        return CommandManager
                .literal("data")
                .then(
                    CommandManager.literal("reload").executes(this::reload)
                )
                .then(
                        CommandManager.literal("getGlowItems").executes(this::getGlowItems)
                )
                .build();
    }

}
