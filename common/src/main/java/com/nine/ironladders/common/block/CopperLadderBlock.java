package com.nine.ironladders.common.block;

import com.nine.ironladders.common.util.LadderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public class CopperLadderBlock extends VariantLadderBlock.ThreeVariantLadder implements WeatheringCopper {
	
	private final WeatheringCopper.WeatherState weatherState;
	
	public CopperLadderBlock(
			Properties properties,
			WeatheringCopper.WeatherState weatherState
	) {
		super(properties, LadderType.COPPER);
		this.weatherState = weatherState;
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		this.onRandomTick(state, level, pos, random);
	}
	
	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return WeatheringCopper.getNext(state.getBlock()).isPresent();
	}
	
	@Override
	public WeatherState getAge() {
		return this.weatherState;
	}

}
