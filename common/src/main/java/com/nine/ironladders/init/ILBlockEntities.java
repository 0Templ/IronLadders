package com.nine.ironladders.init;

import com.nine.ironladders.common.block.entity.MetalLadderBlockEntity;
import com.nine.ironladders.platform.Platform;
import com.nine.ironladders.platform.util.RegistryProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Objects;

public class ILBlockEntities {
	
	public static final RegistryProvider<BlockEntityType<MetalLadderBlockEntity>> METAL_LADDER =
			Platform.REGISTRY.registerBlockEntityType("tier_ladder", MetalLadderBlockEntity::new, () ->
					ILBlocks.AVAILABLE_LADDERS
							.all()
							.stream()
							.filter(Objects::nonNull)
							.toArray(Block[]::new));
	
	public static void init() {
	}
}
