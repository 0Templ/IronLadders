package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
public class IronLadderBlock extends BaseMetalLadder {

    public IronLadderBlock(Settings settings) {
        super(settings, LadderType.IRON);
     }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.ironLadderSpeedMultiplier;
    }
}
