package com.nine.ironladders.common.block;


import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.utils.LadderType;

public class DiamondLadderBlock extends BaseMetalLadder {
    public DiamondLadderBlock(Properties p_54345_) {
        super(p_54345_, LadderType.DIAMOND);
    }
    @Override
    public int getSpeedMultiplier() {
        return ILConfig.diamondLadderSpeedMultiplier.get();
    }
}
