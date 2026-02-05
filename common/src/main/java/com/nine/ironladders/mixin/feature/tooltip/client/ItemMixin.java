package com.nine.ironladders.mixin.feature.tooltip.client;

import com.nine.ironladders.client.tooltip.TooltipContext;
import com.nine.ironladders.common.item.base.ContextTooltipItem;
import com.nine.ironladders.mixin.accessor.client.AbstractContainerScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {
	
	@Inject(method = "appendHoverText", at = @At("RETURN"))
	private void il$injectSmartTooltip(ItemStack stack, Level level, List<Component> components, TooltipFlag flag, CallbackInfo ci) {
		if (this instanceof ContextTooltipItem item) {
			TooltipContext context = TooltipContext.UNKNOWN;
			Minecraft mc = Minecraft.getInstance();
			var player = mc.player;
			if (mc.screen instanceof AbstractContainerScreen<?> screen && player != null) {
				Slot hoveredSlot = ((AbstractContainerScreenAccessor)(screen)).getHoveredSlot();
				boolean creativeScreen = screen instanceof CreativeModeInventoryScreen;
				if (hoveredSlot == null || (creativeScreen && player.getInventory() != hoveredSlot.container)){
					context = TooltipContext.REFERENCE;
				}else if (hoveredSlot.getItem() == stack) {
					context = TooltipContext.INVENTORY;
				}
			}
			item.appendContextTooltip(stack, level, components, flag, context);
		}
	}
}