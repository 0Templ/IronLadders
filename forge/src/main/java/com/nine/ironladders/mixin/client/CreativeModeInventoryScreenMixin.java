package com.nine.ironladders.mixin.client;

import com.nine.ironladders.client.ClientCache;
import com.nine.ironladders.init.ILCreativeTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends AbstractContainerScreen<AbstractContainerMenu> {
	
	public CreativeModeInventoryScreenMixin(AbstractContainerMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
	}
	
	@Unique
	private CreativeModeTab ironLaddersTab;
	
	@Inject(
			method = "renderTabButton",
			at = @At("HEAD")
	)
	private void il$renderTabButton(GuiGraphics guiGraphics, CreativeModeTab tab, CallbackInfo ci) {
		this.ironLaddersTab = tab;
	}
	
	@Redirect(
			method = "renderTabButton",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphics;renderItem(Lnet/minecraft/world/item/ItemStack;II)V"
			)
	)
	private void redirectRenderItem(GuiGraphics instance, ItemStack stack, int x, int y) {
		if (ironLaddersTab == ILCreativeTab.TAB.get()) {
			ClientCache.getTabIcon().render(instance, x, y);
		} else {
			instance.renderItem(stack, x, y);
		}
	}
}
