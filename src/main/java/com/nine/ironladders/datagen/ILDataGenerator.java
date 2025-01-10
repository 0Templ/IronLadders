package com.nine.ironladders.datagen;

import com.nine.ironladders.IronLadders;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = IronLadders.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ILDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        BlockTagsProvider blockTagsProvider = new ILBlockTagProvider(packOutput, lookupProvider, IronLadders.MODID, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ILItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter()));
        generator.addProvider(event.includeServer(), new ILRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), new ILLootGenerator(packOutput));
    }


}
