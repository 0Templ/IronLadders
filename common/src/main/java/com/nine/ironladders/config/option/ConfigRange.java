package com.nine.ironladders.config.option;

import com.nine.ironladders.config.ConfigSpec;

public record ConfigRange<T extends Number & Comparable<T>>(T min, T max) implements ConfigOption {

    //
    public T clamp(T value) {
        if (value.compareTo(min) < 0) return min;
        if (value.compareTo(max) > 0) return max;
        return value;
    }

    @Override
    public void apply(ConfigSpec spec) {
        spec.range = this;
    }

}
