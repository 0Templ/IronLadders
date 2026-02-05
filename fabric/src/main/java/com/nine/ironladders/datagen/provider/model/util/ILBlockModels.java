package com.nine.ironladders.datagen.provider.model.util;

import com.nine.ironladders.init.ILBlocks;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class ILBlockModels {
	
	private ILBlockModels() {}
	
	// Some blocks (WaxedCopper -> Copper) use models of others, so store them like this
	public static final Map<Block, Block> SOURCE = new HashMap<>();
	
	public static void add(Block block){
		add(block, block);
	}
	
	public static void add(Block block, Block modelBlock){
		SOURCE.put(block, modelBlock);
	}
	
	static {
		
		add(ILBlocks.COPPER_LADDER.get());
		add(ILBlocks.EXPOSED_COPPER_LADDER.get());
		add(ILBlocks.WEATHERED_COPPER_LADDER.get());
		add(ILBlocks.OXIDIZED_COPPER_LADDER.get());
		
		add(ILBlocks.WAXED_COPPER_LADDER.get(), ILBlocks.COPPER_LADDER.get());
		add(ILBlocks.WAXED_EXPOSED_COPPER_LADDER.get(), ILBlocks.EXPOSED_COPPER_LADDER.get());
		add(ILBlocks.WAXED_WEATHERED_COPPER_LADDER.get(), ILBlocks.WEATHERED_COPPER_LADDER.get());
		add(ILBlocks.WAXED_OXIDIZED_COPPER_LADDER.get(), ILBlocks.OXIDIZED_COPPER_LADDER.get());
		
		add(ILBlocks.IRON_LADDER.get());
		add(ILBlocks.GOLDEN_LADDER.get());
		add(ILBlocks.DIAMOND_LADDER.get());
		add(ILBlocks.OBSIDIAN_LADDER.get());
		add(ILBlocks.NETHERITE_LADDER.get());
		
		add(ILBlocks.CRYING_OBSIDIAN_LADDER.get());
		add(ILBlocks.BEDROCK_LADDER.get());
		
		add(ILBlocks.TIN_LADDER.get());
		add(ILBlocks.BRONZE_LADDER.get());
		add(ILBlocks.STEEL_LADDER.get());
		add(ILBlocks.SILVER_LADDER.get());
		add(ILBlocks.ALUMINIUM_LADDER.get());
		add(ILBlocks.LEAD_LADDER.get());
		add(ILBlocks.PLATINUM_LADDER.get());
		
		add(ILBlocks.CHROMIUM_LADDER.get());
		add(ILBlocks.ADVANCED_ALLOY_LADDER.get());
		add(ILBlocks.NICKEL_LADDER.get());
		add(ILBlocks.TUNGSTEN_LADDER.get());
		add(ILBlocks.TUNGSTEN_STEEL_LADDER.get());
		add(ILBlocks.TITANIUM_LADDER.get());
		add(ILBlocks.ZINC_LADDER.get());
		add(ILBlocks.INVAR_LADDER.get());
		add(ILBlocks.ELECTRUM_LADDER.get());
		
	}
	
}
