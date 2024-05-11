package com.nine.ironladders;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = IronLadders.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ILConfig {
    public static final ForgeConfigSpec COMMON;
    public static ForgeConfigSpec.IntValue copperLadderSpeedMultiplier;
    public static ForgeConfigSpec.IntValue ironLadderSpeedMultiplier;
    public static ForgeConfigSpec.IntValue goldLadderSpeedMultiplier;
    public static ForgeConfigSpec.IntValue diamondLadderSpeedMultiplier;
    public static ForgeConfigSpec.IntValue netheriteLadderSpeedMultiplier;
    public static ForgeConfigSpec.BooleanValue enableIronLaddersSpeedMultiplierForPlayersOnly;
    public static ForgeConfigSpec.BooleanValue enableIronLaddersSpeedMultiplier;
    public static ForgeConfigSpec.BooleanValue enablePoweredLaddersUpgrade;
    public static ForgeConfigSpec.BooleanValue enableGlowingLaddersUpgrade;
    public static ForgeConfigSpec.BooleanValue enableMorphLaddersUpgrade;
    public static ForgeConfigSpec.BooleanValue enableTierLaddersUpgrade;
    public static ForgeConfigSpec.BooleanValue enablePoweredLaddersSpeedMultiplierForPlayersOnly;
    public static ForgeConfigSpec.ConfigValue<List<String>> entitiesPoweredLadderBlackList;
    public static ForgeConfigSpec.BooleanValue jadeIntegration;
    public static ForgeConfigSpec.BooleanValue topIntegration;

    static {
        final var builder = new ForgeConfigSpec.Builder();
        builder.push("ladders-behavior");
        entitiesPoweredLadderBlackList = builder.
                comment("Entities in the list will not be affected by the auto-climb effect from Powered Ladders.\nExample: [\"entity.minecraft.trader_llama\"]").
                define("entitiesPoweredLadderBlackList",  new ArrayList<>());
        enableIronLaddersSpeedMultiplierForPlayersOnly = builder.
                comment("The acceleration effect from Iron Ladders will only affect players.").
                define("enableIronLaddersSpeedMultiplierForPlayersOnly",  false);
        enableIronLaddersSpeedMultiplier = builder.
                comment("Whether the acceleration effect will work at all.").
                define("enableIronLaddersSpeedMultiplier",  true);
        enablePoweredLaddersUpgrade = builder.
                comment("Will Power Upgrades work?").
                define("enablePowerLaddersUpgrade",  true);
        enableGlowingLaddersUpgrade = builder.
                comment("Will Glowing Upgrades work?").
                define("enableGlowingLaddersUpgrade",  true);
        enableMorphLaddersUpgrade = builder.
                comment("Will Morphing Upgrades work?").
                define("enableMorphLaddersUpgrade",  true);
        enableTierLaddersUpgrade = builder.
                comment("Will Tier Upgrades work?").
                define("enableTierLaddersUpgrade",  true);
        enablePoweredLaddersSpeedMultiplierForPlayersOnly = builder.
                comment("The Powered Ladders affect players only?").
                define("enablePoweredLaddersSpeedMultiplierForPlayersOnly",  false);
        builder.pop();
        builder.push("ladders-settings");
        copperLadderSpeedMultiplier = builder.
                comment("Copper Ladder speed multiplier.").
                defineInRange("copperLadderSpeedMultiplier", 5,0,Integer.MAX_VALUE);
        ironLadderSpeedMultiplier = builder.
                comment("Iron Ladder speed multiplier.").
                defineInRange("ironLadderSpeedMultiplier", 10,0,Integer.MAX_VALUE);
        goldLadderSpeedMultiplier = builder.
                comment("Golden Ladder speed multiplier.").
                defineInRange("goldLadderSpeedMultiplier", 15,0,Integer.MAX_VALUE);
        diamondLadderSpeedMultiplier = builder.
                comment("Diamond Ladder speed multiplier.").
                defineInRange("diamondLadderSpeedMultiplier", 20,0,Integer.MAX_VALUE);
        netheriteLadderSpeedMultiplier = builder.
                comment("Netherite Ladder speed multiplier.").
                defineInRange("netheriteLadderSpeedMultiplier", 25,0,Integer.MAX_VALUE);
        builder.pop();
        builder.push("compatibility-settings");
        jadeIntegration = builder.
                comment("Will information about the condition of the ladders be shown in the tooltip from Jade?").
                define("jadeIntegration",  true);
        topIntegration = builder.
                comment("Will information about the condition of the ladders be shown in the tooltip from Jade?").
                define("topIntegration",  true);
        COMMON = builder.build();
    }
}
