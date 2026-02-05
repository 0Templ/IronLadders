package com.nine.ironladders.mixin.feature.multiselect.common;

import com.nine.ironladders.common.item.base.BlockHitInteractiveItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {
	
	@Shadow
	@Final
	protected ServerPlayer player;
	@Shadow
	protected ServerLevel level;
	
	@Inject(
			method = "handleBlockBreakAction",
			at = @At("HEAD"),
			cancellable = true
	)
	private void il$onBreak(BlockPos pos,
							ServerboundPlayerActionPacket.Action action,
							Direction dir,
							int worldHeight,
							int sequence,
							CallbackInfo ci) {
		if (action != ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK) return;
		ItemStack stack = player.getMainHandItem();
		Item item = stack.getItem();
		if (!(item instanceof BlockHitInteractiveItem interactiveItem)) return;
		Block block = level.getBlockState(pos).getBlock();
		if (interactiveItem.onBlockHit(player, level, stack, block, pos)){
			ci.cancel();
		}
	}
}