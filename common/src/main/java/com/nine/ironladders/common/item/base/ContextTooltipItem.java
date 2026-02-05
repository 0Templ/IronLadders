package com.nine.ironladders.common.item.base;

import com.nine.ironladders.client.tooltip.TooltipContext;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public interface ContextTooltipItem {
	
	void appendContextTooltip(
			ItemStack stack,
			Level level,
			List<Component> components,
			TooltipFlag flag,
			TooltipContext type);

}
