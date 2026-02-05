package com.nine.ironladders.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum MorphType {
	
	
	NONE(0, ""),
	COPPER(1, "copper"),
	EXPOSED_COPPER(2, "exposed_copper"),
	WEATHERED_COPPER(3, "weathered_copper"),
	OXIDIZED_COPPER(4, "oxidized_copper"),
	IRON(5, "iron"),
	GOLDEN(6, "golden"),
	DIAMOND(7, "diamond"),
	NETHERITE(8, "netherite"),
	OBSIDIAN(9, "obsidian"),
	
	BEDROCK(30, "bedrock"),
	CRYING_OBSIDIAN(31, "crying_obsidian"),
	WOODEN(32, "wooden"),
	
	UNKNOWN(99, "unknown"),
	
	;
	
	public static final Map<String, MorphType> KEY_ID_MAP;
	
	public final int id;
	public final String key;
	
	MorphType(int id, String key){
		this.id = id;
		this.key = key;
	}
	
	static {
		Map<String, MorphType> map = new HashMap<>();
		
		{
			map.put("ironladders:copper_ladder", COPPER);
			map.put("ironladders:exposed_copper_ladder", EXPOSED_COPPER);
			map.put("ironladders:weathered_copper_ladder", WEATHERED_COPPER);
			map.put("ironladders:oxidized_copper_ladder", OXIDIZED_COPPER);
			map.put("ironladders:waxed_copper_ladder", COPPER);
			map.put("ironladders:waxed_exposed_copper_ladder", EXPOSED_COPPER);
			map.put("ironladders:waxed_weathered_copper_ladder", WEATHERED_COPPER);
			map.put("ironladders:waxed_oxidized_copper_ladder", OXIDIZED_COPPER);
			map.put("ironladders:iron_ladder", IRON);
			map.put("ironladders:golden_ladder", GOLDEN);
			map.put("ironladders:diamond_ladder", DIAMOND);
			map.put("ironladders:netherite_ladder", NETHERITE);
			map.put("ironladders:obsidian_ladder", OBSIDIAN);
			
			map.put("ironladders:crying_obsidian_ladder", CRYING_OBSIDIAN);
			map.put("ironladders:bedrock_ladder", BEDROCK);
			
			map.put("minecraft:ladder", WOODEN);
		}

		
		KEY_ID_MAP = Collections.unmodifiableMap(map);
	}
	

	
}
