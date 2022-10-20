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
import net.minecraft.resources.ResourceLocation;


public class ConfigCommand implements FoxGlowCommand {



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

    private int setFoxglowDuration(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        FoxGlow.FOXGLOW_DURATION = IntegerArgumentType.getInteger(context, "val");
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

//Commands.argument("entity", EntitySummonArgumentType.entitySummon()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).then(((RequiredArgumentBuilder)Commands.argument("color", StringArgumentType.string())
     public LiteralCommandNode<CommandSourceStack> getNode() {
        return Commands
                .literal("config")
                .then(
                        Commands.literal("set")
                                .then(
                                        Commands.literal("foxglowDuration")
                                                .then(
                                                        Commands.argument("value", IntegerArgumentType.integer(1))
                                                                .executes(this::setFoxglowDuration)
                                                )
                                )
                )
                .then(
                        Commands.literal("set")
                                .then(
                                        Commands.literal("doPlayersGlow")
                                                .then(
                                                        Commands.argument("value", BoolArgumentType.bool())
                                                                .executes(this::setPlayerGlow)
                                                )
                                )
                )
                .then(
                        Commands.literal("set")
                                .then(
                                        Commands.literal("customColorGlow")
                                                .then(
                                                        Commands.argument("value", BoolArgumentType.bool())
                                                                .executes(this::setCustomColorGlow)
                                                )
                                )
                )
                .then(
                        Commands.literal("set")
                                .then(
                                        Commands.literal("randomColorGlow")
                                                .then(
                                                        Commands.argument("value", BoolArgumentType.bool())
                                                                .executes(this::setRandomColorGlow)
                                                )
                                )
                )
                .then(
                        Commands.literal("get")
                                .then(
                                        Commands.literal("foxglowDuration").executes(this::getFoxglowDuration)

                                )
                )
                .then(
                        Commands.literal("get")
                                .then(
                                        Commands.literal("doPlayersGlow").executes(this::getPlayerGlow)

                                )
                )
                .then(
                        Commands.literal("get")
                                .then(
                                        Commands.literal("customColorGlow").executes(this::getCustomColorGlow)

                                )
                )
                .then(
                        Commands.literal("get")
                                .then(
                                        Commands.literal("randomColorGlow").executes(this::getRandomColorGlow)

                                )
                )
                .then(
                        Commands.literal("reload")
                                .executes(this::reload)
                )
                .then(
                        Commands.literal("getGlowItems").executes(this::getGlowItems)
                )
                .build();
    }


}
