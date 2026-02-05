package com.nine.ironladders.common.item.base;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface HotBarScrollableItem {
	
	boolean onHotBarMouseScroll(Player player, ItemStack stack, double xOffset, double yOffset);
	
}
