package com.nine.ironladders.config.option;

import com.nine.ironladders.config.ConfigSpec;

public enum ConfigSection implements ConfigOption {

	NONE(""),
	BEHAVIOR("behavior"),
	SPEED(BEHAVIOR, "speed"),
	UPGRADE(BEHAVIOR, "upgrade"),
	ENTITY_INTERACTION(BEHAVIOR, "interaction"),

	RECIPES("recipes"),

	VISUAL("visual"),
	MODEL(VISUAL, "model"),
	
	COMPATIBILITY("compatibility"),
	

	;
	
	
	@Override
	public void apply(ConfigSpec spec) {
		spec.section = this;
	}
	
	public final String key;
	public final ConfigSection parent;
	
	ConfigSection(String key) {
		this(null, key);
	}
	
	ConfigSection(ConfigSection parent, String key) {
		this.parent = parent;
		this.key = key;
	}
	
	public String getFullKey(){
		var ret = key;
//		if (parent != null)
//			ret = parent.getFullKey() + "." + getKey();
		return ret;
	}

	public String getKey(){
		return key;
	}

	public String getDisplayName() {
		return key;
	}

	public boolean isRoot() {
		return parent == null;
	}

	public ConfigSection getParent() {
		return parent;
	}

}
