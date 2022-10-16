package me.emafire003.dev.foxglow;

import me.emafire003.dev.foxglow.util.DataSaver;
import me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat;
import me.emafire003.dev.foxglow.util.Rule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


@Mod(FoxGlow.MOD_ID)
public class FoxGlow {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "foxglow";
	private static boolean april1_on = false;
	private static boolean cgl_loaded = false;
	public static final Path PATH = FMLPaths.CONFIGDIR.get().resolve(MOD_ID);
	private static List<ResourceLocation> glowFoodsList = new ArrayList<>();

	public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public FoxGlow() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		LOGGER.info("Initializing!");
		LocalDate currentDate = LocalDate.now();
		int day = currentDate.getDayOfMonth();
		Month month = currentDate.getMonth();
		if(month.equals(Month.APRIL) && day == 1){
			april1_on = true;
			LOGGER.info("Yes, april 1st");
		}
		cgl_loaded = ModList.get().isLoaded("coloredglowlib");
		if(cgl_loaded){
			ColoredGlowLibCompat.getLib().setPerEntityTypeColor(true);
		}
		glowFoodsList.add(ForgeRegistries.ITEMS.getKey(Items.GLOW_BERRIES));
		getValuesFromFile();

		modEventBus.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static List<ResourceLocation> convertToIdList(List<String> string_list){
		List<ResourceLocation> list = new ArrayList<>();
		for(String item : string_list){
			ResourceLocation item_maybe = ResourceLocation.tryParse(item);
			if(item_maybe != null){
				list.add(item_maybe);
			}
		}
		return list;
	}

	public static List<String> convertFromIdList(List<ResourceLocation> idlist){
		List<String> list = new ArrayList<>();
		for(ResourceLocation item : idlist){
			list.add(item.toString());
		}
		return list;
	}

	private  void getValuesFromFile(){
		try{
			LOGGER.info("Getting variables values from the data file...");
			DataSaver.createFile();
			List<ResourceLocation> food_list = DataSaver.getGlowFoodsList();
			if(food_list != null && !food_list.isEmpty()){
				//This is done because I need to initialize the glowFoodList map with the glow berries by default,
				//but if just added all the entries each time I would be also adding the glow berries, which
				//would continue to multiply inside the list
				food_list.remove(ForgeRegistries.ITEMS.getKey(Items.GLOW_BERRIES));

				glowFoodsList.addAll(food_list);
			}
			LOGGER.info("Done!");
		}catch (Exception e){
			LOGGER.error("There was an error while getting the values from the file onto the mod");
			e.printStackTrace();
		}
	}

	private void commonSetup(final FMLCommonSetupEvent event) {

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
		glowFoodsList.add(ForgeRegistries.ITEMS.getKey(item));
	}

	/**This method will remove a glowfood (an item that will make foxes/player glow)
	 *
	 * @param item The item that will make player/foxes glow*/
	public static void removeGlowFood(Item item){
		glowFoodsList.remove(ForgeRegistries.ITEMS.getKey(item));
	}

	/**This method will return the list of all the items that make
	 * a fox/player glow when eaten.
	*/
	public static List<ResourceLocation> getGlowFoodsList(){
		return glowFoodsList;
	}

	public static final Rule.IntegerRule FOXGLOW_DURATION =
			new Rule.IntegerRule("foxGlowDuration", GameRules.Category.MOBS, 10);

	public static final Rule.BooleanRule PLAYERGLOW =
			new Rule.BooleanRule("doPlayersGlow", GameRules.Category.PLAYER, true);

	public static final Rule.BooleanRule CUSTOM_COLOR_GLOW =
			new Rule.BooleanRule("customColorGlow", GameRules.Category.MISC, true);

	public static final Rule.BooleanRule RANDOM_COLOR_GLOW =
			new Rule.BooleanRule("randomColorGlow", GameRules.Category.MISC, false);


}
