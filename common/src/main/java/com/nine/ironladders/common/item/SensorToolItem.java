package com.nine.ironladders.common.item;

import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.item.base.PropertySwitchToolItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SensorToolItem extends PropertySwitchToolItem {
	
	public SensorToolItem(Properties properties) {
		super(properties);
	}

	@Override
	public Item chargeItem() {
		return Items.REDSTONE;
	}
	
	@Override
	protected BooleanProperty getProperty(){
		return MetalLadderBlock.HAS_SENSOR;
	}
	
}
