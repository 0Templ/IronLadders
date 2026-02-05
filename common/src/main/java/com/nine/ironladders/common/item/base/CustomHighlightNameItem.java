package com.nine.ironladders.common.item.base;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public interface CustomHighlightNameItem {
	
	MutableComponent getCustomHighlightName(ItemStack stack, MutableComponent original);
	
}
