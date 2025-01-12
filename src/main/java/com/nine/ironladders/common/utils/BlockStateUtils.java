package com.nine.ironladders.common.utils;

import net.minecraft.world.level.block.state.BlockState;

public class BlockStateUtils {

    public static BlockState getStateWithSyncedPropsNoP(BlockState state, BlockState state_1) {
        return state.setValue(LadderProperties.WATERLOGGED, state_1.getValue(LadderProperties.WATERLOGGED))
                .setValue(LadderProperties.LIGHTED, state_1.getValue(LadderProperties.LIGHTED))
                .setValue(LadderProperties.POWERED, state_1.getValue(LadderProperties.POWERED))
                .setValue(LadderProperties.HIDDEN, state_1.getValue(LadderProperties.HIDDEN))
                .setValue(LadderProperties.FACING, state_1.getValue(LadderProperties.FACING));
    }

}
