package com.nine.ironladders.platform.util;

public enum LoaderTarget {
	
	COMMON("common"),
	FABRIC("fabric"),
	FORGE("forge"),
	NEOFORGE("neoforge");
	
	public final String id;
	
	LoaderTarget(String id) {
		this.id = id;
	}
}
