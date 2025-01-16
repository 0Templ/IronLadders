package com.nine.ironladders;

import com.mojang.logging.LogUtils;
import com.nine.ironladders.common.component.CustomComponents;
import com.nine.ironladders.init.BlockEntityRegistry;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.CreativeTabRegistry;
import com.nine.ironladders.init.ItemRegistry;
import com.nine.ironladders.network.MorphPacket;
import com.nine.ironladders.network.SyncPlayerInputData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(IronLadders.MODID)
public class IronLadders {

    public static final String MODID = "ironladders";

    public static final Logger LOGGER = LogUtils.getLogger();

    public IronLadders(IEventBus modEventBus, Dist dist) {
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, ILConfig.COMMON);
        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);
        CreativeTabRegistry.TABS.register(modEventBus);
        CustomComponents.COMPONENTS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::imeRegistry);
        modEventBus.addListener(this::setupPackets);
    }

    public void setupPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0");
        registrar.playToServer(MorphPacket.ID, MorphPacket.PACKET_CODEC, MorphPacket::onMessage);
        registrar.playToServer(SyncPlayerInputData.ID, SyncPlayerInputData.PACKET_CODEC, SyncPlayerInputData::onMessage);
    }

    public void imeRegistry(InterModEnqueueEvent evt) {
    /*    if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TheOneProbeSetup::new);
        }*/
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

}
