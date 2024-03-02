package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
public class GoldLadderBlock extends BaseMetalLadder {

    public GoldLadderBlock(Settings settings) {
        super(settings, LadderType.GOLD);
     }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.goldLadderSpeedMultiplier;
    }
}
