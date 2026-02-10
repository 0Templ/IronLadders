package com.nine.ironladders.platform;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.platform.util.BlockEntityFactory;
import com.nine.ironladders.platform.util.RegistryProvider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgePlatformRegistryHelper implements IPlatformRegistryHelper {

	public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPES =
			DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ILCommon.MODID);

	public static final DeferredRegister<Item> ITEMS =
			DeferredRegister.create(Registries.ITEM, ILCommon.MODID);

	public static final DeferredRegister<Block> BLOCKS =
			DeferredRegister.create(Registries.BLOCK, ILCommon.MODID);

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
			DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ILCommon.MODID);

	public static final DeferredRegister<CreativeModeTab> TAB =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ILCommon.MODID);

	@Override
	@SuppressWarnings("unchecked")
	public <T> RegistryProvider<DataComponentType<T>> registerComponent(String id, DataComponentType<T> dataComponentType) {
		var ret = COMPONENT_TYPES.register(id, () -> dataComponentType);
		return () -> (DataComponentType<T>) ret.get();
	}

	@Override
	public RegistryProvider<Item> registerItem(String id, Supplier<Item> itemSupplier) {
		var ret = ITEMS.register(id, itemSupplier);
		return ret::get;
	}

	@Override
	public RegistryProvider<Block> registerBlock(String id, Supplier<Block> blockSupplier, Function<Block, BlockItem> itemFactory) {
		var ret = BLOCKS.register(id, blockSupplier);
		ITEMS.register(id, () -> itemFactory.apply(ret.get()));
		return ret::get;
	}

	@Override
	public <T extends BlockEntity> RegistryProvider<BlockEntityType<T>> registerBlockEntityType(String id, BlockEntityFactory<T> factory, Supplier<Block[]> validBlocks) {
		var ret = BLOCK_ENTITY_TYPES.register(id, () -> BlockEntityType.Builder.of(factory::create, validBlocks.get()).build(null));
		return ret::get;
	}

	@Override
	public RegistryProvider<CreativeModeTab> registerCreativeTab(String id, CreativeModeTab.Builder builder) {
		var ret = TAB.register(id, builder::build);
		return ret::get;
	}
}
