package me.emafire003.dev.foxglow.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import me.emafire003.dev.coloredglowlib.ColoredGlowLibMod;
import me.emafire003.dev.coloredglowlib.util.Color;
import me.emafire003.dev.foxglow.FoxGlow;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import static me.emafire003.dev.coloredglowlib.ColoredGlowLibMod.LOGGER;

public class DataSaver {

    public static String PATH = String.valueOf(FoxGlow.PATH+ "/" + FoxGlow.MOD_ID + "_data.json");
    @SuppressWarnings("all")
    public static Type glowFoodsListToken = new TypeToken<List<String>>(){}.getType();
    @SuppressWarnings("all")

    static Gson gson = new Gson();

    public static void createFile() {
        try {
            File datafile = new File(PATH);
            if (datafile.createNewFile()) {
                write();
            }
        } catch (IOException e) {
            LOGGER.error("There was an error trying to save the data on the file! (First time saving it)");
            e.printStackTrace();
        }
    }

    public static void write() {
        try {
            FileWriter datafileWriter = new FileWriter(PATH);
            String head = gson.toJson("FoxGlow data. DO NOT ADD ANY OTHER LINES, ONLY EDIT INSIDE THE LIST OR USE THE COMMANDS IN-GAME!") +"\n";
            String glowFoodsList = gson.toJson(FoxGlow.convertFromIdList(FoxGlow.getGlowFoodsList())) + "\n";

            datafileWriter.write(head);
            datafileWriter.append(glowFoodsList);

            datafileWriter.close();
        } catch (IOException e) {
            LOGGER.error("There was an error trying to save the data on the file!");
            e.printStackTrace();
        } catch (Exception e){
            LOGGER.error("There was an error while writing on the file");
            e.printStackTrace();
        }
    }


    public static String getFileLine(int line, FileReader file) throws IOException {
        int lineNumber;
        BufferedReader readbuffer = new BufferedReader(file);
        for (lineNumber = 1; lineNumber < 10; lineNumber++) {
            if (lineNumber == line) {
                String the_line = readbuffer.readLine();
                return the_line;
            } else{
                readbuffer.readLine();
            }
        }
        return "ERROR001-NOLINEFOUND";
    }



    public static @Nullable List<Identifier> getGlowFoodsList(){
        try {
            FileReader file = new FileReader(PATH);
            String line = getFileLine(2, file);
            if(line.equalsIgnoreCase("ERROR001-NOLINEFOUND")){
                return null;
            }
            List<String> list = gson.fromJson(line, glowFoodsListToken);
            return FoxGlow.convertToIdList(list);
        } catch (NoSuchElementException e){
            return null;
        } catch (IOException e) {
            LOGGER.error("There was an error trying to read the data on the file!");
            e.printStackTrace();
            return null;
        } catch (Exception e){
            LOGGER.error("There was an error while reading on the file");
            e.printStackTrace();
            return null;
        }
    }

}
