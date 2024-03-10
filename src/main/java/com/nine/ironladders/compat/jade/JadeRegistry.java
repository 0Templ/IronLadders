package com.nine.ironladders.compat.jade;

import com.nine.ironladders.common.block.BaseMetalLadder;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeRegistry implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(MetalLadderComponentProvider.INSTANCE, BaseMetalLadder.class);
    }
}