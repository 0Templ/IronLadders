package com.nine.ironladders.common.block;

import com.nine.ironladders.common.util.LadderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CryingObsidianLadderBlock extends VariantLadderBlock.ThreeVariantLadder {
	
	public CryingObsidianLadderBlock(Properties properties, LadderType type) {
		super(properties, type);
	}
	
	@Override
	public int getDefaultLight(){
		return 10;
	}
	
	@Override
	protected void doAnimateTick(BlockState state, Level level, BlockPos pos, RandomSource random){
		if (random.nextInt(15) == 0) {
			spawnTearParticles(state, level, pos, random);
		}
	}
	
	public static void spawnTearParticles(BlockState state, Level level, BlockPos pos, RandomSource random) {
		Direction direction = Direction.getRandom(random);
		VoxelShape shape = state.getShape(level, pos);
		BlockState blockState = level.getBlockState(pos);
		if (direction != Direction.UP) {
			if (!state.canOcclude() || !blockState.isFaceSturdy(level, pos, direction.getOpposite()))
				shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
					double x = pos.getX() + random.nextDouble() * (maxX - minX) + minX;
					double y = pos.getY() + random.nextDouble() * (maxY - minY) + minY;
					double z = pos.getZ() + random.nextDouble() * (maxZ - minZ) + minZ;
					level.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, x, y, z, 0.0F, 0.0F, 0.0F);
				});
		}
	}
	
}
