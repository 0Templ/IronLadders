package com.nine.ironladders.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PositionUtils {
	
	private PositionUtils(){
	
	}
	
	public static <T> void walkAndApply(
			BlockPos startPos,
			int maxDistance,
			Function<BlockPos, T> validator,
			BiConsumer<BlockPos, T> action,
			Direction... directions
	) {
		
		T startObject = validator.apply(startPos);
		if (startObject == null) return;
		action.accept(startPos, startObject);

		for (Direction dir : directions) {
			for (int dist = 1; dist < maxDistance; dist++) {
				BlockPos currentPos = startPos.relative(dir, dist);
				T target = validator.apply(currentPos);
				if (target == null) break;
				
				action.accept(currentPos, target);
			}
		}
	}
	
}
