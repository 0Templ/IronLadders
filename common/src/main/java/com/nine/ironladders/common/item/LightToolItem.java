package com.nine.ironladders.common.item;

import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.item.base.PropertySwitchToolItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class LightToolItem extends PropertySwitchToolItem {
	
	public LightToolItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public Item chargeItem() {
		return Items.GLOWSTONE_DUST;
	}
	
	@Override
	protected BooleanProperty getProperty(){
		return MetalLadderBlock.LIGHTED;
	}
	
	@Override
	protected void playPlaceSound(Level level, BlockPos pos){
		level.playSound(
				null, pos, SoundEvents.GLASS_PLACE,
				SoundSource.BLOCKS, 1.0f, 0.7F
		);
	}
	
	@Override
	protected void playRemoveSound(Level level, BlockPos pos){
		level.playSound(
				null, pos, SoundEvents.GLASS_PLACE,
				SoundSource.BLOCKS, 1.0f, 0.9F
		);
	}
	
}
