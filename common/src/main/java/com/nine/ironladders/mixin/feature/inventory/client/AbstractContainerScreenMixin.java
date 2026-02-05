package com.nine.ironladders.mixin.feature.inventory.client;

import com.nine.ironladders.common.item.base.InventoryInteractiveItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu> {
	
	@Shadow
	protected Slot hoveredSlot;
	
	@Shadow
	public abstract T getMenu();
	
	@Inject(method = "render", at = @At("TAIL"))
	public void il$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		var carried = getMenu().getCarried();
		Font font = Minecraft.getInstance().font;
		if (hoveredSlot != null && hoveredSlot.hasItem()){
			var hoveredStack = hoveredSlot.getItem();
			Component component = null;
			if (carried.getItem() instanceof InventoryInteractiveItem tool){
				component = tool.getHoverTooltip(carried, hoveredStack, Screen.hasShiftDown());
			}
			else if (hoveredStack.getItem() instanceof InventoryInteractiveItem tool && !carried.isEmpty()){
				component = tool.getHoverTooltip(carried, hoveredStack, Screen.hasShiftDown());
			}
			if (component != null) {
				guiGraphics.renderTooltip(
						font,
						component,
						mouseX, mouseY);
			}
		}
		
	}

}
