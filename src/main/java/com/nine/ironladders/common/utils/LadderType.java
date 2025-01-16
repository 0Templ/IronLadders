package com.nine.ironladders.common.utils;

import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum LadderType {

    DEFAULT(),
    COPPER(),
    IRON(),
    GOLD(),
    DIAMOND(),
    OBSIDIAN(),
    CRYING_OBSIDIAN(),
    NETHERITE(),
    BEDROCK(),
    BRONZE(),
    LEAD(),
    STEEL(),
    ALUMINUM(),
    SILVER(),
    TIN();

    LadderType() {
    }

    public Block getBlock(LadderType type) {
        return switch (type) {
            case COPPER -> BlockRegistry.COPPER_LADDER.get();
            case IRON -> BlockRegistry.IRON_LADDER.get();
            case GOLD -> BlockRegistry.GOLD_LADDER.get();
            case DIAMOND -> BlockRegistry.DIAMOND_LADDER.get();
            case OBSIDIAN -> BlockRegistry.OBSIDIAN_LADDER.get();
            case CRYING_OBSIDIAN -> BlockRegistry.CRYING_OBSIDIAN_LADDER.get();
            case NETHERITE -> BlockRegistry.NETHERITE_LADDER.get();
            case BRONZE -> BlockRegistry.BRONZE_LADDER.get();
            case LEAD -> BlockRegistry.LEAD_LADDER.get();
            case TIN -> BlockRegistry.TIN_LADDER.get();
            case STEEL -> BlockRegistry.STEEL_LADDER.get();
            case ALUMINUM -> BlockRegistry.ALUMINUM_LADDER.get();
            case SILVER -> BlockRegistry.SILVER_LADDER.get();
            default -> Blocks.LADDER;
        };

    }

}