package com.nine.ironladders;

import com.nine.ironladders.config.ILConfig;
import com.nine.ironladders.init.*;
import com.nine.ironladders.network.ILNeoForgeNetwork;
import com.nine.ironladders.platform.NeoForgePlatformRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ILCommon.MODID)
public class ILNeoForge {

	public ILNeoForge(IEventBus eventBus) {
		ILConfig.init();

		eventBus.addListener(ILNeoForgeNetwork::init);

		ILBlocks.init();
		ILBlockEntities.init();
		ILComponents.init();
		ILItems.init();
		ILCreativeTab.init();
		
		NeoForgePlatformRegistryHelper.COMPONENT_TYPES.register(eventBus);
		NeoForgePlatformRegistryHelper.BLOCKS.register(eventBus);
		NeoForgePlatformRegistryHelper.BLOCK_ENTITY_TYPES.register(eventBus);
		NeoForgePlatformRegistryHelper.ITEMS.register(eventBus);
		NeoForgePlatformRegistryHelper.TAB.register(eventBus);
	}

}
