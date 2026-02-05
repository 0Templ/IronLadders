package com.nine.ironladders.mixin.feature.inventory.common;

import com.nine.ironladders.common.item.base.InventoryInteractiveItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
	
	@Shadow
	@Final
	public NonNullList<Slot> slots;
	
	@Inject(method = "doClick", at = @At("HEAD"), cancellable = true)
	private void il$doClick(int slotId, int button, ClickType clickType, Player player, CallbackInfo ci) {
		if (slotId < 0 || slotId >= slots.size()) return;
		
		ItemStack carried = player.containerMenu.getCarried();
		Slot slot = slots.get(slotId);
		ItemStack hovered = slot.getItem();
		
		if (carried.getItem() instanceof InventoryInteractiveItem item) {
			if (item.onClickWith(carried, hovered, button, clickType == ClickType.QUICK_MOVE)) {
				ci.cancel();
				return;
			}
		}
		if (hovered.getItem() instanceof InventoryInteractiveItem item) {
			if (item.onClickedBy(hovered, carried, button, clickType == ClickType.QUICK_MOVE)) {
				ci.cancel();
			}
		}
	}
	
}