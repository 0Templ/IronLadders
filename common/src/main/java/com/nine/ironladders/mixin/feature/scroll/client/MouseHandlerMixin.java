package com.nine.ironladders.mixin.feature.scroll.client;

import com.nine.ironladders.common.item.base.HotBarScrollableItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
	
	@Shadow
	@Final
	private Minecraft minecraft;
	
	@Inject(method = "onScroll",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;swapPaint(D)V",
					ordinal = 0), cancellable = true)
	public void onRawMouseScrolled(long windowPointer, double xOffset, double yOffset, CallbackInfo ci) {
		Player player = minecraft.player;
		if (player != null ){
			var stack = player.getMainHandItem();
			if (stack.getItem() instanceof HotBarScrollableItem item){
				if (item.onHotBarMouseScroll(player, stack, xOffset, yOffset))
					ci.cancel();
			}
		}
	
	}
	
	
}
