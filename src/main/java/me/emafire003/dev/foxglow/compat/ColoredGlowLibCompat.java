package me.emafire003.dev.foxglow.compat;

import me.emafire003.dev.coloredglowlib.ColoredGlowLibAPI;
import me.emafire003.dev.coloredglowlib.ColoredGlowLibMod;
import me.emafire003.dev.coloredglowlib.util.ColorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import static me.emafire003.dev.foxglow.FoxGlow.*;


public class ColoredGlowLibCompat {

    public static String foxcolor = "#eda205";
    public static String snowfoxcolor = "#a3e9ef";

    private static String randomColor(Random random){
        return ColorUtils.toHex(random.nextInt(254)+1, random.nextInt(254)+1, random.nextInt(254)+1);
    }

    public static void doColoredGlowLibStuff(World world, Entity entity){
        ColoredGlowLibAPI coloredGlowLib = ColoredGlowLibMod.getAPI();
        if(coloredGlowLib == null){
            return;
        }

        if(world.getGameRules().getBoolean(CUSTOM_COLOR_GLOW)){
            Random random = entity.getEntityWorld().getRandom();
            if(random.nextInt(1005) == 1){
                coloredGlowLib.setRainbowColor(entity);
            }else if(world.getGameRules().getBoolean(RANDOM_COLOR_GLOW)){
                coloredGlowLib.setColor(entity.getType(), ColoredGlowLibCompat.randomColor(random));
            }else{
                if(entity instanceof FoxEntity){
                    if(((FoxEntity) entity).getVariant().asString().equals("snow")){
                        coloredGlowLib.setColor(entity, snowfoxcolor);
                    }else{
                        coloredGlowLib.setColor(entity, foxcolor);
                    }
                }
            }
        }else if(coloredGlowLib.hasCustomColor(entity)){
            //This is done because the entity would still have another color selected otherwise.
            coloredGlowLib.clearColor(entity, false);
        }

    }

}