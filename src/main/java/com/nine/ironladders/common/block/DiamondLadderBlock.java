package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.LadderType;

public class DiamondLadderBlock extends BaseMetalLadder {

    public DiamondLadderBlock(Settings settings) {
        super(settings, LadderType.DIAMOND);
     }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.diamondLadderSpeedMultiplier;
    }
}
