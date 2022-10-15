package me.emafire003.dev.foxglow;

import me.emafire003.dev.coloredglowlib.command.CGLCommands;
import me.emafire003.dev.coloredglowlib.util.Color;
import me.emafire003.dev.foxglow.command.FoxGlowCommands;
import me.emafire003.dev.foxglow.util.DataSaver;
import me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static me.emafire003.dev.coloredglowlib.ColoredGlowLibMod.LOGGER;

public class FoxGlow implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static String MOD_ID = "foxglow";
	public static Color foxcolor = new Color(237, 162, 5);
	private static boolean april1_on = false;
	private static boolean cgl_loaded = false;
	public static final Path PATH = FabricLoader.getInstance().getConfigDir();
	private static List<Identifier> glowFoodsList = new ArrayList<>();

	public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing!");
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
		getValuesFromFile();
		CommandRegistrationCallback.EVENT.register(FoxGlowCommands::registerCommands);
	}

	public static List<Identifier> convertToIdList(List<String> string_list){
		List<Identifier> list = new ArrayList<>();
		for(String item : string_list){
			Identifier item_maybe = Identifier.tryParse(item);
			if(item_maybe != null){
				list.add(item_maybe);
			}
		}
		return list;
	}

	public static List<String> convertFromIdList(List<Identifier> idlist){
		List<String> list = new ArrayList<>();
		for(Identifier item : idlist){
			list.add(item.toString());
		}
		return list;
	}

	private  void getValuesFromFile(){
		try{
			LOGGER.info("Getting variables values from the data file...");
			DataSaver.createFile();
			List<Identifier> food_list = DataSaver.getGlowFoodsList();
			glowFoodsList.add(Registry.ITEM.getId(Items.GLOW_BERRIES));
			if(food_list != null && !food_list.isEmpty()){
				//This is done because I need to initialize the glowFoodList map with the glow berries by default,
				//but if just added all the entries each time I would be also adding the glow berries, which
				//would continue to multiply inside the list
				if(food_list.contains(Registry.ITEM.getId(Items.GLOW_BERRIES))){
					food_list.remove(Registry.ITEM.getId(Items.GLOW_BERRIES));
				}
				glowFoodsList.addAll(food_list);
			}
			LOGGER.info("Done!");
		}catch (Exception e){
			LOGGER.error("There was an error while getting the values from the file onto the mod");
			e.printStackTrace();
		}
	}

	public static boolean getAP1(){
		return april1_on;
	}

	public static boolean getCGL(){
		return cgl_loaded;
	}

	/**This method will add a new glowfood (an item that will make foxes/player glow)
	 *
	 * @param item The item that will make player/foxes glow*/
	public static void addGlowFood(Item item){
		glowFoodsList.add(Registry.ITEM.getId(item));
	}

	/**This method will remove a glowfood (an item that will make foxes/player glow)
	 *
	 * @param item The item that will make player/foxes glow*/
	public static void removeGlowFood(Item item){
		glowFoodsList.remove(Registry.ITEM.getId(item));
	}

	/**This method will return the list of all the items that make
	 * a fox/player glow when eaten.
	*/
	public static List<Identifier> getGlowFoodsList(){
		return glowFoodsList;
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
