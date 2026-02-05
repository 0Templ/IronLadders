package com.nine.ironladders.common.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public interface InventoryInteractiveItem {
	
	// todo: make it like self/other too?
	Component getHoverTooltip(ItemStack carried, ItemStack hovered, boolean shift);
	
	// Return true if vanilla doClick should be cancelled
	boolean onClickWith(ItemStack self, ItemStack other, int button, boolean shift);
	
	boolean onClickedBy(ItemStack self, ItemStack other, int button, boolean shift);

}
