package com.nine.ironladders.common.block;

import com.nine.ironladders.IronLadders;
import net.minecraft.block.Oxidizable;
public class CopperLadderBlock extends BaseMetalLadder implements Oxidizable {

    private final Oxidizable.OxidationLevel oxidationLevel;

    public CopperLadderBlock(Oxidizable.OxidationLevel oxidationLevel, Settings settings) {
        super(settings, LadderType.COPPER);
        this.oxidationLevel =oxidationLevel;
    }
    @Override
    public int getSpeedMultiplier() {
        return IronLadders.CONFIG.copperLadderSpeedMultiplier;
    }

    @Override
    public OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }
}
