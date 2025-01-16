package com.nine.ironladders.client;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.client.render.MetalLadderBakedModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

@EventBusSubscriber(modid = IronLadders.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
        event.getBakingResult().blockStateModels().entrySet().stream()
                .filter(entry -> entry.getKey().id().getNamespace().equals(IronLadders.MODID))
                .forEach(entry -> event.getBakingResult()
                        .blockStateModels()
                        .put(entry.getKey(), new MetalLadderBakedModel(entry.getValue())));
    }
}
