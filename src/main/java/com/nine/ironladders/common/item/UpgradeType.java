package com.nine.ironladders.common.item;

import com.nine.ironladders.common.block.LadderType;

public enum UpgradeType {
    DEFAULT_TO_COPPER(LadderType.DEFAULT,LadderType.COPPER),
    DEFAULT_TO_IRON(LadderType.DEFAULT,LadderType.IRON),
    DEFAULT_TO_GOLD(LadderType.DEFAULT,LadderType.GOLD),
    DEFAULT_TO_DIAMOND(LadderType.DEFAULT,LadderType.DIAMOND),
    DEFAULT_TO_NETHERITE(LadderType.DEFAULT,LadderType.NETHERITE),
    COPPER_TO_IRON(LadderType.COPPER,LadderType.IRON),
    IRON_TO_GOLD(LadderType.IRON,LadderType.GOLD),
    GOLD_TO_DIAMOND(LadderType.GOLD,LadderType.DIAMOND),
    DIAMOND_TO_NETHERITE(LadderType.DIAMOND,LadderType.NETHERITE);


    private final LadderType originType;
    private final LadderType finalType;

    UpgradeType(LadderType previousType, LadderType upgradeType) {
        this.originType = previousType;
        this.finalType = upgradeType;
    }
    public LadderType getPreviousType() {
        return originType;
    }
    public LadderType getUpgradeType() {
        return finalType;
    }
}