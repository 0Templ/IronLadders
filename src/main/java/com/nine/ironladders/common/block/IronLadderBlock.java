package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.LadderType;

public class IronLadderBlock extends BaseMetalLadder {

    public IronLadderBlock(Settings settings) {
        super(settings, LadderType.IRON);
     }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.ironLadderSpeedMultiplier;
    }
}
