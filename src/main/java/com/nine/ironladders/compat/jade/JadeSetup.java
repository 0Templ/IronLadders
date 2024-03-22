package com.nine.ironladders.compat.jade;

import net.minecraft.world.level.block.LadderBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeSetup implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(MetalLadderComponentProvider.INSTANCE,
                LadderBlock.class);
    }
}