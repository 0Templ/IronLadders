package com.nine.ironladders.datagen.provider.model;


import com.google.gson.JsonElement;
import com.nine.ironladders.ILClient;
import com.nine.ironladders.ILCommon;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.datagen.provider.model.util.ILBlockModels;
import com.nine.ironladders.datagen.provider.model.util.ILItemModels;
import com.nine.ironladders.datagen.provider.model.util.ILBlockModelGenerator;
import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.platform.util.LoaderTarget;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ILModelProvider extends FabricModelProvider {
	
	protected final LoaderTarget[] targets;
	private final FabricDataOutput dataOutput;
	private final Map<ResourceLocation, Supplier<JsonElement>> customStatesMap = new HashMap<>();

	public ILModelProvider(FabricDataOutput output, LoaderTarget... targets) {
		super(output);
		this.dataOutput = output;
		this.targets = targets;
	}

	public BiConsumer<ResourceLocation, Supplier<JsonElement>> createCustomStatesOutput() {
		return customStatesMap::put;
	}
	
	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		ILItemModels.generateModels(itemModelGenerator);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		BiConsumer<ResourceLocation, Supplier<JsonElement>> customStatesOutput = createCustomStatesOutput();
		ILBlockModelGenerator generator = new ILBlockModelGenerator(
				blockStateModelGenerator.blockStateOutput,
				customStatesOutput,
				blockStateModelGenerator.modelOutput
		);
		for (var data : ILBlockModels.SOURCE.entrySet()){
			var block = data.getKey();
			if (!ILBlocks.AVAILABLE_LADDERS.get(targets).contains(block)) continue;
			var modelBlock = ILBlockModels.SOURCE.get(block);
			generator.generate((MetalLadderBlock) block, (MetalLadderBlock) modelBlock);
		}
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cachedOutput) {
		return super.run(cachedOutput).thenCompose(v -> saveCustomStates(cachedOutput));
	}

	private CompletableFuture<?> saveCustomStates(CachedOutput cachedOutput) {
		CompletableFuture<?>[] futures = customStatesMap.entrySet().stream()
				.map(entry -> {
					ResourceLocation location = entry.getKey();
					JsonElement json = entry.getValue().get();
					Path outputPath = dataOutput.getOutputFolder()
							.resolve("assets")
							.resolve(ILCommon.MODID)
							.resolve(ILClient.ALT_BLOCK_STATES_DIR)
							.resolve(location.getPath() + ".json");
					return DataProvider.saveStable(cachedOutput, json, outputPath);
				})
				.toArray(CompletableFuture[]::new);

		return CompletableFuture.allOf(futures);
	}

	
	public static class Common extends ILModelProvider {
		
		public Common(FabricDataOutput output) {
			super(output, LoaderTarget.COMMON);
		}
		
	}
	
	public static class Fabric extends ILModelProvider {
		
		public Fabric(FabricDataOutput output) {
			super(output, LoaderTarget.FABRIC);
		}
		
	}
	
	public static class Forge extends ILModelProvider {
		
		public Forge(FabricDataOutput output) {
			super(output, LoaderTarget.FORGE);
		}
		
	}
	
	
	
}
