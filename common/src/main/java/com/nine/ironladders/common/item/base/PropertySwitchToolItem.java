package com.nine.ironladders.common.item.base;

import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.block.entity.MetalLadderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class PropertySwitchToolItem extends StoredChargeToolItem {
	
	protected PropertySwitchToolItem(Properties properties) {
		super(properties);
	}
	
	protected abstract BooleanProperty getProperty();
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		Player player = context.getPlayer();
		
		if (player == null) return InteractionResult.PASS;
		if (!(state.getBlock() instanceof MetalLadderBlock)) return InteractionResult.PASS;
		if (!state.hasProperty(getProperty())) return InteractionResult.PASS;
		
		boolean current = state.getValue(getProperty());
		if (current && !player.isShiftKeyDown()) return InteractionResult.PASS;
		
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		
		ItemStack stack = context.getItemInHand();
		int currentCharges = getCharges(stack);
		if (currentCharges < 0) {
			return InteractionResult.FAIL;
		}
		if (!current && !player.isCreative() && currentCharges <= 0) {
			return InteractionResult.FAIL;
		}
		BlockState newState = state.setValue(getProperty(), !current);
		level.setBlock(pos, newState, Block.UPDATE_ALL);
		
		if (level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be) {
			boolean creative = player.isCreative();
			if (!current) {
				be.addToDrop(chargeItem(), 1);
				playPlaceSound(level, pos);
				if (!creative) {
					changeCharges(stack, -1);
				}
			} else if (be.hasItem(chargeItem())) {
				playRemoveSound(level, pos);
				be.removeFromDrop(chargeItem());
				if (currentCharges < maxSavedCharges() && !creative){
					changeCharges(stack, 1);
				}
				else if (!creative){
					Block.popResource(level, pos, new ItemStack(chargeItem()));
				}
			}
			be.setChanged();
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
	
	protected void playPlaceSound(Level level, BlockPos pos){
		level.playSound(
				null, pos, level.getBlockState(pos).getSoundType().getPlaceSound(),
				SoundSource.BLOCKS, 1.0f, 1.2f
		);
	}
	
	protected void playRemoveSound(Level level, BlockPos pos){
		level.playSound(
				null, pos, level.getBlockState(pos).getSoundType().getBreakSound(),
				SoundSource.BLOCKS, 1.0f, 1.2f
		);
	}
	
}
