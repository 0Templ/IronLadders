package com.nine.ironladders.datagen.provider.loot;

import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.platform.util.LoaderTarget;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class ILLootTableProvider extends FabricBlockLootTableProvider {
	
	protected final LoaderTarget[] targets;
	
	public ILLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup, LoaderTarget... targets) {
		super(output, registryLookup);
		this.targets = targets;
	}

	private static final Set<Block> NO_DROP = Set.of();
	
	@Override
	public void generate() {
		for (var ladder : ILBlocks.AVAILABLE_LADDERS.get(targets)){
			if (!NO_DROP.contains(ladder))
				dropSelf(ladder);
		}
	}
	
	public static class Common extends ILLootTableProvider {
		
		public Common(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(output, registryLookup, LoaderTarget.COMMON);
		}
	}
	
	public static class Fabric extends ILLootTableProvider {
		
		public Fabric(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(output, registryLookup, LoaderTarget.FABRIC, LoaderTarget.COMMON);
		}
	}
	
	public static class Forge extends ILLootTableProvider {
		
		public Forge(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(output, registryLookup, LoaderTarget.FORGE, LoaderTarget.COMMON);
		}
	}

	public static class NeoForge extends ILLootTableProvider {

		public NeoForge(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
			super(output, registryLookup, LoaderTarget.NEOFORGE, LoaderTarget.COMMON);
		}
	}

	
}
