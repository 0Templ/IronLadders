package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
public class NetheriteLadderBlock extends BaseMetalLadder {

    public NetheriteLadderBlock(Settings settings) {
        super(settings, LadderType.NETHERITE);
     }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.netheriteLadderSpeedMultiplier;
    }
}
