package com.nine.ironladders;

import com.nine.ironladders.config.ILConfig;
import com.nine.ironladders.event.FabricCommonEvents;
import com.nine.ironladders.init.ILBlockEntities;
import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.init.ILCreativeTab;
import com.nine.ironladders.init.ILItems;
import com.nine.ironladders.network.ILFabricNetwork;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;

public class ILFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
		ILConfig.init();
		
		ILFabricNetwork.initServer();
		
		{
			ILBlocks.init();
			ILBlockEntities.init();
			ILItems.init();
			ILCreativeTab.init();
		}
		
		injectCopperLadders();
		FabricCommonEvents.registerCommon();
    }
	
	
	void injectCopperLadders(){
		OxidizableBlocksRegistry.registerOxidizableBlockPair(ILBlocks.COPPER_LADDER.get(), ILBlocks.EXPOSED_COPPER_LADDER.get());
		OxidizableBlocksRegistry.registerOxidizableBlockPair(ILBlocks.EXPOSED_COPPER_LADDER.get(), ILBlocks.WEATHERED_COPPER_LADDER.get());
		OxidizableBlocksRegistry.registerOxidizableBlockPair(ILBlocks.WEATHERED_COPPER_LADDER.get(), ILBlocks.OXIDIZED_COPPER_LADDER.get());
		
		OxidizableBlocksRegistry.registerWaxableBlockPair(ILBlocks.COPPER_LADDER.get(), ILBlocks.WAXED_COPPER_LADDER.get());
		OxidizableBlocksRegistry.registerWaxableBlockPair(ILBlocks.EXPOSED_COPPER_LADDER.get(), ILBlocks.WAXED_EXPOSED_COPPER_LADDER.get());
		OxidizableBlocksRegistry.registerWaxableBlockPair(ILBlocks.WEATHERED_COPPER_LADDER.get(), ILBlocks.WAXED_WEATHERED_COPPER_LADDER.get());
		OxidizableBlocksRegistry.registerWaxableBlockPair(ILBlocks.OXIDIZED_COPPER_LADDER.get(), ILBlocks.WAXED_OXIDIZED_COPPER_LADDER.get());
		
	}
}
