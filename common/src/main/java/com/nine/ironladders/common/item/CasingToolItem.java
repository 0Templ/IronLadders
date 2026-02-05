package com.nine.ironladders.common.item;

import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.client.ILUI;
import com.nine.ironladders.client.tooltip.TooltipContext;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.block.entity.MetalLadderBlockEntity;
import com.nine.ironladders.common.item.base.BlockHitInteractiveItem;
import com.nine.ironladders.common.item.base.ContextTooltipItem;
import com.nine.ironladders.common.util.PositionUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.List;
import java.util.Map;

public class CasingToolItem extends Item implements ContextTooltipItem, BlockHitInteractiveItem {
	
	private static final int MAX_DEPTH = 256;
	
	public static final Map<BooleanProperty, Boolean> PROPERTIES_TO_HIDE = Map.of(
			MetalLadderBlock.LIGHTED, false,
			MetalLadderBlock.HAS_SENSOR, false
	);
	
	public CasingToolItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public void appendContextTooltip(
			ItemStack stack,
			Level level,
			List<Component> components,
			TooltipFlag flag,
			TooltipContext type
	) {
		if (type == TooltipContext.REFERENCE){
			components.add(Component.translatable("item.ironladders.ladder_casing_tool.desc").withStyle(ChatFormatting.GRAY));
		}
		else {
			components.add(Component.translatable("item.ironladders.ladder_casing_tool.use",
					ILUI.withColor(ILUI.Text.RBM, ILUI.Color.SOFT_GRAY)).withStyle(ChatFormatting.GRAY));
			components.add(Component.translatable("item.ironladders.ladder_casing_tool.remove",
					ILUI.withColor(ILUI.Text.LBM, ILUI.Color.SOFT_GRAY)).withStyle(ChatFormatting.GRAY));
			components.add(Component.translatable("item.ironladders.ladder_casing_tool.multiple_use",
					ILUI.withColor(ILUI.Text.SHIFT, ClientHelper.shiftPressed() ? ILUI.Color.GRAY : ILUI.Color.SOFT_GRAY))
					.withStyle(ChatFormatting.GRAY));
		}
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		var pos = context.getClickedPos();
		var level = context.getLevel();
		var state = level.getBlockState(pos);
		var block = state.getBlock();
		Player player = context.getPlayer();
		if (block instanceof MetalLadderBlock && player != null && level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be) {
			boolean value = true;
			boolean changed;
			if (player.isShiftKeyDown()) {
				changed = applyMultipleEffect(level, pos, value);
			} else {
				changed = be.isHideAttachments() != value;
				if (!level.isClientSide()) {
					applyCasingEffect(be, value);
				}
			}
			if (changed) {
				if (!level.isClientSide()) {
					level.playSound(
							null,
							pos,
							SoundEvents.SHROOMLIGHT_PLACE,
							SoundSource.PLAYERS, 1.0F, 0.9F
					);
				}
				return InteractionResult.SUCCESS;
			}
			return InteractionResult.PASS;
		}
		return InteractionResult.PASS;
	}
	
	private static void applyCasingEffect(MetalLadderBlockEntity be, boolean value){
		if (be.isHideAttachments() != value){
			be.setHideAttachments(value);
		}
	}
	
	@Override
	public boolean onBlockHit(Player player, Level level, ItemStack stack, Block block, BlockPos pos) {
		if (block instanceof MetalLadderBlock && level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be){
			boolean value = false;
			if (!level.isClientSide()){
				boolean changed = false;
				if (player.isShiftKeyDown()) {
					changed = applyMultipleEffect(level, pos, value);
				}
				else if (be.isHideAttachments() != value) {
					changed = be.isHideAttachments() != value;
					if (!level.isClientSide()) {
						applyCasingEffect(be, value);
					}
				}
				if (changed){
					level.playSound(
							null,
							pos,
							SoundEvents.SHROOMLIGHT_PLACE,
							SoundSource.PLAYERS, 1.0F, 1.12F
					);
				}
				
			}

			return true;
			
		}
		return false;
	}
	
	private static boolean applyMultipleEffect(Level level, BlockPos startPos, boolean value) {
		if (!(level.getBlockEntity(startPos) instanceof MetalLadderBlockEntity startBe)) return false;
		var startState = startBe.getBlockState();
		if (!startState.hasProperty(LadderBlock.FACING)) return false;
		var startDir = startState.getValue(LadderBlock.FACING);
		boolean[] changed = {false};
		PositionUtils.walkAndApply(
				startPos,
				MAX_DEPTH,
				pos -> getHideTarget(level, pos, startDir),
				(pos, foundBe) -> {
					if (foundBe.isHideAttachments() != value) {
						changed[0] = true;
						if (!level.isClientSide()){
							foundBe.setHideAttachments(value);
						}
					}
				},
				Direction.DOWN, Direction.UP
		);
		return changed[0];
	}
	
	private static MetalLadderBlockEntity getHideTarget(Level level, BlockPos pos, Direction startDir) {
		BlockState state = level.getBlockState(pos);
		if (!(state.getBlock() instanceof MetalLadderBlock)) return null;
		if (!state.hasProperty(LadderBlock.FACING) || state.getValue(LadderBlock.FACING) != startDir) return null;
		if (level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be) {
			return be;
		}
		return null;
	}
	
}
