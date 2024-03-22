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
    NETHERITE();

    LadderType() {}
    public Block getBlock(LadderType type){
        return switch (type){
            case COPPER -> BlockRegistry.COPPER_LADDER;
            case IRON -> BlockRegistry.IRON_LADDER;
            case GOLD -> BlockRegistry.GOLD_LADDER;
            case DIAMOND -> BlockRegistry.DIAMOND_LADDER;
            case NETHERITE -> BlockRegistry.NETHERITE_LADDER;
            default -> Blocks.LADDER;
        };

    }

}