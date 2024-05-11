package com.nine.ironladders.common.block;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.utils.LadderType;

public class IronLadderBlock extends BaseMetalLadder {
    public IronLadderBlock(Properties p_54345_) {
        super(p_54345_, LadderType.IRON);
    }
    @Override
    public int getSpeedMultiplier() {
        return ILConfig.ironLadderSpeedMultiplier.get();
    }
}
