package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.foxglow.FoxGlow;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;


public class GetGamerulesCommand implements FoxGlowCommand {



    private int getFoxglowDuration(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        source.sendSystemMessage(Component.literal("§6[FoxGlow] §fThe foxglow effect duration is currently: §b" + FoxGlow.FOXGLOW_DURATION + " §fseconds!"));
        return 1;
    }

    private int getPlayerGlow(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        if(FoxGlow.PLAYERGLOW){
            source.sendSystemMessage(Component.literal("§6[FoxGlow] §fDo players glow upon eating a glow-food item (like glowberries): §aYES"));
        }else{
            source.sendSystemMessage(Component.literal("§6[FoxGlow] §fDo players glow upon eating a glow-food item (like glowberries): §cNO"));
        }
        return 1;
    }

    private int getCustomColorGlow(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        if(FoxGlow.PLAYERGLOW){
            source.sendSystemMessage(Component.literal("§6[FoxGlow] §fDo players/foxes glow a custom color: §aYES"));
        }else{
            source.sendSystemMessage(Component.literal("§6[FoxGlow] §fDo players/foxes glow a custom color: §cNO"));
        }
        return 1;
    }

    private int getRandomColorGlow(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        if(FoxGlow.PLAYERGLOW){
            source.sendSystemMessage(Component.literal("§6[FoxGlow] §fDo players/foxes glow a §lrandom §fcustom color: §aYES"));
        }else{
            source.sendSystemMessage(Component.literal("§6[FoxGlow] §fDo players/foxes glow a §lrandom §fcustom color: §cNO"));
        }
        return 1;
    }

    public LiteralCommandNode<CommandSourceStack> getNode() {
        return Commands
                .literal("get")
                .then(
                    Commands.literal("foxglowDuration").executes(this::getFoxglowDuration)
                )
                .then(
                        Commands.literal("doPlayersGlow").executes(this::getPlayerGlow)
                )
                .then(
                        Commands.literal("customColorGlow").executes(this::getCustomColorGlow)
                )
                .then(
                        Commands.literal("randomColorGlow").executes(this::getRandomColorGlow)
                )
                .build();
    }

}
