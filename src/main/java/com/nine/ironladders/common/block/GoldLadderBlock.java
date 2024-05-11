package com.nine.ironladders.common.block;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.utils.LadderType;

public class GoldLadderBlock extends BaseMetalLadder {
    public GoldLadderBlock(Properties p_54345_) {
        super(p_54345_, LadderType.GOLD);
    }
    @Override
    public int getSpeedMultiplier() {
        return ILConfig.goldLadderSpeedMultiplier.get();
    }
}
