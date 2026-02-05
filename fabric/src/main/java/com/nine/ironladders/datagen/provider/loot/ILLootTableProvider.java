package com.nine.ironladders.datagen.provider.loot;

import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.platform.util.LoaderTarget;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public abstract class ILLootTableProvider extends FabricBlockLootTableProvider {
	
	protected final LoaderTarget[] targets;
	
	public ILLootTableProvider(FabricDataOutput dataOutput, LoaderTarget... targets) {
		super(dataOutput);
		this.targets = targets;
	}

	private static Set<Block> NO_DROP = Set.of();
	
	@Override
	public void generate() {
		for (var ladder : ILBlocks.AVAILABLE_LADDERS.get(targets)){
			if (!NO_DROP.contains(ladder))
				dropSelf(ladder);
		}
	}
	
	public static class Common extends ILLootTableProvider {
		
		public Common(FabricDataOutput output) {
			super(output, LoaderTarget.COMMON);
		}
	}
	
	public static class Fabric extends ILLootTableProvider {
		
		public Fabric(FabricDataOutput output) {
			super(output, LoaderTarget.FABRIC);
		}
	}
	
	public static class Forge extends ILLootTableProvider {
		
		public Forge(FabricDataOutput output) {
			super(output, LoaderTarget.FORGE);
		}
	}

	
}
