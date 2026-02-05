package com.nine.ironladders.compat.nei;

import com.nine.ironladders.common.material.RecipeHelper;
import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.init.ILItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeiHelper {
	
	private NeiHelper(){}
	
	public static final Map<Item, Component> NEI_ITEM_INFO_MAP  = new HashMap<>();
	
	private static List<ItemStack> hiddenNEIStacksCache = null;
	
	static {
		NEI_ITEM_INFO_MAP.put(
				ILItems.MORPH_TOOL.get(),
				Component.translatable("item.ironladders.ladder_morph_tool.nei.info")
		);
		NEI_ITEM_INFO_MAP.put(
				ILItems.CASING_TOOL.get(),
				Component.translatable("item.ironladders.ladder_morph_tool.nei.info")
		);
		NEI_ITEM_INFO_MAP.put(
				ILItems.LIGHT_TOOL.get(),
				Component.translatable("item.ironladders.ladder_light_tool.nei.info")
		);
		NEI_ITEM_INFO_MAP.put(
				ILItems.SENSOR_TOOL.get(),
				Component.translatable("item.ironladders.ladder_sensor_tool.nei.info")
		);
		NEI_ITEM_INFO_MAP.put(
				ILItems.STYLER_TOOL.get(),
				Component.translatable("item.ironladders.ladder_styler_tool.nei.info")
		);
		
	}
	
	
	public static List<ItemStack> hiddenNEIStacks() {
		if (hiddenNEIStacksCache != null) {
			return hiddenNEIStacksCache;
		}
		var ret = new ArrayList<>(RecipeHelper.hiddenStacks());
		ret.add(ILBlocks.CRYING_OBSIDIAN_LADDER.get().asItem().getDefaultInstance());
		hiddenNEIStacksCache = ret;
		return hiddenNEIStacksCache;
	}
	
}
