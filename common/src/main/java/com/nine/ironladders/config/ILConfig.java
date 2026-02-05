package com.nine.ironladders.config;

import com.nine.ironladders.client.model.ModelType;
import com.nine.ironladders.config.option.*;
import com.nine.ironladders.platform.util.LoaderTarget;

public class ILConfig {
	
	private static final double COPPER_SPEED = 0.04;
	private static final double IRON_SPEED = 0.10;
	private static final double GOLD_SPEED = 0.14;
	private static final double DIAMOND_SPEED = 0.20;
	private static final double NETHERITE_SPEED = 0.30;
	
	private static final double OBSIDIAN_SPEED  = DIAMOND_SPEED * 0.80;
	private static final double BEDROCK_SPEED = NETHERITE_SPEED;
	
	
	public static final ConfigValue<Double> GLOBAL_SPEED_MULTIPLIER =
			speed(1, "global",
					ConfigComment.of("Global Speed Multiplier")
							.line("Set to 0 to disable ladder speed boosts")
			);
	
	public static final ConfigValue<Double> COPPER_LADDER_SPEED_MULTIPLIER =
			speed(COPPER_SPEED, "copper",
					ConfigComment.of()
							.line("Ladders speed settings")
							.line("Value = blocks per tick added while climbing (up/down)")
							.line("Allowed range: > 0")
							.emptyLine()
							.line("Vanilla ladders")
			);
	
	public static final ConfigValue<Double> IRON_LADDER_SPEED_MULTIPLIER =
			speed(IRON_SPEED, "iron");
	
	public static final ConfigValue<Double> GOLD_LADDER_SPEED_MULTIPLIER =
			speed(GOLD_SPEED, "gold");
	
	public static final ConfigValue<Double> DIAMOND_LADDER_SPEED_MULTIPLIER =
			speed(DIAMOND_SPEED, "diamond");
	
	public static final ConfigValue<Double> OBSIDIAN_LADDER_SPEED_MULTIPLIER =
			speed(OBSIDIAN_SPEED, "obsidian");
	
	public static final ConfigValue<Double> NETHERITE_LADDER_SPEED_MULTIPLIER =
			speed(NETHERITE_SPEED, "netherite");
	
	public static final ConfigValue<Double> BEDROCK_LADDER_SPEED_MULTIPLIER =
			speed(BEDROCK_SPEED, "bedrock");
	
	
	public static final ConfigValue<Double> TIN_LADDER_SPEED_MULTIPLIER =
			speed(COPPER_SPEED * 1.05, "tin", ConfigComment.of()
					.line("Non-vanilla ladders"));
	
	public static final ConfigValue<Double> ZINC_LADDER_SPEED_MULTIPLIER =
			speed(COPPER_SPEED * 1.08, "zinc", LoaderTarget.FABRIC);
	
	public static final ConfigValue<Double> ALUMINIUM_LADDER_SPEED_MULTIPLIER =
			speed(COPPER_SPEED * 1.10, "aluminium");
	
	
	public static final ConfigValue<Double> BRONZE_LADDER_SPEED_MULTIPLIER =
			speed(IRON_SPEED * 1.05, "bronze");
	
	public static final ConfigValue<Double> LEAD_LADDER_SPEED_MULTIPLIER =
			speed(IRON_SPEED * 1.07, "lead");
	
	public static final ConfigValue<Double> SILVER_LADDER_SPEED_MULTIPLIER =
			speed(IRON_SPEED * 1.10, "silver");
	
	public static final ConfigValue<Double> STEEL_LADDER_SPEED_MULTIPLIER =
			speed(IRON_SPEED * 1.12, "steel");
	
	public static final ConfigValue<Double> NICKEL_LADDER_SPEED_MULTIPLIER =
			speed(IRON_SPEED * 1.15, "nickel", LoaderTarget.FABRIC);
	
	
	public static final ConfigValue<Double> ELECTRUM_LADDER_SPEED_MULTIPLIER =
			speed(GOLD_SPEED * 1.05, "electrum", LoaderTarget.FABRIC);
	
	public static final ConfigValue<Double> ADVANCED_ALLOY_LADDER_SPEED_MULTIPLIER =
			speed(GOLD_SPEED * 1.08, "advanced_alloy", LoaderTarget.FABRIC);
	
	public static final ConfigValue<Double> INVAR_LADDER_SPEED_MULTIPLIER =
			speed(GOLD_SPEED * 1.10, "invar", LoaderTarget.FABRIC);
	
	
	public static final ConfigValue<Double> CHROMIUM_LADDER_SPEED_MULTIPLIER =
			speed(DIAMOND_SPEED * 1.05, "chromium", LoaderTarget.FABRIC);
	
	public static final ConfigValue<Double> PLATINUM_LADDER_SPEED_MULTIPLIER =
			speed(DIAMOND_SPEED * 1.10, "platinum");
	
