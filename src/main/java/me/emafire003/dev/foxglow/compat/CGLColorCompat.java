package me.emafire003.dev.foxglow.compat;

import me.emafire003.dev.coloredglowlib.ColoredGlowLib;
import me.emafire003.dev.coloredglowlib.ColoredGlowLibMod;
import me.emafire003.dev.coloredglowlib.util.Color;
import net.minecraft.util.math.random.Random;

public class CGLColorCompat {

    public static Color getWhiteColor(){
        return Color.getWhiteColor();
    }
    public static Color fromHex(String color){
        return Color.translateFromHEX(color);
    }
    public static String toHex(int r, int g, int b){
        return Color.translateToHEX(r,g,b);
    }
    public static Color randomColor(Random random){
       return new Color(random.nextInt(254)+1, random.nextInt(254)+1, random.nextInt(254)+1);
    }

    public static ColoredGlowLib getLib(){
        return ColoredGlowLibMod.getLib();
    }

}