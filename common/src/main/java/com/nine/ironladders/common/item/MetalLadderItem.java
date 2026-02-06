package com.nine.ironladders.common.item;

import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.client.ILUI;
import com.nine.ironladders.common.block.MetalLadderBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class MetalLadderItem extends BlockItem {
	
	public MetalLadderItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag isAdvanced) {
		if (ClientHelper.shiftPressed()){
			MetalLadderBlock block = (MetalLadderBlock) getBlock();
			var speed = Math.round((block.getSpeedMultiplier() * 20) * 1000.0) / 1000.0;
			components.add(Component.translatable("block.ironladders.speed",
					ILUI.withColor(Component.literal("+" + speed), ILUI.Color.SOFT_GRAY)).withStyle(ChatFormatting.GRAY));
		}
	}
	
	
}
