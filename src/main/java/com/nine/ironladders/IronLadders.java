package com.nine.ironladders;

import com.nine.ironladders.common.CommonEvents;
import com.nine.ironladders.init.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.loader.api.FabricLoader;


import java.io.File;

public class IronLadders implements ModInitializer {
	public static final String MODID = "ironladders";

	public static ILConfig CONFIG;

	@Override
	public void onInitialize() {
		CONFIG = ILConfig.loadConfig(new File(FabricLoader.getInstance().getConfigDir() + "/ironladders_config.json"));
		ItemRegistry.register();
		BlockRegistry.register();
		CreativeTabGroup.register();
		CommonEvents.init();

		OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistry.COPPER_LADDER, BlockRegistry.EXPOSED_COPPER_LADDER);
		OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistry.EXPOSED_COPPER_LADDER, BlockRegistry.WEATHERED_COPPER_LADDER);
		OxidizableBlocksRegistry.registerOxidizableBlockPair(BlockRegistry.WEATHERED_COPPER_LADDER, BlockRegistry.OXIDIZED_COPPER_LADDER);

		OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistry.COPPER_LADDER, BlockRegistry.WAXED_COPPER_LADDER);
		OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistry.EXPOSED_COPPER_LADDER, BlockRegistry.WAXED_EXPOSED_COPPER_LADDER);
		OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistry.WEATHERED_COPPER_LADDER, BlockRegistry.WAXED_WEATHERED_COPPER_LADDER);
		OxidizableBlocksRegistry.registerWaxableBlockPair(BlockRegistry.OXIDIZED_COPPER_LADDER, BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER);


	}
}