package com.nine.ironladders.compat.jade;

import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.config.ILConfig;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeSetup implements IWailaPlugin {
	
	@Override
	public void registerClient(IWailaClientRegistration registration) {
		if (!ILConfig.JADE_INTEGRATION.get()){
			return;
		}
		
		registration.registerBlockComponent(new JadeBlockComponentProvider(), MetalLadderBlock.class);
	}

}
