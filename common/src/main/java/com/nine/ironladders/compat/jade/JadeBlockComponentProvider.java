package com.nine.ironladders.compat.jade;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.ILUI;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.block.entity.MetalLadderBlockEntity;
import com.nine.ironladders.common.item.CasingToolItem;
import com.nine.ironladders.common.item.MorphToolItem;
import com.nine.ironladders.common.item.StylerToolItem;
import com.nine.ironladders.config.ILConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class JadeBlockComponentProvider implements IBlockComponentProvider {
	
	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig pluginConfig) {
		Player player = blockAccessor.getPlayer();
		Block block = blockAccessor.getBlock();
		
		if (player == null || !(block instanceof MetalLadderBlock)) {
			return;
		}
		if (!(blockAccessor.getBlockEntity() instanceof MetalLadderBlockEntity be)){
			return;
		}
		BlockState state = blockAccessor.getBlockState();
		if (state.hasProperty(MetalLadderBlock.HAS_SENSOR)
				&& state.hasProperty(MetalLadderBlock.SENSOR_ACTIVE)
				&& state.getValue(MetalLadderBlock.HAS_SENSOR)
		){
			boolean active = state.getValue(MetalLadderBlock.SENSOR_ACTIVE);
			tooltip.add(Component.translatable("item.ironladders.ladder_sensor_tool.waila.block.state")
							.append(Component.translatable("item.ironladders.ladder_sensor_tool.waila.block.state."
									+ (active ? "on" : "off")).withStyle(active ? ChatFormatting.GREEN : ChatFormatting.RED))
							.withStyle(ChatFormatting.GRAY)
			);
		}

		ItemStack stack = player.getMainHandItem();

		// Model changer Tool
		if (stack.getItem() instanceof StylerToolItem){
			var modelType = be.modelType();
			if (modelType != null) {
				tooltip.add(Component.translatable("item.ironladders.ladder_styler_tool.waila.block.style",
						ILUI.withColor(StylerToolItem.getTypeAsComponent(modelType), ILUI.Color.SOFT_GRAY))
						.withStyle(ChatFormatting.GRAY));
			}
		}
		
		// Morph Tool
		if (stack.getItem() instanceof MorphToolItem) {
			if (MorphToolItem.morphStateId(stack) == 0) {
				tooltip.add(Component.translatable("item.ironladders.ladder_morph_tool.waila.block.copy",
								ILUI.withColor(ILUI.Text.LBM, ILUI.Color.SOFT_GRAY))
						.withStyle(ChatFormatting.GRAY));
			}
			var morph = be.morphState();
			if (morph != null) {
				tooltip.add(Component.translatable("item.ironladders.ladder_morph_tool.waila.block.current",
								ILUI.withColor(Component.translatable(morph.getBlock().getDescriptionId()), ILUI.Color.SOFT_GRAY))
						.withStyle(ChatFormatting.GRAY));
			}
		}
		
		// Casing Tool
		if (stack.getItem() instanceof CasingToolItem) {
			boolean hidden = be.isHideAttachments();
			tooltip.add(Component.translatable("item.ironladders.ladder_casing_tool.waila.block.state",
							ILUI.withColor(
									Component.translatable("item.ironladders.ladder_casing_tool.waila.block.state."
											+ (hidden ? "hidden" : "revealed")),
									ILUI.Color.SOFT_GRAY
							))
					.withStyle(ChatFormatting.GRAY));
		}
	}
	
	@Override
	public ResourceLocation getUid() {
		return new ResourceLocation(ILCommon.MODID);
	}
	
}
