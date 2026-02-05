package com.nine.ironladders.config.option;

import com.nine.ironladders.config.ConfigSpec;

public enum ConfigSide implements ConfigOption {
	
    CLIENT,
    COMMON,
	;
	
	@Override
	public void apply(ConfigSpec spec) {
		spec.side = this;
	}
}