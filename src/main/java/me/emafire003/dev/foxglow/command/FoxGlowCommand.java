package me.emafire003.dev.foxglow.command;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.ServerCommandSource;


//Based on Factions' code https://github.com/ickerio/factions
public interface FoxGlowCommand {
    LiteralCommandNode<ServerCommandSource> getNode();

}
