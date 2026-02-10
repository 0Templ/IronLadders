package com.nine.ironladders.datagen;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.datagen.provider.loot.ILLootTableProvider;
import com.nine.ironladders.datagen.provider.model.ILModelProvider;
import com.nine.ironladders.datagen.provider.recipe.ILRecipeProvider;
import com.nine.ironladders.datagen.provider.tag.ILBlockTagProvider;
import com.nine.ironladders.datagen.provider.tag.ILItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ILDataGenerator implements DataGeneratorEntrypoint {
	
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		String platform = System.getProperty("platform");
		switch (platform){
			case "forge":
				generateForgeData(dataGenerator);
				break;
			case "neoforge":
				generateNeoForgeData(dataGenerator);
				break;
			case "fabric":
				generateFabricData(dataGenerator);
				break;
			case "common":
				generateCommonData(dataGenerator);
				break;
			default:
				ILCommon.LOGGER.error("Unknown platform: {}", platform);
		}
	}

	public static void generateCommonData(FabricDataGenerator dataGenerator) {
		FabricDataGenerator.Pack pack = dataGenerator.createPack();
		
		pack.addProvider(ILModelProvider.Common::new);
		pack.addProvider(ILLootTableProvider.Common::new);
		pack.addProvider(ILItemTagProvider.Common::new);
	
	}
	
	public static void generateForgeData(FabricDataGenerator dataGenerator) {
		FabricDataGenerator.Pack pack = dataGenerator.createPack();
		
		pack.addProvider(ILRecipeProvider.Forge::new);
		
		pack.addProvider(ILModelProvider.Forge::new);
		pack.addProvider(ILBlockTagProvider.Forge::new);
		pack.addProvider(ILLootTableProvider.Forge::new);
		pack.addProvider(ILItemTagProvider.Forge::new);
		
	}

	public static void generateNeoForgeData(FabricDataGenerator dataGenerator) {
		FabricDataGenerator.Pack pack = dataGenerator.createPack();

		pack.addProvider(ILRecipeProvider.NeoForge::new);

		pack.addProvider(ILModelProvider.NeoForge::new);
		pack.addProvider(ILBlockTagProvider.NeoForge::new);
		pack.addProvider(ILLootTableProvider.NeoForge::new);
		pack.addProvider(ILItemTagProvider.NeoForge::new);
	}
	
	public static void generateFabricData(FabricDataGenerator dataGenerator) {
		FabricDataGenerator.Pack pack = dataGenerator.createPack();
		
		pack.addProvider(ILRecipeProvider.Fabric::new);

		pack.addProvider(ILModelProvider.Fabric::new);
		pack.addProvider(ILBlockTagProvider.Fabric::new);
		pack.addProvider(ILLootTableProvider.Fabric::new);
		pack.addProvider(ILItemTagProvider.Fabric::new);
		
	}
	
}
