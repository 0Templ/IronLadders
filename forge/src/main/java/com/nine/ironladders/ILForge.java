package com.nine.ironladders;

import com.nine.ironladders.config.ILConfig;
import com.nine.ironladders.init.ILBlockEntities;
import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.init.ILCreativeTab;
import com.nine.ironladders.init.ILItems;
import com.nine.ironladders.network.ILForgeNetwork;
import com.nine.ironladders.platform.ForgePlatformRegistryHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ILCommon.MODID)
public class ILForge {
	
	@SuppressWarnings("removal")
	public ILForge() {
		ILConfig.init();
		
		ILForgeNetwork.init();
		
		var modBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		ILBlocks.init();
		ILBlockEntities.init();
		ILItems.init();
		ILCreativeTab.init();
		
		ForgePlatformRegistryHelper.BLOCKS.register(modBus);
		ForgePlatformRegistryHelper.BLOCK_ENTITY_TYPES.register(modBus);
		ForgePlatformRegistryHelper.ITEMS.register(modBus);
		ForgePlatformRegistryHelper.TAB.register(modBus);
		
	}
	
}
