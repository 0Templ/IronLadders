package com.nine.ironladders.platform;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.platform.util.BlockEntityFactory;
import com.nine.ironladders.platform.util.ForgeRegistryObject;
import com.nine.ironladders.platform.util.RegistryProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class ForgePlatformRegistryHelper implements IPlatformRegistryHelper {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ILCommon.MODID);
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ILCommon.MODID);
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
			DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ILCommon.MODID);
	
	public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ILCommon.MODID);
	
	@Override
	public com.nine.ironladders.platform.util.RegistryProvider<Item> registerItem(String id, Supplier<Item> itemSupplier) {
		return new ForgeRegistryObject<>(ITEMS.register(id, itemSupplier));
	}
	
	@Override
	public com.nine.ironladders.platform.util.RegistryProvider<Block> registerBlock(String id, Supplier<Block> blockSupplier, Function<Block, BlockItem> itemFactory) {
		var ret = BLOCKS.register(id, blockSupplier);
		ITEMS.register(id, () -> itemFactory.apply(ret.get()));
		return new ForgeRegistryObject<>(ret);
	}
	
	@Override
	public <T extends BlockEntity> RegistryProvider<BlockEntityType<T>> registerBlockEntityType(String id, BlockEntityFactory<T> factory, Supplier<Block[]> validBlocks) {
		return new ForgeRegistryObject<>(BLOCK_ENTITY_TYPES.register(id,
				() -> BlockEntityType.Builder.of(factory::create, validBlocks.get()).build(null)));
	}
	
	@Override
	public RegistryProvider<CreativeModeTab> registerCreativeTab(String id, CreativeModeTab.Builder builder) {
		var tab = TAB.register(id, builder::build);
		return new ForgeRegistryObject<>(tab);
	}
}
