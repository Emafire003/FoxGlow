package me.emafire003.dev.foxglow.compat;

import me.emafire003.dev.coloredglowlib.ColoredGlowLib;
import me.emafire003.dev.coloredglowlib.ColoredGlowLibMod;
import me.emafire003.dev.coloredglowlib.util.Color;
import me.emafire003.dev.foxglow.FoxGlow;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.Random;


public class ColoredGlowLibCompat {

    public static Color foxcolor = new Color(237, 162, 5);
    private static ColoredGlowLib coloredGlowLib = ColoredGlowLibMod.getLib();

    public static ColoredGlowLib getLib(){
        return coloredGlowLib;
    }

    private static Color randomColor(){
        Random random = new Random();
        return new Color(random.nextInt(254)+1, random.nextInt(254)+1, random.nextInt(254)+1);
    }

    private static boolean checkEntityTypeColor(EntityType type){
        return coloredGlowLib.getEntityTypeColor(type).equals(Color.getWhiteColor());
    }

    public static void doColoredGlowLibStuff(Level world, Entity entity){

        if(FoxGlow.CUSTOM_COLOR_GLOW){
            Random random = new Random();
            if(random.nextInt(1005) == 1){
                coloredGlowLib.setRainbowColorToEntity(entity, true);
            }//else if(world.getGameRules().getBoolean(RANDOM_COLOR_GLOW.getRule())){
            else if(FoxGlow.RANDOM_COLOR_GLOW){
                coloredGlowLib.setColorToEntityType(entity.getType(), ColoredGlowLibCompat.randomColor());
            }else{
                coloredGlowLib.setColorToEntityType(entity.getType(), foxcolor);
            }
        }else if(!checkEntityTypeColor(entity.getType())){
            //This is done because the entity would still have another color selected otherwise.
            coloredGlowLib.setColorToEntityType(entity.getType(), Color.getWhiteColor());
        }
    }

}