package com.nine.ironladders.client;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.init.PropertiesRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = IronLadders.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void registerProperties(FMLClientSetupEvent event) {
        PropertiesRegistry.register();
    }
}
