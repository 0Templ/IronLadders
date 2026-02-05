package com.nine.ironladders.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.nine.ironladders.client.ClientCache;
import com.nine.ironladders.init.ILCreativeTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {

	@WrapOperation(
			method = "renderTabButton",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphics;renderItem(Lnet/minecraft/world/item/ItemStack;II)V"
			)
	)
	private void il$renderTabButton(
			GuiGraphics instance,
			ItemStack stack,
			int x,
			int y,
			Operation<Void> original,
			@Local(argsOnly = true) CreativeModeTab creativeModeTab
	) {
		if (creativeModeTab == ILCreativeTab.TAB.get()) {
			ClientCache.getTabIcon().render(instance, x, y);
		} else {
			original.call(instance, stack, x, y);
		}
	}
}
