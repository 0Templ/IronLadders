package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
public class DiamondLadderBlock extends BaseMetalLadder {

    public DiamondLadderBlock(Settings settings) {
        super(settings, LadderType.DIAMOND);
     }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.diamondLadderSpeedMultiplier;
    }
}
