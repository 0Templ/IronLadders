package com.nine.ironladders.datagen.provider.tag;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.platform.util.LoaderTarget;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public abstract class ILBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	
	protected final LoaderTarget[] targets;
	
	protected Multimap<TagKey<Block>, Block> map = HashMultimap.create();
	
	public ILBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, LoaderTarget... targets) {
		super(output, registriesFuture);
		this.targets = targets;
		setupTags();
	}
	
	protected void add(TagKey<Block> tag, Block... blocks) {
		for (var block : blocks)
			map.put(tag, block);
	}
	
	protected void addAll(TagKey<Block> tag) {
		for (var block : ILBlocks.AVAILABLE_LADDERS.get(targets))
			map.put(tag, block);
	}
	
	@Override
	protected void addTags(HolderLookup.Provider provider) {
		var set = ILBlocks.AVAILABLE_LADDERS.get(targets);
		for (var el : map.entries()){
			var ladder = el.getValue();
			if (!set.contains(ladder)) continue;
			var tag = el.getKey();
			getOrCreateTagBuilder(tag).add(ladder);
		}
	}
	
	protected void setupTags(){
		
		addAll(BlockTags.CLIMBABLE);
		addAll(BlockTags.MINEABLE_WITH_PICKAXE);
		
		getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(
				ILBlocks.COPPER_LADDER.get(),
				ILBlocks.EXPOSED_COPPER_LADDER.get(),
				ILBlocks.WEATHERED_COPPER_LADDER.get(),
				ILBlocks.OXIDIZED_COPPER_LADDER.get(),
				ILBlocks.WAXED_COPPER_LADDER.get(),
				ILBlocks.WAXED_EXPOSED_COPPER_LADDER.get(),
				ILBlocks.WAXED_WEATHERED_COPPER_LADDER.get(),
				ILBlocks.WAXED_OXIDIZED_COPPER_LADDER.get(),
				
				ILBlocks.IRON_LADDER.get(),
				ILBlocks.TIN_LADDER.get(),
				ILBlocks.ALUMINIUM_LADDER.get()
		);
		
		getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(
				ILBlocks.GOLDEN_LADDER.get(),
				ILBlocks.DIAMOND_LADDER.get(),
				
				ILBlocks.BRONZE_LADDER.get(),
				ILBlocks.SILVER_LADDER.get(),
				ILBlocks.LEAD_LADDER.get(),
				ILBlocks.STEEL_LADDER.get(),
				
				ILBlocks.ZINC_LADDER.get(),
				ILBlocks.NICKEL_LADDER.get(),
				ILBlocks.INVAR_LADDER.get(),
				ILBlocks.ELECTRUM_LADDER.get()
		);
		
		getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(
				ILBlocks.NETHERITE_LADDER.get(),
				ILBlocks.OBSIDIAN_LADDER.get(),
				ILBlocks.CRYING_OBSIDIAN_LADDER.get(),
				ILBlocks.PLATINUM_LADDER.get(),
				
				ILBlocks.ADVANCED_ALLOY_LADDER.get(),
				ILBlocks.CHROMIUM_LADDER.get(),
				ILBlocks.TUNGSTEN_LADDER.get(),
				ILBlocks.TITANIUM_LADDER.get(),
				ILBlocks.TUNGSTEN_STEEL_LADDER.get()
		);
		
		
		getOrCreateTagBuilder(BlockTags.GUARDED_BY_PIGLINS)
				.add(
						ILBlocks.GOLDEN_LADDER.get()
				);
		
		getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE)
				.add(
						ILBlocks.OBSIDIAN_LADDER.get(),
						ILBlocks.CRYING_OBSIDIAN_LADDER.get(),
						ILBlocks.BEDROCK_LADDER.get()
				);
		
		getOrCreateTagBuilder(BlockTags.FEATURES_CANNOT_REPLACE)
				.add(
						ILBlocks.BEDROCK_LADDER.get()
				
				);
		
		getOrCreateTagBuilder(BlockTags.WITHER_IMMUNE)
				.add(
						ILBlocks.BEDROCK_LADDER.get()
				);
		
		
	}
	
	public static class Fabric extends ILBlockTagProvider {
		
		public Fabric(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture, LoaderTarget.FABRIC, LoaderTarget.COMMON);
		}
	}
	
	public static class Forge extends ILBlockTagProvider {
		
		public Forge(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture, LoaderTarget.FORGE, LoaderTarget.COMMON);
		}
	}
	
	
}
