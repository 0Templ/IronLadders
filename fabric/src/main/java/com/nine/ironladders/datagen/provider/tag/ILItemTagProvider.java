package com.nine.ironladders.datagen.provider.tag;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.common.util.ILTags;
import com.nine.ironladders.platform.util.LoaderTarget;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public abstract class ILItemTagProvider extends FabricTagProvider.ItemTagProvider {
	
	protected final LoaderTarget[] targets;
	
	protected final Multimap<TagKey<Item>, Item> items = HashMultimap.create();
	protected final Multimap<TagKey<Item>, TagKey<Item>> includes = HashMultimap.create();
	
	public ILItemTagProvider(FabricDataOutput output,
							 CompletableFuture<HolderLookup.Provider> registriesFuture,
							 LoaderTarget... targets) {
		super(output, registriesFuture);
		this.targets = targets;
		setupTags();
	}
	
	protected void add(TagKey<Item> tag, Item... toAdd) {
		for (var item : toAdd) {
			items.put(tag, item);
		}
	}
	
	protected final void add(TagKey<Item> tag, TagKey<Item>... otherTags) {
		for (var other : otherTags) {
			includes.put(tag, other);
		}
	}
	
	@Override
	protected void addTags(HolderLookup.Provider provider) {
		for (var entry : items.entries()) {
			getOrCreateTagBuilder(entry.getKey()).add(entry.getValue());
		}
		for (var entry : includes.entries()) {
			if (entry.getValue() == null) continue;
			getOrCreateTagBuilder(entry.getKey()).addTag(entry.getValue());
		}
	}
	
	protected void setupTags() {
		
		add(ILTags.COPPER_LADDER,
				ILBlocks.COPPER_LADDER.get().asItem(),
				ILBlocks.EXPOSED_COPPER_LADDER.get().asItem(),
				ILBlocks.WEATHERED_COPPER_LADDER.get().asItem(),
				ILBlocks.OXIDIZED_COPPER_LADDER.get().asItem(),
				ILBlocks.WAXED_COPPER_LADDER.get().asItem(),
				ILBlocks.WAXED_EXPOSED_COPPER_LADDER.get().asItem(),
				ILBlocks.WAXED_WEATHERED_COPPER_LADDER.get().asItem(),
				ILBlocks.WAXED_OXIDIZED_COPPER_LADDER.get().asItem()
		);
		
		add(ItemTags.PIGLIN_LOVED, ILBlocks.GOLDEN_LADDER.get().asItem());
		
	}
	
	public static class Common extends ILItemTagProvider {
		
		public Common(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture, LoaderTarget.FABRIC);
		}
		
	}
	
	public static class Fabric extends ILItemTagProvider {
		
		public Fabric(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture, LoaderTarget.FABRIC);
		}
	}
	
	public static class Forge extends ILItemTagProvider {
		
		public Forge(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture, LoaderTarget.FORGE);
		}
	}
	
	
}
