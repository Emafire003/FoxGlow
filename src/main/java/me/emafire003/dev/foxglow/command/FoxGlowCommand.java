package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;


//Based on Factions' code https://github.com/ickerio/factions
public interface FoxGlowCommand {
    LiteralCommandNode<CommandSourceStack> getNode();

}
