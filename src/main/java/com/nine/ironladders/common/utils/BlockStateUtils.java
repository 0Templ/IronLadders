package com.nine.ironladders.common.utils;

import net.minecraft.world.level.block.state.BlockState;

public class BlockStateUtils {
    public static BlockState getStateWithSyncedProps(BlockState state, BlockState state_1){
        return state.setValue(LadderProperties.WATERLOGGED, state_1.getValue(LadderProperties.WATERLOGGED))
                .setValue(LadderProperties.LIGHTED, state_1.getValue(LadderProperties.LIGHTED))
                .setValue(LadderProperties.POWERED, state_1.getValue(LadderProperties.POWERED))
                .setValue(LadderProperties.HIDDEN, state_1.getValue(LadderProperties.HIDDEN))
                .setValue(LadderProperties.HAS_SIGNAL, state_1.getValue(LadderProperties.HAS_SIGNAL))
                .setValue(LadderProperties.MORPH_TYPE, state_1.getValue(LadderProperties.MORPH_TYPE))
                .setValue(LadderProperties.FACING, state_1.getValue(LadderProperties.FACING));
    }
    public static BlockState getStateWithSyncedPropsNoP(BlockState state, BlockState state_1){
        return state.setValue(LadderProperties.WATERLOGGED, state_1.getValue(LadderProperties.WATERLOGGED))
                .setValue(LadderProperties.LIGHTED, state_1.getValue(LadderProperties.LIGHTED))
                .setValue(LadderProperties.POWERED, state_1.getValue(LadderProperties.POWERED))
                .setValue(LadderProperties.HIDDEN, state_1.getValue(LadderProperties.HIDDEN))
                .setValue(LadderProperties.MORPH_TYPE, state_1.getValue(LadderProperties.MORPH_TYPE))
                .setValue(LadderProperties.FACING, state_1.getValue(LadderProperties.FACING));
    }

}

