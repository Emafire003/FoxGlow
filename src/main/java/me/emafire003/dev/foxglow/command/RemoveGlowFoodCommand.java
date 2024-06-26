package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.util.DataSaver;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;


public class RemoveGlowFoodCommand implements FoxGlowCommand {

    CommandRegistryAccess registryAccess;

    public RemoveGlowFoodCommand(CommandRegistryAccess registryAccess){
        this.registryAccess = registryAccess;
    }

    private int addGlowFood(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Item item = ItemStackArgumentType.getItemStackArgument(context, "item").getItem();
        ServerCommandSource source = context.getSource();

        try{
            if(FoxGlow.getGlowFoodsList().contains(Registries.ITEM.getId(item))){
                FoxGlow.removeGlowFood(item);
                source.sendFeedback(() -> Text.literal("§6[FoxGlow] §fThe item §c" + item.toString() + " §fwill no longer make foxes/player glow when eaten!"), false);
            }else{
                source.sendFeedback(() -> Text.literal("§6[FoxGlow] §cThe item §b" + item.toString() + " §cdoes not make foxes/player glow when eaten already!"), false);
            }
            DataSaver.write();
        }catch (Exception e){
            source.sendError(Text.literal("There has been an error while performing the command:"));
            source.sendError(Text.literal(e.toString()));
            e.printStackTrace();
        }


        return 1;
    }

    public LiteralCommandNode<ServerCommandSource> getNode() {
        return CommandManager
                .literal("removeglowfood")
                .then(
                        CommandManager.argument("item", ItemStackArgumentType.itemStack(registryAccess))
                                .executes(this::addGlowFood)
                )
                .build();
    }

}
