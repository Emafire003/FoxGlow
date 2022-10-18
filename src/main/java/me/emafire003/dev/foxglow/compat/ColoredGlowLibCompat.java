package me.emafire003.dev.foxglow.compat;

import me.emafire003.dev.coloredglowlib.ColoredGlowLib;
import me.emafire003.dev.coloredglowlib.ColoredGlowLibMod;
import me.emafire003.dev.coloredglowlib.util.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import static me.emafire003.dev.foxglow.FoxGlow.*;


public class ColoredGlowLibCompat {

    public static Color foxcolor = new Color(237, 162, 5);
    private static ColoredGlowLib coloredGlowLib = ColoredGlowLibMod.getLib();

    public static ColoredGlowLib getLib(){
        return coloredGlowLib;
    }

    private static Color randomColor(Random random){
        return new Color(random.nextInt(254)+1, random.nextInt(254)+1, random.nextInt(254)+1);
    }

    private static boolean checkEntityTypeColor(EntityType type){
        return coloredGlowLib.getEntityTypeColor(type).equals(Color.getWhiteColor());
    }

    public static void doColoredGlowLibStuff(World world, Entity entity){
        if(world.getGameRules().getBoolean(CUSTOM_COLOR_GLOW)){
            Random random = entity.getEntityWorld().getRandom();
            if(random.nextInt(1005) == 1){
                coloredGlowLib.setRainbowColorToEntity(entity, true);
            }else if(world.getGameRules().getBoolean(RANDOM_COLOR_GLOW)){
                coloredGlowLib.setColorToEntityType(entity.getType(), ColoredGlowLibCompat.randomColor(random));
            }else{
                coloredGlowLib.setColorToEntityType(entity.getType(), foxcolor);
            }
        }else if(!checkEntityTypeColor(entity.getType())){
            //This is done because the entity would still have another color selected otherwise.
            coloredGlowLib.setColorToEntityType(entity.getType(), Color.getWhiteColor());
        }
    }

}