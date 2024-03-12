package com.nine.ironladders.common.utils;

import net.minecraft.block.BlockState;

public class BlockStateUtils {
    public static BlockState getStateWithSyncedProps(BlockState state, BlockState state_1){
        return state.with(LadderProperties.WATERLOGGED, state_1.get(LadderProperties.WATERLOGGED))
                .with(LadderProperties.LIGHTED, state_1.get(LadderProperties.LIGHTED))
                .with(LadderProperties.POWERED, state_1.get(LadderProperties.POWERED))
                .with(LadderProperties.HIDDEN, state_1.get(LadderProperties.HIDDEN))
                .with(LadderProperties.HAS_SIGNAL, state_1.get(LadderProperties.HAS_SIGNAL))
                .with(LadderProperties.MORPH_TYPE, state_1.get(LadderProperties.MORPH_TYPE))
                .with(LadderProperties.FACING, state_1.get(LadderProperties.FACING));
    }
    public static BlockState getStateWithSyncedPropsNoP(BlockState state, BlockState state_1){
        return state.with(LadderProperties.WATERLOGGED, state_1.get(LadderProperties.WATERLOGGED))
                .with(LadderProperties.LIGHTED, state_1.get(LadderProperties.LIGHTED))
                .with(LadderProperties.POWERED, state_1.get(LadderProperties.POWERED))
                .with(LadderProperties.HIDDEN, state_1.get(LadderProperties.HIDDEN))
                .with(LadderProperties.MORPH_TYPE, state_1.get(LadderProperties.MORPH_TYPE))
                .with(LadderProperties.FACING, state_1.get(LadderProperties.FACING));
    }

}
