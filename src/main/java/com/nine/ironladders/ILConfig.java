package com.nine.ironladders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ILConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @SerializedName("Copper Ladder speed multiplier.")
    public int copperLadderSpeedMultiplier = 5;
    @SerializedName("Iron Ladder speed multiplier.")
    public int ironLadderSpeedMultiplier = 10;
    @SerializedName("Golden Ladder speed multiplier.")
    public int goldLadderSpeedMultiplier = 15;
    @SerializedName("Diamond Ladder speed multiplier.")
    public int diamondLadderSpeedMultiplier = 20;
    @SerializedName("Netherite Ladder speed multiplier.")
    public int netheriteLadderSpeedMultiplier = 25;


    @SerializedName("Entities in the list will not be affected by the auto-climb effect from Powered Ladders. Example: [\"entity.minecraft.trader_llama\"]")
    public List<String> entitiesPoweredLadderBlackList = new ArrayList<>();
    @SerializedName("The acceleration effect from Iron Ladders will only affect players.")
    public boolean enableIronLaddersSpeedMultiplierForPlayersOnly = false;
    @SerializedName("Whether the acceleration effect will work at all.")
    public boolean enableIronLaddersSpeedMultiplier = true;
    @SerializedName("Will Power Upgrades work?")
    public boolean enablePoweredLaddersUpgrade = true;
    @SerializedName("Will Glowing Upgrades work?")
    public boolean enableGlowingLaddersUpgrade = true;
    @SerializedName("Will Morphing Upgrades work?")
    public boolean enableMorphLaddersUpgrade = true;
    @SerializedName("Will Tier Upgrades work?")
    public boolean enableTierLaddersUpgrade = true;
    @SerializedName("The Powered Ladders affect players only?")
    public boolean enablePoweredLaddersSpeedMultiplierForPlayersOnly = false;



    public static ILConfig loadConfig(File file) {
        ILConfig config;

        if (file.exists() && file.isFile()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                config = GSON.fromJson(bufferedReader, ILConfig.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config", e);
            }
        } else {
            config = new ILConfig();
        }

        config.saveConfig(file);

        return config;
    }

    public void saveConfig(File configFile) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(configFile);
             Writer writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8)) {

            GSON.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config", e);
        }
    }
}