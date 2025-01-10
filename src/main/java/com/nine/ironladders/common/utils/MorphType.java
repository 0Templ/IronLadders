package com.nine.ironladders.common.utils;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum MorphType implements StringRepresentable {

    NONE("none"),
    OXIDIZED_COPPER("oxidized_copper"),
    WEATHERED_COPPER("weathered_copper"),
    EXPOSED_COPPER("exposed_copper"),
    COPPER("copper"),
    IRON("iron"),
    GOLD("gold"),
    DIAMOND("diamond"),
    NETHERITE("netherite"),
    VINES("vines"),
    STEEL("steel"),
    TIN("tin"),
    LEAD("lead"),
    BRONZE("bronze"),
    ALUMINUM("aluminum"),
    SILVER("silver"),
    DEFAULT("default");

    private final String name;

    MorphType(String type) {
        this.name = type;
    }

    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }

}
