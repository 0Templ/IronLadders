package com.nine.ironladders.mixin.feature.inventory.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nine.ironladders.client.ILUI;
import com.nine.ironladders.common.item.base.StoredChargeToolItem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
	
	
	@Shadow
	@Final
	private PoseStack pose;
	
	@Shadow
	public abstract int drawString(Font font, String text, int x, int y, int color, boolean b);
	
	@Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
	at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
	public void il$renderItemDecorations(Font font, ItemStack stack, int x, int y, String text, CallbackInfo ci) {
		if (stack.getItem() instanceof StoredChargeToolItem){
			il$renderChargableToolToDoChangeThisNamingDecorations(font, stack, x, y);
		}
	}
	
	private void il$renderChargableToolToDoChangeThisNamingDecorations(Font font, ItemStack stack, int x, int y){
		int current = StoredChargeToolItem.getCharges(stack);
		if (current == 0) return;
		pose.pushPose();
		pose.translate(0.0F, 0.0F, 200.0F);
		String text = String.valueOf(current);
		drawString(font, text, x + 0, y + 9, ILUI.Color.WHITE, true);
		pose.popPose();
	}

}
