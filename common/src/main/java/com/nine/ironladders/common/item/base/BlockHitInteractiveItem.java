package com.nine.ironladders.common.item.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public interface BlockHitInteractiveItem {
	
	boolean onBlockHit(Player player, Level level, ItemStack stack, Block block, BlockPos pos);
	
}
