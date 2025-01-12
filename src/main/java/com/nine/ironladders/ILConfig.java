package com.nine.ironladders;


import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class ILConfig {
    public static final ModConfigSpec COMMON;

    public static ModConfigSpec.IntValue copperLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue ironLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue goldLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue diamondLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue obsidianLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue netheriteLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue tinLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue leadLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue bronzeLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue silverLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue steelLadderSpeedMultiplier;
    public static ModConfigSpec.IntValue aluminumLadderSpeedMultiplier;
    public static ModConfigSpec.BooleanValue enableIronLaddersSpeedMultiplierForPlayersOnly;
    public static ModConfigSpec.BooleanValue enableIronLaddersSpeedMultiplier;
    public static ModConfigSpec.BooleanValue enablePoweredLaddersUpgrade;
    public static ModConfigSpec.BooleanValue enableGlowingLaddersUpgrade;
    public static ModConfigSpec.BooleanValue enableMorphLaddersUpgrade;
    public static ModConfigSpec.BooleanValue enableTierLaddersUpgrade;
    public static ModConfigSpec.BooleanValue enablePoweredLaddersSpeedMultiplierForPlayersOnly;
    public static ModConfigSpec.ConfigValue<List<String>> entitiesPoweredLadderBlackList;
    public static ModConfigSpec.BooleanValue jadeIntegration;
    public static ModConfigSpec.BooleanValue topIntegration;
    public static ModConfigSpec.BooleanValue nonVanillaLadders;
    public static ModConfigSpec.BooleanValue hideUncraftableLadders;

    static {
        final var builder = new ModConfigSpec.Builder();
        builder.push("ladders-behavior");
        entitiesPoweredLadderBlackList = builder.
                comment("Entities in the list will not be affected by the auto-climb effect from Powered Ladders.\nExample: [\"entity.minecraft.trader_llama\"]").
                define("entitiesPoweredLadderBlackList", new ArrayList<>());
        enableIronLaddersSpeedMultiplierForPlayersOnly = builder.
                comment("The acceleration effect from Iron Ladders will only affect players.").
                define("enableIronLaddersSpeedMultiplierForPlayersOnly", false);
        enableIronLaddersSpeedMultiplier = builder.
                comment("Whether the acceleration effect will work at all.").
                define("enableIronLaddersSpeedMultiplier", true);
        enablePoweredLaddersUpgrade = builder.
                comment("Will Power Upgrades work?").
                define("enablePowerLaddersUpgrade", true);
        enableGlowingLaddersUpgrade = builder.
                comment("Will Glowing Upgrades work?").
                define("enableGlowingLaddersUpgrade", true);
        enableMorphLaddersUpgrade = builder.
                comment("Will Morphing Upgrades work?").
                define("enableMorphLaddersUpgrade", true);
        enableTierLaddersUpgrade = builder.
                comment("Will Tier Upgrades work?").
                define("enableTierLaddersUpgrade", true);
        enablePoweredLaddersSpeedMultiplierForPlayersOnly = builder.
                comment("The Powered Ladders affect players only?").
                define("enablePoweredLaddersSpeedMultiplierForPlayersOnly", false);
        builder.pop();
        builder.push("ladders-settings");
        copperLadderSpeedMultiplier = builder.
                comment("Copper Ladder speed multiplier.").
                defineInRange("copperLadderSpeedMultiplier", 5, 0, Integer.MAX_VALUE);
        ironLadderSpeedMultiplier = builder.
                comment("Iron Ladder speed multiplier.").
                defineInRange("ironLadderSpeedMultiplier", 10, 0, Integer.MAX_VALUE);
        goldLadderSpeedMultiplier = builder.
                comment("Golden Ladder speed multiplier.").
                defineInRange("goldLadderSpeedMultiplier", 20, 0, Integer.MAX_VALUE);
        diamondLadderSpeedMultiplier = builder.
                comment("Diamond Ladder speed multiplier.").
                defineInRange("diamondLadderSpeedMultiplier", 28, 0, Integer.MAX_VALUE);
        obsidianLadderSpeedMultiplier = builder.
                comment("Obsidian Ladder speed multiplier.").
                defineInRange("obsidianLadderSpeedMultiplier", 31, 0, Integer.MAX_VALUE);
        netheriteLadderSpeedMultiplier = builder.
                comment("Netherite Ladder speed multiplier.").
                defineInRange("netheriteLadderSpeedMultiplier", 37, 0, Integer.MAX_VALUE);
        aluminumLadderSpeedMultiplier = builder.
                comment("Aluminum Ladder speed multiplier.").
                defineInRange("aluminumLadderSpeedMultiplier", 7, 0, Integer.MAX_VALUE);
        tinLadderSpeedMultiplier = builder.
                comment("Tin Ladder speed multiplier.").
                defineInRange("tinLadderSpeedMultiplier", 8, 0, Integer.MAX_VALUE);
        leadLadderSpeedMultiplier = builder.
                comment("Lead Ladder speed multiplier.").
                defineInRange("leadLadderSpeedMultiplier", 8, 0, Integer.MAX_VALUE);
        bronzeLadderSpeedMultiplier = builder.
                comment("Bronze Ladder speed multiplier.").
                defineInRange("bronzeLadderSpeedMultiplier", 13, 0, Integer.MAX_VALUE);
        steelLadderSpeedMultiplier = builder.
                comment("Steel Ladder speed multiplier.").
                defineInRange("steelLadderSpeedMultiplier", 15, 0, Integer.MAX_VALUE);
        silverLadderSpeedMultiplier = builder.
                comment("Silver Ladder speed multiplier.").
                defineInRange("silverLadderSpeedMultiplier", 22, 0, Integer.MAX_VALUE);
        builder.pop();
        builder.push("other-settings");
        hideUncraftableLadders = builder.
                comment("Will be hidden from the ladders if the material from which they are crafted is not in the game?").
                define("hideUncraftableLadders", true);
        builder.pop();
        builder.push("compatibility-settings");
        jadeIntegration = builder.
                comment("Will information about the condition of the ladders be shown in the tooltip from Jade?").
                define("jadeIntegration", true);
        topIntegration = builder.
                comment("Will information about the condition of the ladders be shown in the tooltip from Jade?").
                define("topIntegration", true);
        nonVanillaLadders = builder.
                comment("Will there be an acceleration effect and also displayed in the tab ladders made of non-vanilla materials (lead, silver, etc.)").
                define("nonVanillaLadders", true);
        COMMON = builder.build();
    }
}
