package me.emafire003.dev.foxglow.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import me.emafire003.dev.foxglow.FoxGlow;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import static me.emafire003.dev.foxglow.FoxGlow.LOGGER;

public class DataSaver {

    public static String PATH = String.valueOf(FoxGlow.PATH+ "/" + FoxGlow.MOD_ID + "_data.json");
    @SuppressWarnings("all")
    public static Type glowFoodsListToken = new TypeToken<List<String>>(){}.getType();
    @SuppressWarnings("all")
    public static Type foxglowDurationToken = new TypeToken<Integer>(){}.getType();
    @SuppressWarnings("all")
    public static Type playerGlowToken = new TypeToken<Boolean>(){}.getType();
    @SuppressWarnings("all")
    public static Type customColorGlowToken = new TypeToken<Boolean>(){}.getType();
    @SuppressWarnings("all")
    public static Type randomColorGlowToken = new TypeToken<Boolean>(){}.getType();

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
            datafileWriter.append(String.valueOf(FoxGlow.FOXGLOW_DURATION));
            datafileWriter.append(String.valueOf(FoxGlow.PLAYERGLOW));
            datafileWriter.append(String.valueOf(FoxGlow.CUSTOM_COLOR_GLOW));
            datafileWriter.append(String.valueOf(FoxGlow.RANDOM_COLOR_GLOW));

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



    public static @Nullable List<ResourceLocation> getGlowFoodsList(){
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

    public static int getFoxglowDuration(){
        try {
            FileReader file = new FileReader(PATH);
            String line = getFileLine(3, file);
            if(line.equalsIgnoreCase("ERROR001-NOLINEFOUND")){
                return 10;
            }
            int value = gson.fromJson(line, foxglowDurationToken);
            return value;
        } catch (NoSuchElementException e){
            return 10;
        } catch (IOException e) {
            LOGGER.error("There was an error trying to read the data on the file!");
            e.printStackTrace();
            return 10;
        } catch (Exception e){
            LOGGER.error("There was an error while reading on the file");
            e.printStackTrace();
            return 10;
        }
    }

    public static boolean getPlayerGlow(){
        try {
            FileReader file = new FileReader(PATH);
            String line = getFileLine(4, file);
            if(line.equalsIgnoreCase("ERROR001-NOLINEFOUND")){
                return true;
            }
            boolean value = gson.fromJson(line, playerGlowToken);
            return value;
        } catch (NoSuchElementException e){
            return true;
        } catch (IOException e) {
            LOGGER.error("There was an error trying to read the data on the file!");
            e.printStackTrace();
            return true;
        } catch (Exception e){
            LOGGER.error("There was an error while reading on the file");
            e.printStackTrace();
            return true;
        }
    }

    public static boolean getCustomColorGlow(){
        try {
            FileReader file = new FileReader(PATH);
            String line = getFileLine(4, file);
            if(line.equalsIgnoreCase("ERROR001-NOLINEFOUND")){
                return true;
            }
            boolean value = gson.fromJson(line, customColorGlowToken);
            return value;
        } catch (NoSuchElementException e){
            return true;
        } catch (IOException e) {
            LOGGER.error("There was an error trying to read the data on the file!");
            e.printStackTrace();
            return true;
        } catch (Exception e){
            LOGGER.error("There was an error while reading on the file");
            e.printStackTrace();
            return true;
        }
    }

    public static boolean getRandomColorGlow(){
        try {
            FileReader file = new FileReader(PATH);
            String line = getFileLine(4, file);
            if(line.equalsIgnoreCase("ERROR001-NOLINEFOUND")){
                return false;
            }
            boolean value = gson.fromJson(line, randomColorGlowToken);
            return value;
        } catch (NoSuchElementException e){
            return false;
        } catch (IOException e) {
            LOGGER.error("There was an error trying to read the data on the file!");
            e.printStackTrace();
            return false;
        } catch (Exception e){
            LOGGER.error("There was an error while reading on the file");
            e.printStackTrace();
            return false;
        }
    }

}
