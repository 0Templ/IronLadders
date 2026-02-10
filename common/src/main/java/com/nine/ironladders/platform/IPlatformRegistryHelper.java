package com.nine.ironladders.platform;

import com.nine.ironladders.platform.util.BlockEntityFactory;
import com.nine.ironladders.platform.util.RegistryProvider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Function;
import java.util.function.Supplier;

public interface IPlatformRegistryHelper {
	
	
	<T> RegistryProvider<DataComponentType<T>> registerComponent(String id, DataComponentType<T> dataComponentType);
	
	RegistryProvider<Item> registerItem(String id, Supplier<Item> itemSupplier);
	
	RegistryProvider<Block> registerBlock(String id, Supplier<Block> blockSupplier, Function<Block, BlockItem> itemFactory);
	
	<T extends BlockEntity> RegistryProvider<BlockEntityType<T>> registerBlockEntityType(
			String id,
			BlockEntityFactory<T> factory,
			Supplier<Block[]> validBlocks
	);
	
	RegistryProvider<CreativeModeTab> registerCreativeTab(String id, CreativeModeTab.Builder builder);
	
	
	
}
