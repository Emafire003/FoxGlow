package me.emafire003.dev.foxglow.events;

import me.emafire003.dev.coloredglowlib.ColoredGlowLib;
import me.emafire003.dev.coloredglowlib.ColoredGlowLibMod;
import me.emafire003.dev.coloredglowlib.command.CGLCommands;
import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.command.FoxGlowCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import static me.emafire003.dev.coloredglowlib.ColoredGlowLibMod.LOGGER;

public class FoxGlowEvents {
    @Mod.EventBusSubscriber(modid = FoxGlow.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onCommandsRegister(RegisterCommandsEvent event) {
            new FoxGlowCommands(event.getDispatcher(), event.getBuildContext());
            ConfigCommand.register(event.getDispatcher());
        }
    }

}
