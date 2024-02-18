package com.nine.ironladders;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IronLadders.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ILConfig {
    public static final ForgeConfigSpec COMMON;
    public static ForgeConfigSpec.IntValue copperLadderSpeedMultiplier;
    public static ForgeConfigSpec.IntValue ironLadderSpeedMultiplier;
    public static ForgeConfigSpec.IntValue goldLadderSpeedMultiplier;
    public static ForgeConfigSpec.IntValue diamondLadderSpeedMultiplier;
    public static ForgeConfigSpec.IntValue netheriteLadderSpeedMultiplier;
    static {
        final var builder = new ForgeConfigSpec.Builder();

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

        COMMON = builder.build();
    }
}
