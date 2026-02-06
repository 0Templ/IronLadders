package com.nine.ironladders.config.option;

import com.nine.ironladders.config.ConfigSpec;

public class ConfigRange<T extends Number & Comparable<T>> implements ConfigOption {
	
	public final T min;
	public final T max;
	
	public ConfigRange(T min, T max) {
		this.min = min;
		this.max = max;
	}
	
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
