package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.LadderType;

public class GoldLadderBlock extends BaseMetalLadder {

    public GoldLadderBlock(Settings settings) {
        super(settings, LadderType.GOLD);
     }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.goldLadderSpeedMultiplier;
    }
}
