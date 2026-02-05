package com.nine.ironladders.common.block;

import com.google.common.hash.Hashing;
import com.nine.ironladders.common.util.LadderType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Function;

public class VariantLadderBlock extends MetalLadderBlock {
	
	public final Function<Long, Integer> variantProvider;
	
	public final int variants;
	
	public IntegerProperty getVariantProperty(){
		return IntegerProperty.create("variant", 0, variants - 1);
	}
	
	public VariantLadderBlock(Properties properties, LadderType type, int... weights) {
		super(properties, type);
		this.variants = weights.length > 1 ? weights.length - 1 : weights[0];
		this.variantProvider = createProvider(weights);
	}
	
	public static VariantLadderBlock create(Properties properties, LadderType type, int... weights){
		int variants = weights.length > 1 ? weights.length : weights[0];
		if (variants == 2)
			return new TwoVariantLadder(properties, type, weights);
		if (variants == 3)
			return new ThreeVariantLadder(properties, type, weights);
		return new VariantLadderBlock(properties, type, weights);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(getVariantProperty());
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState base = super.getStateForPlacement(ctx);
		if (base == null) return null;
		int v = variantProvider.apply(ctx.getClickedPos().asLong());
		return base.setValue(getVariantProperty(), v);
	}
	
	public static Function<Long, Integer> createProvider(int... weights){
		if (weights.length == 1){
			return clicked -> Math.floorMod(clicked, weights[0]);
		}
		return clicked -> pickWeighted(clicked, weights);
	}
	
	public static int pickWeighted(long seed, int... weights) {
		if (weights.length == 0) return 0;
		
		long total = 0;
		for (int w : weights) {
			total += Math.max(0, w);
		}
		
		if (total == 0) return 0;
		
		long target = Long.remainderUnsigned(
				Hashing.murmur3_128().hashLong(seed).asLong(),
				total
		);
		
		long accumulated = 0;
		for (int i = 0; i < weights.length; i++) {
			accumulated += Math.max(0, weights[i]);
			if (target < accumulated) {
				return i;
			}
		}
		return weights.length - 1;
	}
	
	public static class TwoVariantLadder extends VariantLadderBlock{
		
		private static final int VARIANTS = 2;
		
		private static final IntegerProperty PROPERTY = IntegerProperty.create("variant", 0, VARIANTS - 1);
		
		public TwoVariantLadder(Properties properties, LadderType type) {
			super(properties, type, VARIANTS);
		}
		
		public TwoVariantLadder(Properties properties, LadderType type, int... variants) {
			super(properties, type, variants);
		}
		
		@Override
		public IntegerProperty getVariantProperty() {
			return PROPERTY;
		}
	}
	
	public static class ThreeVariantLadder extends VariantLadderBlock{
		
		private static final int VARIANTS = 3;
		
		private static final IntegerProperty PROPERTY = IntegerProperty.create("variant", 0, VARIANTS - 1);
		
		public ThreeVariantLadder(Properties properties, LadderType type) {
			super(properties, type, 3);
		}
		
		public ThreeVariantLadder(Properties properties, LadderType type, int... variants) {
			super(properties, type, variants);
		}
		
		@Override
		public IntegerProperty getVariantProperty() {
			return PROPERTY;
		}
	}
	
}