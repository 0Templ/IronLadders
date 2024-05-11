package com.nine.ironladders.client;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.PropertiesRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = IronLadders.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void registerProperties(FMLClientSetupEvent event) {
        PropertiesRegistry.register();
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, getter, pos, tintIndex) -> isVines(state) ?
                        ((getter != null && pos != null) ?
                                BiomeColors.getAverageFoliageColor(getter, pos) :  FoliageColor.getDefaultColor()) :
                        getter == null ?
                        BlockRegistry.getMapColor(state.getBlock()) : getter.getBlockState(pos).getMapColor(getter,pos).col,
                BlockRegistry.COPPER_LADDER.get(),
                BlockRegistry.EXPOSED_COPPER_LADDER.get(),
                BlockRegistry.WEATHERED_COPPER_LADDER.get(),
                BlockRegistry.OXIDIZED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get(),
                BlockRegistry.IRON_LADDER.get(),
                BlockRegistry.GOLD_LADDER.get(),
                BlockRegistry.DIAMOND_LADDER.get(),
                BlockRegistry.NETHERITE_LADDER.get());
    }

    private static boolean isVines(BlockState state){
        if (state.getBlock() instanceof BaseMetalLadder baseMetalLadder){
            return baseMetalLadder.isVines(state);
        }
        return false;
    }

}
