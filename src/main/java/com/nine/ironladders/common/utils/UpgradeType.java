package com.nine.ironladders.common.utils;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public enum UpgradeType {

    DEFAULT_TO_COPPER(LadderType.DEFAULT, LadderType.COPPER),
    DEFAULT_TO_IRON(LadderType.DEFAULT, LadderType.IRON),
    DEFAULT_TO_GOLD(LadderType.DEFAULT, LadderType.GOLD),
    DEFAULT_TO_DIAMOND(LadderType.DEFAULT, LadderType.DIAMOND),
    DEFAULT_TO_OBSIDIAN(LadderType.DEFAULT, LadderType.OBSIDIAN),
    DEFAULT_TO_NETHERITE(LadderType.DEFAULT, LadderType.NETHERITE),
    COPPER_TO_IRON(LadderType.COPPER, LadderType.IRON),
    IRON_TO_GOLD(LadderType.IRON, LadderType.GOLD),
    GOLD_TO_DIAMOND(LadderType.GOLD, LadderType.DIAMOND),
    DIAMOND_TO_OBSIDIAN(LadderType.DIAMOND, LadderType.OBSIDIAN),
    DIAMOND_TO_NETHERITE(LadderType.DIAMOND, LadderType.NETHERITE),
    ANY_TO_POWERED(LadderProperties.POWERED),
    ANY_TO_GLOWING(LadderProperties.LIGHTED),
    ANY_TO_HIDDEN(LadderProperties.HIDDEN),

    DEFAULT_TO_BRONZE(LadderType.DEFAULT, LadderType.BRONZE),
    DEFAULT_TO_STEEL(LadderType.DEFAULT, LadderType.STEEL),
    DEFAULT_TO_TIN(LadderType.DEFAULT, LadderType.TIN),
    DEFAULT_TO_LEAD(LadderType.DEFAULT, LadderType.LEAD),
    DEFAULT_TO_ALUMINUM(LadderType.DEFAULT, LadderType.LEAD),
    DEFAULT_TO_SILVER(LadderType.DEFAULT, LadderType.LEAD);

    private final LadderType originType;
    private final LadderType finalType;
    private final BooleanProperty property;

    UpgradeType(LadderType previousType, LadderType upgradeType) {
        this.property = null;
        this.originType = previousType;
        this.finalType = upgradeType;
    }

    UpgradeType(BooleanProperty property) {
        this.property = property;
        this.originType = null;
        this.finalType = null;
    }

    public BooleanProperty getProperty() {
        return property;
    }

    public LadderType getPreviousType() {
        return originType;
    }

    public LadderType getUpgradeType() {
        return finalType;
    }
}