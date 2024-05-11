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

    public Block getBlock(LadderType type){
        return switch (type){
            case COPPER -> BlockRegistry.COPPER_LADDER.get();
            case IRON -> BlockRegistry.IRON_LADDER.get();
            case GOLD -> BlockRegistry.GOLD_LADDER.get();
            case DIAMOND -> BlockRegistry.DIAMOND_LADDER.get();
            case NETHERITE -> BlockRegistry.NETHERITE_LADDER.get();
            default -> Blocks.LADDER;
        };

    }

}