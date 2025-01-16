package com.nine.ironladders.datagen;

import com.nine.ironladders.IronLadders;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = IronLadders.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ILDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        BlockTagsProvider blockTagsProvider = new ILBlockTagProvider(packOutput, lookupProvider, IronLadders.MODID);
        generator.addProvider(true, blockTagsProvider);
        generator.addProvider(true, new ILItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter()));
        event.createProvider(ILRecipeProvider.Runner::new);
        event.createProvider(ILLootGenerator::new);
        generator.addProvider(true, new ILDataMapsProvider(packOutput, lookupProvider));
    }
}
