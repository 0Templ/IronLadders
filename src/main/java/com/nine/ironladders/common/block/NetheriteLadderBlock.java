package com.nine.ironladders.common.block;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.utils.LadderType;

public class NetheriteLadderBlock extends BaseMetalLadder {
    public NetheriteLadderBlock(Properties p_54345_) {
        super(p_54345_, LadderType.NETHERITE);
    }
    @Override
    public int getSpeedMultiplier() {
        return ILConfig.netheriteLadderSpeedMultiplier.get();
    }
}
