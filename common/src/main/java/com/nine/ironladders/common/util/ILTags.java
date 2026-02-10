package com.nine.ironladders.common.util;

import com.nine.ironladders.ILCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ILTags {
	
	public static final TagKey<Item> COPPER_LADDER = createItemTag(ILCommon.MODID, "copper_ladder");
	
	
	public static TagKey<Item> createItemTag(String namespace, String id) {
		return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, id));
	}
	
	public static TagKey<Block> createBlockTag(String id) {
		return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, id));
	}
}

