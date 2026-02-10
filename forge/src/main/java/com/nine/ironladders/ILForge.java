package com.nine.ironladders;

import com.nine.ironladders.config.ILConfig;
import com.nine.ironladders.init.*;
import com.nine.ironladders.network.ILForgeNetwork;
import com.nine.ironladders.platform.ForgePlatformRegistryHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ILCommon.MODID)
public class ILForge {
	
	public ILForge(FMLJavaModLoadingContext context) {
		ILConfig.init();
		
		ILForgeNetwork.init();
		
		var modBus = context.getModEventBus();
		
		ILBlocks.init();
		ILBlockEntities.init();
		ILComponents.init();
		ILItems.init();
		ILCreativeTab.init();
		
		ForgePlatformRegistryHelper.COMPONENT_TYPES.register(modBus);
		ForgePlatformRegistryHelper.BLOCKS.register(modBus);
		ForgePlatformRegistryHelper.BLOCK_ENTITY_TYPES.register(modBus);
		ForgePlatformRegistryHelper.ITEMS.register(modBus);
		ForgePlatformRegistryHelper.TAB.register(modBus);
		
	}
	
}
