package com.nine.ironladders.compat.wthit;

import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.config.ILConfig;
import mcp.mobius.waila.api.IClientRegistrar;
import mcp.mobius.waila.api.IWailaClientPlugin;

public class WTHITClientSetup implements IWailaClientPlugin {
	
	@Override
	public void register(IClientRegistrar registrar) {
		if (!ILConfig.WTHIT_INTEGRATION.get()){
			return;
		}
		
		registrar.body(new WTHITBlockComponentProvider(), MetalLadderBlock.class);
		
	}

}