package com.nine.ironladders.config;

import com.nine.ironladders.config.option.ConfigComment;
import com.nine.ironladders.config.option.ConfigRange;
import com.nine.ironladders.config.option.ConfigSection;
import com.nine.ironladders.config.option.ConfigSide;
import com.nine.ironladders.platform.util.LoaderTarget;

public class ConfigSpec {
	
	public boolean shouldSync = false;
	public ConfigSide side = ConfigSide.COMMON;
	public LoaderTarget target = LoaderTarget.COMMON;
	
	public ConfigComment comment = ConfigComment.EMPTY;
	public ConfigSection section = ConfigSection.NONE;
	public boolean hideConstraints = false;
	
	public ConfigRange<? extends Number> range = null;
	
	
}
