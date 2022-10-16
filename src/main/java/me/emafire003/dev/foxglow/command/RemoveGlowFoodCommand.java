package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.util.DataSaver;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class RemoveGlowFoodCommand implements FoxGlowCommand {

    CommandBuildContext buildContext;

    public RemoveGlowFoodCommand(CommandBuildContext buildcontext){
        this.buildContext = buildcontext;
    }

    private int addGlowFood(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Item item = ItemArgument.getItem(context, "item").getItem();
        CommandSourceStack source = context.getSource();

        try{
            if(FoxGlow.getGlowFoodsList().contains(ForgeRegistries.ITEMS.getKey(item))){
                FoxGlow.removeGlowFood(item);
                source.sendSystemMessage(Component.literal("§6[FoxGlow] §fThe item §c" + item.toString() + " §fwill no longer make foxes/player glow when eaten!"));
            }else{
                source.sendSystemMessage(Component.literal("§6[FoxGlow] §cThe item §b" + item.toString() + " §cdoes not make foxes/player glow when eaten already!"));
            }
            DataSaver.write();
        }catch (Exception e){
            source.sendSystemMessage(Component.literal("There has been an error while performing the command:"));
            source.sendSystemMessage(Component.literal(e.toString()));
            e.printStackTrace();
        }


        return 1;
    }

    public LiteralCommandNode<CommandSourceStack> getNode() {
        return Commands
                .literal("removeglowfood")
                .then(
                        Commands.argument("item", ItemArgument.item(buildContext))
                                .executes(this::addGlowFood)
                )
                .build();
    }

}
