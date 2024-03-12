package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.LadderType;

public class NetheriteLadderBlock extends BaseMetalLadder {

    public NetheriteLadderBlock(Settings settings) {
        super(settings, LadderType.NETHERITE);
     }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.netheriteLadderSpeedMultiplier;
    }
}
