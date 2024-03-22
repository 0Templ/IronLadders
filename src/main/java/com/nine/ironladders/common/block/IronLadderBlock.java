package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.LadderType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class IronLadderBlock extends BaseMetalLadder {

    public IronLadderBlock(BlockBehaviour.Properties settings) {
        super(settings, LadderType.IRON);
     }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.ironLadderSpeedMultiplier;
    }
}
