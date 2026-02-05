package com.nine.ironladders.init;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.ItemProperties;
import com.nine.ironladders.common.block.CryingObsidianLadderBlock;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.block.VariantLadderBlock;
import com.nine.ironladders.common.block.CopperLadderBlock;
import com.nine.ironladders.common.item.MetalLadderItem;
import com.nine.ironladders.common.util.LadderType;
import com.nine.ironladders.common.util.PlatformObjects;
import com.nine.ironladders.platform.util.LoaderTarget;
import com.nine.ironladders.platform.Platform;
import com.nine.ironladders.platform.util.RegistryProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
import java.util.function.Supplier;

public class ILBlocks {
	
	public static final PlatformObjects<Block> AVAILABLE_LADDERS = new PlatformObjects<>();
	
	// Vanilla ladders
	public static final RegistryProvider<Block> COPPER_LADDER = registerLadder(
			"copper_ladder",
			() -> new CopperLadderBlock(BlockBehaviour.Properties.of()
					.strength(1.2F, 6.0F)
					.sound(SoundType.COPPER)
					.requiresCorrectToolForDrops()
					.pushReaction(PushReaction.DESTROY)
					.mapColor(MapColor.COLOR_ORANGE), WeatheringCopper.WeatherState.UNAFFECTED),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> EXPOSED_COPPER_LADDER = registerLadder(
			"exposed_copper_ladder",
			() -> new CopperLadderBlock(BlockBehaviour.Properties.of()
					.strength(1.5F, 6.0F)
					.sound(SoundType.COPPER)
					.requiresCorrectToolForDrops()
					.pushReaction(PushReaction.DESTROY)
					.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY), WeatheringCopper.WeatherState.EXPOSED),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> WEATHERED_COPPER_LADDER = registerLadder(
			"weathered_copper_ladder",
			() -> new CopperLadderBlock(BlockBehaviour.Properties.of()
					.strength(1.5F, 6.0F)
					.sound(SoundType.COPPER)
					.requiresCorrectToolForDrops()
					.pushReaction(PushReaction.DESTROY)
					.mapColor(MapColor.WARPED_STEM), WeatheringCopper.WeatherState.WEATHERED),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> OXIDIZED_COPPER_LADDER = registerLadder(
			"oxidized_copper_ladder",
			() -> new CopperLadderBlock(BlockBehaviour.Properties.of()
					.strength(1.5F, 6.0F)
					.sound(SoundType.COPPER)
					.requiresCorrectToolForDrops()
					.pushReaction(PushReaction.DESTROY)
					.mapColor(MapColor.WARPED_NYLIUM), WeatheringCopper.WeatherState.OXIDIZED),
			LoaderTarget.COMMON
	);

	public static final RegistryProvider<Block> WAXED_COPPER_LADDER = registerLadder(
			"waxed_copper_ladder",
			() -> new VariantLadderBlock.ThreeVariantLadder(BlockBehaviour.Properties.copy(COPPER_LADDER.get()),
					LadderType.COPPER),
			LoaderTarget.COMMON
	);
	

	public static final RegistryProvider<Block> WAXED_EXPOSED_COPPER_LADDER = registerLadder(
			"waxed_exposed_copper_ladder",
			() -> new VariantLadderBlock.ThreeVariantLadder(BlockBehaviour.Properties.copy(EXPOSED_COPPER_LADDER.get()),
					LadderType.COPPER),
			LoaderTarget.COMMON
	);

	public static final RegistryProvider<Block> WAXED_WEATHERED_COPPER_LADDER = registerLadder(
			"waxed_weathered_copper_ladder",
			() -> new VariantLadderBlock.ThreeVariantLadder(BlockBehaviour.Properties.copy(WEATHERED_COPPER_LADDER.get()),
					LadderType.COPPER),
			LoaderTarget.COMMON
	);

	public static final RegistryProvider<Block> WAXED_OXIDIZED_COPPER_LADDER = registerLadder(
			"waxed_oxidized_copper_ladder",
			() -> new VariantLadderBlock.ThreeVariantLadder(BlockBehaviour.Properties.copy(OXIDIZED_COPPER_LADDER.get()),
					LadderType.COPPER),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> IRON_LADDER = registerLadder(
			"iron_ladder",
			() -> new VariantLadderBlock.TwoVariantLadder(BlockBehaviour.Properties.of()
					.strength(2F, 6.0F)
					.sound(SoundType.METAL)
					.pushReaction(PushReaction.DESTROY)
					.mapColor(MapColor.METAL), LadderType.IRON),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> GOLDEN_LADDER = registerLadder(
			"golden_ladder",
			() -> new VariantLadderBlock.TwoVariantLadder(BlockBehaviour.Properties.of()
					.strength(1.5F, 6.0F)
					.sound(SoundType.METAL)
					.requiresCorrectToolForDrops()
					.pushReaction(PushReaction.DESTROY)
					.mapColor(MapColor.GOLD), LadderType.GOLD),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> DIAMOND_LADDER = registerLadder(
			"diamond_ladder",
			() -> new VariantLadderBlock.TwoVariantLadder(BlockBehaviour.Properties.of()
					.strength(3F, 6.0F)
					.sound(SoundType.METAL)
					.requiresCorrectToolForDrops()
					.pushReaction(PushReaction.DESTROY)
					.mapColor(MapColor.METAL), LadderType.DIAMOND),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> NETHERITE_LADDER = registerLadder(
			"netherite_ladder",
			() -> new VariantLadderBlock.TwoVariantLadder(BlockBehaviour.Properties.of()
					.strength(12F, 1200.0F)
					.sound(SoundType.NETHERITE_BLOCK)
					.requiresCorrectToolForDrops()
					.pushReaction(PushReaction.DESTROY)
					.mapColor(MapColor.COLOR_BLACK), LadderType.NETHERITE),
			new Item.Properties().fireResistant(),
			LoaderTarget.COMMON
	);

	public static final RegistryProvider<Block> OBSIDIAN_LADDER = registerLadder(
			"obsidian_ladder",
			() -> new VariantLadderBlock.ThreeVariantLadder(BlockBehaviour.Properties.of()
					.strength(22.0F, 1200.0F)
					.requiresCorrectToolForDrops()
					.pushReaction(PushReaction.IGNORE)
					.mapColor(MapColor.COLOR_BLACK), LadderType.OBSIDIAN),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> CRYING_OBSIDIAN_LADDER = registerLadder(
			"crying_obsidian_ladder",
			() -> new CryingObsidianLadderBlock(BlockBehaviour.Properties.of()
					.strength(22.0F, 1200.0F)
					.requiresCorrectToolForDrops()
					.pushReaction(PushReaction.IGNORE)
					.mapColor(MapColor.COLOR_BLACK)
					, LadderType.CRYING_OBSIDIAN),
			
			LoaderTarget.COMMON
	);

	public static final RegistryProvider<Block> BEDROCK_LADDER = registerLadder(
			"bedrock_ladder",
			() -> new VariantLadderBlock.TwoVariantLadder(BlockBehaviour.Properties.of()
					.strength(-1.0F, 3600000.0F)
					.noLootTable()
					.pushReaction(PushReaction.IGNORE)
					.mapColor(MapColor.STONE), LadderType.BEDROCK),
			
			LoaderTarget.COMMON
	);
	
	// Non-vanilla ladders
	public static final RegistryProvider<Block> TIN_LADDER = registerLadder(
			"tin_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(2, 6)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.TERRACOTTA_WHITE),
					LadderType.TIN),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> BRONZE_LADDER = registerLadder(
			"bronze_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(4.5F, 9.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.COLOR_ORANGE),
					LadderType.BRONZE),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> SILVER_LADDER = registerLadder(
			"silver_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(4, 9.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.TERRACOTTA_WHITE),
					LadderType.SILVER),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> ALUMINIUM_LADDER = registerLadder(
			"aluminium_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(2.5F, 6.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.COLOR_LIGHT_GRAY),
					LadderType.ALUMINIUM),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> LEAD_LADDER = registerLadder(
			"lead_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(4, 9.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.COLOR_LIGHT_GRAY),
					LadderType.LEAD),
			
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> STEEL_LADDER = registerLadder(
			"steel_ladder",
			() -> new VariantLadderBlock.ThreeVariantLadder(BlockBehaviour.Properties.of()
					.strength(4, 9.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.TERRACOTTA_WHITE),
					LadderType.STEEL),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> PLATINUM_LADDER = registerLadder(
			"platinum_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(5, 9.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.SNOW),
					LadderType.PLATINUM),
			LoaderTarget.COMMON
	);
	
	public static final RegistryProvider<Block> CHROMIUM_LADDER = registerLadder(
			"chromium_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(4f, 6.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.METAL),
					LadderType.CHROMIUM),
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> ADVANCED_ALLOY_LADDER = registerLadder(
			"advanced_alloy_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(4f, 6.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.METAL),
					LadderType.ADVANCED_ALLOY),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> NICKEL_LADDER = registerLadder(
			"nickel_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(4f, 6.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.METAL),
					LadderType.NICKEL),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> TUNGSTEN_LADDER = registerLadder(
			"tungsten_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(4f, 6.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.METAL),
					LadderType.TUNGSTEN),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> TUNGSTEN_STEEL_LADDER = registerLadder(
			"tungsten_steel_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(25f, 800.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.METAL),
					LadderType.TUNGSTEN_STEEL),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> TITANIUM_LADDER = registerLadder(
			"titanium_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(5f, 6.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.METAL),
					LadderType.TITANIUM),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> ZINC_LADDER = registerLadder(
			"zinc_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(4f, 6.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.METAL),
					LadderType.ZINC),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> INVAR_LADDER = registerLadder(
			"invar_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(2.5f, 6.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.METAL),
					LadderType.INVAR),
			
			LoaderTarget.FABRIC
	);
	
	public static final RegistryProvider<Block> ELECTRUM_LADDER = registerLadder(
			"electrum_ladder",
			() -> new MetalLadderBlock(BlockBehaviour.Properties.of()
					.strength(2.5f, 6.0F)
					.requiresCorrectToolForDrops()
					.mapColor(MapColor.METAL),
					LadderType.ELECTRUM),
			
			LoaderTarget.FABRIC
	);
	
	public static RegistryProvider<Block> registerLadder(
			String id,
			Supplier<Block> blockSupplier,
			LoaderTarget... targets
	) {
		return registerLadder(id, blockSupplier, new Item.Properties(), targets);
	}
	
	public static RegistryProvider<Block> registerLadder(
			String id,
			Supplier<Block> blockSupplier,
			Item.Properties properties,
			LoaderTarget... targets
	) {
		return register(id, blockSupplier, block -> new MetalLadderItem(block, properties), targets);
	}
	
	public static RegistryProvider<Block> register(
			String id,
			Supplier<Block> blockSupplier,
			LoaderTarget... targets
	) {
		return register(id, blockSupplier, block -> new BlockItem(block, new Item.Properties()), targets);
	}
	
	public static RegistryProvider<Block> register(
			String id,
			Supplier<Block> blockSupplier,
			Function<Block, BlockItem> itemFactory,
			LoaderTarget... targets
	) {
		RegistryProvider<Block> ret;
		boolean shouldRegister = availableLadder(targets) || ILCommon.IS_DATAGEN;
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
	
	private static boolean availableLadder(LoaderTarget... targets){
		for (var loader : targets) {
			if (loader == LoaderTarget.COMMON || loader == Platform.CORE.currentLoader())
				return true;
		}
		return false;
	}
	
	public static void init(){
	}

}
