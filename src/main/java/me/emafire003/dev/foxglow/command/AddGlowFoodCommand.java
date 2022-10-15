package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.foxglow.FoxGlow;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;


public class AddGlowFoodCommand implements FoxGlowCommand {

    CommandRegistryAccess registryAccess;

    public AddGlowFoodCommand(CommandRegistryAccess registryAccess){
        this.registryAccess = registryAccess;
    }

    private int addGlowFood(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Item item = ItemStackArgumentType.getItemStackArgument(context, "item").getItem();
        ServerCommandSource source = context.getSource();
        try{
            if(FoxGlow.getGlowFoodsList().contains(Registry.ITEM.getId(item))){
                source.sendFeedback(Text.literal("§6[FoxGlow] §cThe item §b" + item.toString() + " §calready make foxes/player glow when eaten!"), false);
            }else{
                FoxGlow.addGlowFood(item);
                source.sendFeedback(Text.literal("§6[FoxGlow] §fThe item §a" + item.toString() + " §fwill now make foxes/player glow when eaten!"), false);
            }
        }catch (Exception e){
            source.sendError(Text.literal("There has been an error while performing the command:"));
            source.sendError(Text.literal(e.toString()));
            e.printStackTrace();
        }


        return 1;
    }

    public LiteralCommandNode<ServerCommandSource> getNode() {
        return CommandManager
                .literal("addglowfood")
                .then(
                        CommandManager.argument("item", ItemStackArgumentType.itemStack(registryAccess))
                                .executes(this::addGlowFood)
                )
                .build();
    }

}
