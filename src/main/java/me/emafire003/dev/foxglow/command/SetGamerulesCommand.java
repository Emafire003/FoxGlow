package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.util.DataSaver;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;


public class SetGamerulesCommand implements FoxGlowCommand {



    private int setFoxglowDuration(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        FoxGlow.FOXGLOW_DURATION = IntegerArgumentType.getInteger(context, "value");
        DataSaver.write();
        source.sendSystemMessage(Component.literal("§6[FoxGlow] §fThe foxglow effect duration is now: §b" + FoxGlow.FOXGLOW_DURATION + " §fseconds!"));
        return 1;
    }

    private int setPlayerGlow(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();

        FoxGlow.PLAYERGLOW = BoolArgumentType.getBool(context, "value");
        DataSaver.write();
        if(FoxGlow.PLAYERGLOW){
            source.sendSystemMessage(Component.literal("§6[FoxGlow] §fDo players glow upon eating a glow-food item (like glowberries): §aYES"));
        }else{
            source.sendSystemMessage(Component.literal("§6[FoxGlow] §fDo players glow upon eating a glow-food item (like glowberries): §cNO"));
        }
        return 1;
    }

    private int setCustomColorGlow(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();

        FoxGlow.CUSTOM_COLOR_GLOW = BoolArgumentType.getBool(context, "value");
        DataSaver.write();
        if(FoxGlow.PLAYERGLOW){
            source.sendSystemMessage(Component.literal("§6[FoxGlow] §fDo players/foxes glow a custom color: §aYES"));
        }else{
            source.sendSystemMessage(Component.literal("§6[FoxGlow] §fDo players/foxes glow a custom color: §cNO"));
        }
        return 1;
    }

    private int setRandomColorGlow(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();

        FoxGlow.RANDOM_COLOR_GLOW = BoolArgumentType.getBool(context, "value");
        DataSaver.write();
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
                    Commands.literal("foxglowDuration")
                            .then(
                                    Commands.argument("value", IntegerArgumentType.integer(1))
                            ).executes(this::setFoxglowDuration)
                )
                .then(
                        Commands.literal("doPlayersGlow")
                                .then(
                                        Commands.argument("value", BoolArgumentType.bool())
                                ).executes(this::setPlayerGlow)
                )
                .then(
                        Commands.literal("customColorGlow")
                                .then(
                                        Commands.argument("value", BoolArgumentType.bool())
                                ).executes(this::setCustomColorGlow)
                )
                .then(
                        Commands.literal("randomColorGlow")
                                .then(
                                        Commands.argument("value", BoolArgumentType.bool())
                                ).executes(this::setRandomColorGlow)
                )
                .build();
    }

}
