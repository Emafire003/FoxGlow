package me.emafire003.dev.foxglow;

import me.emafire003.dev.coloredglowlib.ColoredGlowLib;
import me.emafire003.dev.coloredglowlib.util.Color;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

import java.util.Random;

public class FoxGlow implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static String MOD_ID = "foxglow";
	public static Color foxcolor = new Color(237, 162, 5);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		//CrowdinTranslate.downloadTranslations(MOD_ID, MOD_ID);
		ColoredGlowLib.setPerEntityTypeColor(true);

	}

	public static final GameRules.Key<GameRules.IntRule> FOXGLOW_DURATION =
			GameRuleRegistry.register("foxGlowDuration", GameRules.Category.MOBS, GameRuleFactory.createIntRule(10));

	public static final GameRules.Key<GameRules.BooleanRule> PLAYERGLOW =
			GameRuleRegistry.register("doPlayersGlow", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));

	public static final GameRules.Key<GameRules.BooleanRule> CUSTOM_COLOR_GLOW =
			GameRuleRegistry.register("customColorGlow", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

	public static final GameRules.Key<GameRules.BooleanRule> RANDOM_COLOR_GLOW =
			GameRuleRegistry.register("randomColorGlow", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));

}
