package com.nine.ironladders.init;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.common.block.CopperLadderBlock;
import com.nine.ironladders.common.block.CryingObsidianLadderBlock;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.block.VariantLadderBlock;
import com.nine.ironladders.common.item.MetalLadderItem;
import com.nine.ironladders.common.util.LadderType;
import com.nine.ironladders.common.util.PlatformObjects;
import com.nine.ironladders.platform.Platform;
import com.nine.ironladders.platform.util.LoaderTarget;
import com.nine.ironladders.platform.util.RegistryProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
import java.util.function.Supplier;

public class ILBlocks {
	
	public static final PlatformObjects<Block> AVAILABLE_LADDERS = new PlatformObjects<>();
	
	// Vanilla ladders
	public static final RegistryProvider<Block> COPPER_LADDER = register(
			"copper_ladder",
			LadderType.COPPER,
			() -> new CopperLadderBlock(properties()
					.strength(1.2F, 6.0F)
					.sound(SoundType.COPPER)
					.mapColor(MapColor.COLOR_ORANGE), WeatheringCopper.WeatherState.UNAFFECTED),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> EXPOSED_COPPER_LADDER = register(
			"exposed_copper_ladder",
			LadderType.COPPER,
			() -> new CopperLadderBlock(properties()
					.strength(1.5F, 6.0F)
					.sound(SoundType.COPPER)
					.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY), WeatheringCopper.WeatherState.EXPOSED),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> WEATHERED_COPPER_LADDER = register(
			"weathered_copper_ladder",
			LadderType.COPPER,
			() -> new CopperLadderBlock(properties()
					.strength(1.5F, 6.0F)
					.sound(SoundType.COPPER)
					.mapColor(MapColor.WARPED_STEM), WeatheringCopper.WeatherState.WEATHERED),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> OXIDIZED_COPPER_LADDER = register(
			"oxidized_copper_ladder",
			LadderType.COPPER,
			() -> new CopperLadderBlock(properties()
					.strength(1.5F, 6.0F)
					.sound(SoundType.COPPER)
					.mapColor(MapColor.WARPED_NYLIUM), WeatheringCopper.WeatherState.OXIDIZED),
			LoaderTarget.COMMON
	);

	public static final RegistryProvider<Block> WAXED_COPPER_LADDER = register(
			"waxed_copper_ladder",
			LadderType.COPPER,
			LadderKind.THREE,
			copperProperties(MapColor.COLOR_ORANGE),
			LoaderTarget.COMMON
	);
	

	public static final RegistryProvider<Block> WAXED_EXPOSED_COPPER_LADDER = register(
			"waxed_exposed_copper_ladder",
			LadderType.COPPER,
			LadderKind.THREE,
			copperProperties(MapColor.TERRACOTTA_LIGHT_GRAY),
			LoaderTarget.COMMON
	);

	public static final RegistryProvider<Block> WAXED_WEATHERED_COPPER_LADDER = register(
			"waxed_weathered_copper_ladder",
			LadderType.COPPER,
			LadderKind.THREE,
			copperProperties(MapColor.WARPED_STEM),
			LoaderTarget.COMMON
	);

	public static final RegistryProvider<Block> WAXED_OXIDIZED_COPPER_LADDER = register(
			"waxed_oxidized_copper_ladder",
			LadderType.COPPER,
			LadderKind.THREE,
			copperProperties(MapColor.WARPED_NYLIUM),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> IRON_LADDER = register(
			"iron_ladder",
			LadderType.IRON,
			LadderKind.TWO,
			properties()
					.strength(2F, 6.0F)
					.mapColor(MapColor.METAL),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> GOLDEN_LADDER = register(
			"golden_ladder",
			LadderType.GOLD, LadderKind.TWO,
			properties()
					.strength(1.5F, 6.0F)
					.mapColor(MapColor.GOLD),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> DIAMOND_LADDER = register(
			"diamond_ladder",
			LadderType.DIAMOND,
			LadderKind.TWO,
			properties()
					.strength(3F, 6.0F)
					.mapColor(MapColor.METAL),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> NETHERITE_LADDER = register(
			"netherite_ladder",
			LadderType.NETHERITE,
			LadderKind.TWO,
			properties()
					.strength(12F, 1200.0F)
					.sound(SoundType.NETHERITE_BLOCK)
					.mapColor(MapColor.COLOR_BLACK),
			new Item.Properties().fireResistant(),
			LoaderTarget.COMMON
	);

	public static final RegistryProvider<Block> OBSIDIAN_LADDER = register(
			"obsidian_ladder",
			LadderType.OBSIDIAN,
			LadderKind.THREE,
			properties()
					.strength(22.0F, 1200.0F)
					.pushReaction(PushReaction.IGNORE)
					.mapColor(MapColor.COLOR_BLACK),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> CRYING_OBSIDIAN_LADDER = register(
			"crying_obsidian_ladder",
			LadderType.CRYING_OBSIDIAN,
			() -> new CryingObsidianLadderBlock(properties()
					.strength(22.0F, 1200.0F)
					.pushReaction(PushReaction.IGNORE)
					.mapColor(MapColor.COLOR_BLACK),
					LadderType.CRYING_OBSIDIAN),
			
			LoaderTarget.COMMON
	);

	public static final RegistryProvider<Block> BEDROCK_LADDER = register(
			"bedrock_ladder",
			LadderType.BEDROCK,
			LadderKind.TWO,
			properties()
					.strength(-1.0F, 3600000.0F)
					.noLootTable()
					.pushReaction(PushReaction.IGNORE)
					.mapColor(MapColor.STONE),
			
			LoaderTarget.COMMON
	);
	
	// Non-vanilla ladders
	public static final RegistryProvider<Block> TIN_LADDER = register(
			"tin_ladder",
			LadderType.TIN,
			LadderKind.PLAIN,
			properties()
					.strength(2, 6)
					.mapColor(MapColor.TERRACOTTA_WHITE),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> BRONZE_LADDER = register(
			"bronze_ladder",
			LadderType.BRONZE,
			LadderKind.PLAIN,
			properties()
					.strength(4.5F, 9.0F)
					.mapColor(MapColor.COLOR_ORANGE),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> SILVER_LADDER = register(
			"silver_ladder",
			LadderType.SILVER,
			LadderKind.PLAIN,
			properties()
					.strength(4, 9.0F)
					.mapColor(MapColor.TERRACOTTA_WHITE),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> ALUMINIUM_LADDER = register(
			"aluminium_ladder",
			LadderType.ALUMINIUM,
			LadderKind.PLAIN,
			properties()
					.strength(2.5F, 6.0F)
					.mapColor(MapColor.COLOR_LIGHT_GRAY),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> LEAD_LADDER = register(
			"lead_ladder",
			LadderType.LEAD,
			LadderKind.PLAIN,
			properties()
					.strength(4, 9.0F)
					.mapColor(MapColor.COLOR_LIGHT_GRAY),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> STEEL_LADDER = register(
			"steel_ladder",
			LadderType.STEEL,
			LadderKind.THREE,
			properties()
					.strength(4, 9.0F)
					.mapColor(MapColor.TERRACOTTA_WHITE),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> PLATINUM_LADDER = register(
			"platinum_ladder",
			LadderType.PLATINUM,
			LadderKind.PLAIN,
			properties()
					.strength(5, 9.0F)
					.mapColor(MapColor.SNOW),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> CHROMIUM_LADDER = register(
			"chromium_ladder",
			LadderType.CHROMIUM,
			LadderKind.PLAIN,
			properties()
					.strength(4f, 6.0F)
					.mapColor(MapColor.METAL),
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> ADVANCED_ALLOY_LADDER = register(
			"advanced_alloy_ladder",
			LadderType.ADVANCED_ALLOY,
			LadderKind.PLAIN,
			properties()
					.strength(4f, 6.0F)
					.mapColor(MapColor.METAL),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> NICKEL_LADDER = register(
			"nickel_ladder",
			LadderType.NICKEL,
			LadderKind.PLAIN,
			properties()
					.strength(4f, 6.0F)
					.mapColor(MapColor.METAL),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> TUNGSTEN_LADDER = register(
			"tungsten_ladder",
			LadderType.TUNGSTEN,
			LadderKind.PLAIN,
			properties()
					.strength(4f, 6.0F)
					.mapColor(MapColor.METAL),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> TUNGSTEN_STEEL_LADDER = register(
			"tungsten_steel_ladder",
			LadderType.TUNGSTEN_STEEL,
			LadderKind.PLAIN,
			properties()
					.strength(25f, 800.0F)
					.mapColor(MapColor.METAL),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> TITANIUM_LADDER = register(
			"titanium_ladder",
			LadderType.TITANIUM,
			LadderKind.PLAIN,
			properties()
					.strength(5f, 6.0F)
					.mapColor(MapColor.METAL),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> ZINC_LADDER = register(
			"zinc_ladder",
			LadderType.ZINC,
			LadderKind.PLAIN,
			properties()
					.strength(4f, 6.0F)
					.mapColor(MapColor.METAL),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> INVAR_LADDER = register(
			"invar_ladder",
			LadderType.INVAR,
			LadderKind.PLAIN,
			properties()
					.strength(2.5f, 6.0F)
					.mapColor(MapColor.METAL),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> ELECTRUM_LADDER = register(
			"electrum_ladder",
			LadderType.ELECTRUM,
			LadderKind.PLAIN,
			properties()
					.strength(2.5f, 6.0F)
					.mapColor(MapColor.METAL),
			
			LoaderTarget.FABRIC
	);
	
	private static BlockBehaviour.Properties properties(){
		return BlockBehaviour.Properties.of();
	}
	
	private static BlockBehaviour.Properties copperProperties(MapColor color) {
		return properties()
				.strength(1.3F, 6.0F)
				.sound(SoundType.COPPER)
				.mapColor(color);
	}
	
	private enum LadderKind {
		PLAIN, TWO, THREE
		
	}
	
	
	private static RegistryProvider<Block> register(
			String id,
			LadderType type,
			LadderKind ladderKind,
			BlockBehaviour.Properties blockBehaviour,
			LoaderTarget... targets
	) {
		return register(id, type, ladderKind, blockBehaviour, new Item.Properties(), targets);
	}
	
	private static RegistryProvider<Block> register(
			String id,
			LadderType type,
			LadderKind ladderKind,
			BlockBehaviour.Properties blockBehaviour,
			Item.Properties properties,
			LoaderTarget... targets
	) {
		return register(id, type, ladderKind, blockBehaviour, block -> new MetalLadderItem(block, properties), targets);
	}
	
	private static RegistryProvider<Block> register(
			String id,
			LadderType type,
			LadderKind ladderKind,
			BlockBehaviour.Properties blockBehaviour,
			Function<Block, BlockItem> itemFactory,
			LoaderTarget... targets
	) {
		Supplier<Block> blockSupplier = switch(ladderKind) {
			case PLAIN -> () -> new MetalLadderBlock(blockBehaviour, type);
			case TWO -> () -> new VariantLadderBlock.TwoVariantLadder(blockBehaviour, type);
			case THREE -> () -> new VariantLadderBlock.ThreeVariantLadder(blockBehaviour, type);
		};
		return register(id, blockSupplier, itemFactory, type, targets);
	}
	
	private static RegistryProvider<Block> register(
			String id,
			LadderType type,
			Supplier<Block> blockSupplier,
			LoaderTarget... targets
	) {
		return register(id, blockSupplier, block -> new MetalLadderItem(block, new Item.Properties()), type, targets);
	}
	
	private static RegistryProvider<Block> register(
			String id,
			Supplier<Block> blockSupplier,
			Function<Block, BlockItem> itemFactory,
			LadderType type,
			LoaderTarget... targets
	) {
		RegistryProvider<Block> ret;
		boolean shouldRegister = availableLadder(type, targets) || ILCommon.IS_DATAGEN;
		if (shouldRegister) {
			// Register: either matches current platform OR datagen needs all blocks
			ret = Platform.REGISTRY.registerBlock(id, blockSupplier, itemFactory);
			// Do not add fake blocks from datagen
			if (targets.length != 0) {
				AVAILABLE_LADDERS.add(ret, targets);
			}
		}
		else {
			ret = () -> null;
		}
		
		return ret;
	}
	
	private static boolean availableLadder(LadderType type, LoaderTarget... targets){
		/*ILConfig.DISABLE_NON_VANILLA_LADDERS.get()*/
		if (false){
			return false;
		}
		for (var loader : targets) {
			if (loader == LoaderTarget.COMMON || loader == Platform.CORE.currentLoader())
				return true;
		}
		return false;
	}
	
	public static void init(){
	}

}
