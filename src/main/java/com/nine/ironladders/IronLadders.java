package com.nine.ironladders;

import com.nine.ironladders.compat.top.TheOneProbeSetup;
import com.nine.ironladders.init.BlockEntityRegistry;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.CreativeTabRegistry;
import com.nine.ironladders.init.ItemRegistry;
import com.nine.ironladders.network.NetworkHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mod(IronLadders.MODID)
public class IronLadders {
    public static final String MODID = "ironladders";

    public IronLadders() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ILConfig.COMMON);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);
        CreativeTabRegistry.TABS.register(modEventBus);
        modEventBus.addListener(this::imeRegistry);
        NetworkHandler.register();

        MinecraftForge.EVENT_BUS.register(this);

    }

    public static final Logger LOGGER = LoggerFactory.getLogger(IronLadders.class);

    public void imeRegistry(InterModEnqueueEvent evt) {
        if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TheOneProbeSetup::new);
        }
    }
}
