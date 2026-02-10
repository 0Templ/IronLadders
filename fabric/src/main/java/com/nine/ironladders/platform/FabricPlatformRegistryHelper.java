package com.nine.ironladders.platform;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.platform.util.BlockEntityFactory;
import com.nine.ironladders.platform.util.FabricRegistryProvider;
import com.nine.ironladders.platform.util.RegistryProvider;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Function;
import java.util.function.Supplier;

public class FabricPlatformRegistryHelper implements IPlatformRegistryHelper {
	
	@Override
	public <T> RegistryProvider<DataComponentType<T>> registerComponent(String id, DataComponentType<T> dataComponentType) {
		var ret = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, id), dataComponentType);
		return new FabricRegistryProvider<>(ret);
	}
	
	
	@Override
	public RegistryProvider<Item> registerItem(String id, Supplier<Item> itemSupplier) {
		var ret = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, id), itemSupplier.get());
		return new FabricRegistryProvider<>(ret);
	}
	
	@Override
	public RegistryProvider<Block> registerBlock(String id, Supplier<Block> blockSupplier, Function<Block, BlockItem> itemFactory) {
		var ret = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, id), blockSupplier.get());
		Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, id), itemFactory.apply(ret));
		return new FabricRegistryProvider<>(ret);
	}
	
	@Override
	public <T extends BlockEntity> RegistryProvider<BlockEntityType<T>> registerBlockEntityType(String id, BlockEntityFactory<T> factory, Supplier<Block[]> validBlocks) {
		var ret = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, id),
				FabricBlockEntityTypeBuilder.create(factory::create, validBlocks.get()).build());
		return new FabricRegistryProvider<>(ret);
	}
	
	@Override
	public RegistryProvider<CreativeModeTab> registerCreativeTab(String id, CreativeModeTab.Builder builder) {
		var tab = builder.build();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, id), tab);
		return new FabricRegistryProvider<>(tab);
	}
	
}

