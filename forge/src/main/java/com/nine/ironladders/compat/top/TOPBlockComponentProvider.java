package com.nine.ironladders.compat.top;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.ILUI;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.block.entity.MetalLadderBlockEntity;
import com.nine.ironladders.common.item.MorphToolItem;
import com.nine.ironladders.common.item.StylerToolItem;
import mcjty.theoneprobe.api.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TOPBlockComponentProvider implements IProbeInfoProvider {
	
	@Override
	public void addProbeInfo(ProbeMode probeMode, IProbeInfo info, Player player, Level level, BlockState state, IProbeHitData data) {
		Block block = state.getBlock();
		ItemStack stack = player.getMainHandItem();
		BlockPos pos = data.getPos();
		if (probeMode == ProbeMode.EXTENDED && !player.isShiftKeyDown()) {
			return;
		}
		if (!(block instanceof MetalLadderBlock)){
			return;
		}
		if (!(level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be)){
			return;
		}
		
		if (state.hasProperty(MetalLadderBlock.HAS_SENSOR)
				&& state.hasProperty(MetalLadderBlock.SENSOR_ACTIVE)
				&& state.getValue(MetalLadderBlock.HAS_SENSOR)
		){
			boolean active = state.getValue(MetalLadderBlock.SENSOR_ACTIVE);
			CompoundText stateText = CompoundText.create()
					.style(TextStyleClass.LABEL)
					.text(Component.translatable("item.ironladders.ladder_sensor_tool.waila.block.state"))
					.style(active ? TextStyleClass.OK : TextStyleClass.ERROR)
					.text(Component.translatable(
							"item.ironladders.ladder_sensor_tool.waila.block.state." + (active ? "on" : "off")
					));
			
			info.text(stateText);
		}
		
		// Model changer Tool
		if (stack.getItem() instanceof StylerToolItem) {
			var modelType = be.modelType();
			if (modelType != null) {
				var styleComp = Component.translatable("item.ironladders.ladder_styler_tool.waila.block.style",
						StylerToolItem.getTypeAsComponent(modelType));
				info.text(CompoundText.create().style(TextStyleClass.LABEL)
						.text(styleComp));
			}
		}
		
		// Morph Tool
		if (stack.getItem() instanceof MorphToolItem) {
			if (MorphToolItem.morphStateId(stack) == 0){
				info.text(CompoundText.create().style(TextStyleClass.LABEL)
						.text(Component.translatable("item.ironladders.ladder_morph_tool.waila.block.copy", ILUI.Text.LBM))
						.style(TextStyleClass.LABEL));
			}
			var morph = be.morphState();
			if (morph != null) {
				var component = Component.translatable("item.ironladders.ladder_morph_tool.waila.block.current",
						Component.translatable(morph.getBlock().getDescriptionId()));
				info.text(CompoundText.create().style(TextStyleClass.LABEL)
						.text(component)
						.style(TextStyleClass.LABEL));
			}
		}
	}
	
	@Override
	public ResourceLocation getID() {
		return ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, "block_info");
	}
}
