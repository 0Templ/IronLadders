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
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        BlockTagsProvider blockTagsProvider = new ILBlockTagProvider(packOutput, lookupProvider, IronLadders.MODID, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTagsProvider);

        generator.addProvider(event.includeServer(), new ILItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter()));
        generator.addProvider(event.includeServer(), new ILRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ILLootGenerator(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ILDataMapsPapProvider(packOutput, lookupProvider));
    }
}
