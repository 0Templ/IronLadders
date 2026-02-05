package com.nine.ironladders.config.option;

import com.nine.ironladders.config.ConfigSpec;

public interface ConfigOption {
	
	void apply(ConfigSpec spec);
	
}