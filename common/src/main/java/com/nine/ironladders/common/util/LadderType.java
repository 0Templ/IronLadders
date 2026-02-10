package com.nine.ironladders.common.util;

import com.nine.ironladders.client.tab.TabCopperIconPart;
import com.nine.ironladders.client.tab.TabIconPart;
import com.nine.ironladders.common.material.ILMaterial;
import com.nine.ironladders.common.material.ILMaterials;
import com.nine.ironladders.config.ConfigValue;
import com.nine.ironladders.config.ILConfig;

import java.util.Set;

public enum LadderType {
	
	NONE("none", ILConfig.COPPER_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("none"), false
			),
	
	COPPER("copper", ILConfig.COPPER_LADDER_SPEED_MULTIPLIER,
			new TabCopperIconPart(),
			false,
			ILMaterials.PLATFORM_MATERIALS.COPPER_INGOT
	),
	
	IRON("iron", ILConfig.IRON_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("iron"),
			false,
			ILMaterials.PLATFORM_MATERIALS.IRON_INGOT
	),
	
	GOLD("gold", ILConfig.GOLD_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("gold"),
			false,
			ILMaterials.PLATFORM_MATERIALS.GOLD_INGOT
	),
	
	DIAMOND("diamond", ILConfig.DIAMOND_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("diamond"),
			false,
			ILMaterials.PLATFORM_MATERIALS.DIAMOND
	),
	
	OBSIDIAN("obsidian", ILConfig.OBSIDIAN_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("obsidian"),
			false,
			ILMaterials.PLATFORM_MATERIALS.OBSIDIAN
	),
	
	NETHERITE("netherite", ILConfig.NETHERITE_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("netherite"),
			false,
			ILMaterials.PLATFORM_MATERIALS.NETHERITE
	),
	
	BEDROCK("bedrock", ILConfig.BEDROCK_LADDER_SPEED_MULTIPLIER, false),
	
	CRYING_OBSIDIAN("crying_obsidian", ILConfig.OBSIDIAN_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("crying_obsidian"), false
			),
		
	TIN("tin", ILConfig.TIN_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.TIN_INGOT
			),
			
	BRONZE("bronze", ILConfig.BRONZE_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("bronze"),
			ILMaterials.PLATFORM_MATERIALS.BRONZE_INGOT
			),
	
	STEEL("steel", ILConfig.STEEL_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("steel"),
			ILMaterials.PLATFORM_MATERIALS.STEEL_INGOT
			),
	
	SILVER("silver", ILConfig.SILVER_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.SILVER_INGOT
			),
	
	ALUMINIUM("aluminium", ILConfig.ALUMINIUM_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.ALUMINUM_INGOT
			),
	
	LEAD("lead", ILConfig.LEAD_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.LEAD_INGOT
			),
	
	PLATINUM("platinum", ILConfig.PLATINUM_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("platinum"),
			ILMaterials.PLATFORM_MATERIALS.PLATINUM_INGOT
			),
	
	CHROMIUM("chromium", ILConfig.CHROMIUM_LADDER_SPEED_MULTIPLIER,
			new TabIconPart("chromium"),
			ILMaterials.PLATFORM_MATERIALS.CHROMIUM_INGOT
	),
	
	ADVANCED_ALLOY("advanced_alloy", ILConfig.ADVANCED_ALLOY_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.ADVANCED_ALLOY_INGOT
	),
	
	NICKEL("nickel", ILConfig.NICKEL_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.NICKEL_INGOT
	),
	
	TUNGSTEN("tungsten", ILConfig.TUNGSTEN_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.TUNGSTEN_INGOT
	),
	
	TUNGSTEN_STEEL("tungsten_steel", ILConfig.TUNGSTEN_STEEL_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.TUNGSTEN_STEEL_INGOT
	),
	
	TITANIUM("titanium", ILConfig.TITANIUM_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.TITANIUM_INGOT
	),
	
	ZINC("zinc", ILConfig.ZINC_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.ZINC_INGOT
	),
	
	INVAR("invar", ILConfig.INVAR_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.INVAR_INGOT
	),
	
	ELECTRUM("electrum", ILConfig.ELECTRUM_LADDER_SPEED_MULTIPLIER,
			ILMaterials.PLATFORM_MATERIALS.ELECTRUM_INGOT
	),
	
	;
	
	// Used in model datagen as subdirectories for ladder textures
	public final String key;
	
	public final boolean nonVanilla;
	public final boolean vanilla;
	public final ConfigValue<Double> configValue;
	private double speedMultiplier = Double.NaN;
	private boolean initialized = false;
	
	public final TabIconPart tabIconPart;
	
	public final Set<ILMaterial> materials;
	
	
	
	LadderType(String key, ConfigValue<Double> configValue, ILMaterial... materials) {
		this(key, configValue, true, materials);
	}
	
	LadderType(String key, ConfigValue<Double> configValue, boolean nonVanilla, ILMaterial... materials) {
		this(key, configValue, null, nonVanilla, materials);
	}

	LadderType(String key, ConfigValue<Double> configValue, TabIconPart tabIconPart, ILMaterial... materials) {
		this(key, configValue, tabIconPart, true, materials);
	}
	
	
	LadderType(String key, ConfigValue<Double> configValue, TabIconPart tabIconPart, boolean nonVanilla, ILMaterial... materials){
		this.key = key;
		this.configValue = configValue;
		this.tabIconPart = tabIconPart;
		this.nonVanilla = nonVanilla;
		this.vanilla = !nonVanilla;
		this.materials = Set.of(materials);
	}
	
	public double getSpeedMultiplier() {
		if (!initialized) {
			speedMultiplier = configValue.get();
			initialized = true;
		}
		return speedMultiplier;
	}
	
	public static void resetSpeedCache(){
		for (var type : values()){
			type.initialized = false;
			type.speedMultiplier = Double.NaN;
		}
	}
	
}
