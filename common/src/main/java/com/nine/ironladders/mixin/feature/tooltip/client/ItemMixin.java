package com.nine.ironladders.mixin.feature.tooltip.client;

import com.nine.ironladders.client.tooltip.TooltipSource;
import com.nine.ironladders.common.item.base.ContextTooltipItem;
import com.nine.ironladders.mixin.accessor.client.AbstractContainerScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {
	
	@Inject(method = "appendHoverText", at = @At("RETURN"))
	private void il$injectSmartTooltip(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag, CallbackInfo ci) {
		if (this instanceof ContextTooltipItem item) {
			TooltipSource source = TooltipSource.UNKNOWN;
			Minecraft mc = Minecraft.getInstance();
			var player = mc.player;
			if (mc.screen instanceof AbstractContainerScreen<?> screen && player != null) {
				Slot hoveredSlot = ((AbstractContainerScreenAccessor)(screen)).getHoveredSlot();
				boolean creativeScreen = screen instanceof CreativeModeInventoryScreen;
				if (hoveredSlot == null || (creativeScreen && player.getInventory() != hoveredSlot.container)){
					source = TooltipSource.REFERENCE;
				}else if (hoveredSlot.getItem() == stack) {
					source = TooltipSource.INVENTORY;
				}
			}
			item.appendContextTooltip(stack, mc.level, components, tooltipFlag, source);
		}
	}
}