	public static final ConfigValue<Double> TITANIUM_LADDER_SPEED_MULTIPLIER =
			speed(DIAMOND_SPEED * 1.12, "titanium", LoaderTarget.FABRIC);
	
	public static final ConfigValue<Double> TUNGSTEN_LADDER_SPEED_MULTIPLIER =
			speed(DIAMOND_SPEED * 1.15, "tungsten", LoaderTarget.FABRIC);
	
	
	public static final ConfigValue<Double> TUNGSTEN_STEEL_LADDER_SPEED_MULTIPLIER =
			speed(NETHERITE_SPEED * 1.05, "tungsten_steel", LoaderTarget.FABRIC);
	
	
	// Speed system configuration
	public static final ConfigValue<Boolean> LADDERS_SPEED_MULTIPLYING_ONLY_FOR_PLAYERS =
			ConfigImpl.register(
					"ladders_speed_multiplying_only_for_players",
					false,
					ConfigSection.SPEED,
					ConfigSide.COMMON,
					ConfigComment.of().line("Apply speed multiplier only to players, not to other entities"));
	
	public static final ConfigValue<Boolean> ENABLE_NARROW_OPENING_DETECTION =
			ConfigImpl.register(
					"enable_narrow_opening_detection",
					true,
					ConfigSection.SPEED,
					ConfigSide.COMMON,
					ConfigComment.of().line("Enable automatic slowdown when approaching 2-block high openings")
							.line("Prevents skipping through narrow spaces when moving at high speed"));
	
	
	public static final ConfigValue<Boolean> HIDE_UNCRAFTABLE_LADDERS =
			ConfigImpl.register(
					"hide_uncraftable_ladders",
					true,
					ConfigSection.RECIPES,
					ConfigSide.COMMON,
					ConfigFlag.SYNC,
					ConfigComment.of("Hides ladders with invalid recipe ingredients (from the creative tab and JEI/REI/EMI panel)")
							.line("If you tweak ladder recipes, you may want to set this to false")
			);
	
	
	// Ladder model types
	public static final ConfigValue<ModelType> DEFAULT_MODEL_TYPE =
			ConfigImpl.register(
					"default_ladder_model_type",
					ModelType.V1,
					ConfigSection.MODEL,
					ConfigSide.CLIENT,
					ConfigComment.of("Default ladder model type")
							.line("Used for ladders that were not customized with the Styler tool")
			);
	
	public static final ConfigValue<Boolean> ENABLE_MULTIVARIANT_MODELS =
			ConfigImpl.register(
					"enable_multivariant_models",
					true,
					ConfigSection.MODEL,
					ConfigSide.CLIENT,
					ConfigComment.of("Adds small random visual variations to ladder textures (highlights / slight tint changes)"));
	
	public static final ConfigValue<Boolean> JADE_INTEGRATION =
			ConfigImpl.register(
					"enable_jade_integration",
					true,
					ConfigSection.COMPATIBILITY,
					ConfigSide.CLIENT,
					ConfigComment.of("Enables Jade integration"));
	
	public static final ConfigValue<Boolean> WTHIT_INTEGRATION =
			ConfigImpl.register(
					"enable_wthit_integration",
					true,
					ConfigSection.COMPATIBILITY,
					ConfigSide.CLIENT,
					ConfigComment.of("Enables WTHIT integration"));
	
	public static final ConfigValue<Boolean> TOP_INTEGRATION =
			ConfigImpl.register(
					"enable_top_integration",
					true,
					ConfigSection.COMPATIBILITY,
					ConfigSide.CLIENT,
					ConfigLoaderTarget.of(LoaderTarget.FORGE),
					ConfigComment.of("Enables The One Probe integration"));
	
	
	
	private static ConfigValue<Double> speed(double base, String id){
		return speed(base, id, LoaderTarget.COMMON, ConfigComment.EMPTY);
	}
	
	private static ConfigValue<Double> speed(double base, String id, ConfigComment comment){
		return speed(base, id, LoaderTarget.COMMON, comment);
	}
	
	private static ConfigValue<Double> speed(double base, String id, LoaderTarget loaderTarget){
		return speed(base, id, loaderTarget, ConfigComment.EMPTY);
	}
	
	private static ConfigValue<Double> speed(double base, String id, LoaderTarget loaderTarget, ConfigComment comment){
		return ConfigImpl.register(
				id + "_speed_multiplier",
				Math.round(base * 1000.0) / 1000.0,
				ConfigSection.SPEED,
				ConfigSide.COMMON,
				ConfigFlag.SYNC,
				ConfigFlag.HIDE_CONSTRAINTS,
				new ConfigRange<>(0D, Double.MAX_VALUE),
				ConfigLoaderTarget.of(loaderTarget),
				comment);
	}
	
	public static void init(){
	}
	
}

