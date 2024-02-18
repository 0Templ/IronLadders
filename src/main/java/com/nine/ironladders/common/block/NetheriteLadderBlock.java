package com.nine.ironladders.common.block;

import com.nine.ironladders.ILConfig;
public class NetheriteLadderBlock extends BaseMetalLadder {
    public NetheriteLadderBlock(Properties p_54345_) {
        super(p_54345_, LadderType.NETHERITE);
    }
    @Override
    public int getSpeedMultiplier() {
        return ILConfig.netheriteLadderSpeedMultiplier.get();
    }
}
