package com.nine.ironladders.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class UpgradeToolItem extends Item {
	
	public UpgradeToolItem(Properties properties) {
		super(properties);
	}
	
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (hand != InteractionHand.MAIN_HAND) {
			return InteractionResultHolder.pass(player.getItemInHand(hand));
		}

		return InteractionResultHolder.pass(player.getItemInHand(hand));
	}
}
