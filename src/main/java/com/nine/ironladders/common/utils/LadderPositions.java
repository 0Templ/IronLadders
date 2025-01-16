package com.nine.ironladders.common.utils;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum LadderPositions implements StringRepresentable {

    SINGLE("single"),
    HAS_UP_NEIGHBOUR("has_up"),
    HAS_DOWN_NEIGHBOUR("has_down"),
    HAS_DOWN_AND_UP_NEIGHBOUR("has_both");

    private final String name;

    LadderPositions(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }

}
