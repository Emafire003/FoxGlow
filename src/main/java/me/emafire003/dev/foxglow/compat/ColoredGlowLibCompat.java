package me.emafire003.dev.foxglow.compat;

import me.emafire003.dev.coloredglowlib.ColoredGlowLib;
import me.emafire003.dev.coloredglowlib.ColoredGlowLibMod;
import me.emafire003.dev.coloredglowlib.util.Color;

public class ColoredGlowLibCompat {

    public static Color foxcolor = new Color(237, 162, 5);
    public static ColoredGlowLib getLib(){
        return ColoredGlowLibMod.getLib();
    }

}