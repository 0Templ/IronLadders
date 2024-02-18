package com.nine.ironladders;

import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.CreativeTabRegistry;
import com.nine.ironladders.init.ItemRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(IronLadders.MODID)
public class IronLadders {
    public static final String MODID = "ironladders";

    public IronLadders() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ILConfig.COMMON);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        CreativeTabRegistry.TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }
}
