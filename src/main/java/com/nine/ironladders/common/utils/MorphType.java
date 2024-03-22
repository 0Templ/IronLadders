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
    DEFAULT("default");
    private final String name;
    MorphType(String p_61775_) {
        this.name = p_61775_;
    }
    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
    public MorphType getTypeFromId(int id){
        return switch (id) {
            case 99 -> DEFAULT;
            case 1 -> OXIDIZED_COPPER;
            case 2 -> WEATHERED_COPPER;
            case 3 -> EXPOSED_COPPER;
            case 4 -> COPPER;
            case 5 -> IRON;
            case 6 -> GOLD;
            case 7 -> DIAMOND;
            case 8 -> NETHERITE;
            case 9 -> VINES;
            default -> NONE;
        };
    }
}
