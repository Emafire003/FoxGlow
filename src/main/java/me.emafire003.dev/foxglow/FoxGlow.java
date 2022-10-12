package me.emafire003.dev.foxglow;

import me.emafire003.dev.coloredglowlib.ColoredGlowLib;
import me.emafire003.dev.coloredglowlib.util.Color;
import me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat;
import me.emafire003.dev.foxglow.mixin.FoodComponentsBerriesMixin;
import me.emafire003.dev.foxglow.mixin.GetBerriesComponentMixin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Items;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;

public class FoxGlow implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static String MOD_ID = "foxglow";
	public static Color foxcolor = new Color(237, 162, 5);
	private static boolean april1_on = false;
	private static boolean cgl_loaded = false;

	public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		FoodComponent berry = new FoodComponent.Builder().alwaysEdible().hunger(2).saturationModifier(0.1F).build();
		FoodComponent alwaysEdibleBerry = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.1F).alwaysEdible().build();
		FoodComponentsBerriesMixin.setEdible(berry);
		LocalDate currentDate = LocalDate.now();
		int day = currentDate.getDayOfMonth();
		Month month = currentDate.getMonth();
		if(month.equals(Month.APRIL) && day == 1){
			april1_on = true;
			LOGGER.info("Yes, april 1st");
		}
		cgl_loaded = FabricLoader.getInstance().isModLoaded("coloredglowlib");
		if(cgl_loaded){
			ColoredGlowLibCompat.getLib().setPerEntityTypeColor(true);
		}
	}

	public static boolean getAP1(){
		return april1_on;
	}

	public static boolean getCGL(){
		return cgl_loaded;
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
