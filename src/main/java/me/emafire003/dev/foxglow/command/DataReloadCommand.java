package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.util.DataSaver;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;


public class DataReloadCommand implements FoxGlowCommand {



    private int reload(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        DataSaver.getGlowFoodsList();
        source.sendSystemMessage(Component.literal("§6[FoxGlow] §fThe data has been reloaded!"));
        return 1;
    }

    private int getGlowItems(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        DataSaver.getGlowFoodsList();
        source.sendSystemMessage(Component.literal("§6[FoxGlow] §fHere is the list of items that currently make foxes/players glow:"));
        for(ResourceLocation id : FoxGlow.getGlowFoodsList()){
            source.sendSystemMessage(Component.literal("§b"+id.toString()));
        }
        return 1;
    }

    public LiteralCommandNode<CommandSourceStack> getNode() {
        return Commands
                .literal("data")
                .then(
                    Commands.literal("reload").executes(this::reload)
                )
                .then(
                        Commands.literal("getGlowItems").executes(this::getGlowItems)
                )
                .build();
    }

}
