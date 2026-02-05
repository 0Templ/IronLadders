package com.nine.ironladders.mixin.feature.multiselect.client;

import com.nine.ironladders.common.item.base.BlockHitInteractiveItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
	
	@Inject(method = "destroyBlock", at = @At(value = "HEAD"), cancellable = true)
	public void il$destroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		Player player = Minecraft.getInstance().player;
		if (player == null) return;
		Level level = player.level();
		ItemStack stack = player.getMainHandItem();
		Item item = stack.getItem();
		if (!(item instanceof BlockHitInteractiveItem interactiveItem)) return;
		Block block = player.level().getBlockState(pos).getBlock();
		if (interactiveItem.onBlockHit(player, level, stack, block, pos)) {
			cir.setReturnValue(true);
		}
	}
}