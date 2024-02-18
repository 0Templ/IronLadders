package com.nine.ironladders.common.block;

import com.nine.ironladders.ILConfig;
public class GoldLadderBlock extends BaseMetalLadder {
    public GoldLadderBlock(Properties p_54345_) {
        super(p_54345_, LadderType.GOLD);
    }
    @Override
    public int getSpeedMultiplier() {
        return ILConfig.goldLadderSpeedMultiplier.get();
    }
}
