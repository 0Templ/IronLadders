package com.nine.ironladders.mixin.feature.scroll.client;

import com.nine.ironladders.common.item.base.CustomHighlightNameItem;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Gui.class)
public class GuiMixin {

	@Shadow
	private ItemStack lastToolHighlight;

	@ModifyVariable(
			method = "Lnet/minecraft/client/gui/Gui;renderSelectedItemName(Lnet/minecraft/client/gui/GuiGraphics;I)V",
			at = @At(value = "STORE"),
			ordinal = 0
	)
	private MutableComponent il$renderSelectedItemName(MutableComponent original) {
		if (lastToolHighlight.getItem() instanceof CustomHighlightNameItem customItem) {
			return customItem.getCustomHighlightName(lastToolHighlight, original);
		}
		return original;
	}
}
