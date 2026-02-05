package com.nine.ironladders.config.option;

import com.nine.ironladders.config.ConfigSpec;
import com.nine.ironladders.platform.util.LoaderTarget;

public record ConfigLoaderTarget(LoaderTarget target) implements ConfigOption {
	
	public static ConfigLoaderTarget of(LoaderTarget target) {
		return new ConfigLoaderTarget(target);
	}
	
	@Override
	public void apply(ConfigSpec spec) {
		spec.target = this.target;
	}
	
}