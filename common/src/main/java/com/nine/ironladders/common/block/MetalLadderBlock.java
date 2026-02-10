package com.nine.ironladders.common.block;

import com.nine.ironladders.common.block.entity.MetalLadderBlockEntity;
import com.nine.ironladders.common.util.LadderType;
import com.nine.ironladders.init.ILBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MetalLadderBlock extends LadderBlock implements EntityBlock {
	
	private final LadderType type;
	
	public static final int LIGHTED_MODIFIER_LEVEL = 13;
	
	public static final BooleanProperty HAS_SENSOR = BooleanProperty.create("has_sensor");
	public static final BooleanProperty SENSOR_ACTIVE = BooleanProperty.create("sensor_active");
	public static final BooleanProperty LIGHTED = BooleanProperty.create("lighted");
	public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("lit", 0, 15);
	
	public MetalLadderBlock() {
		this(Properties.of(), LadderType.NONE);
	}
	
	public MetalLadderBlock(Properties properties, LadderType type) {
		super(properties
				.requiresCorrectToolForDrops()
				.forceSolidOff()
				.noOcclusion()
				.sound(SoundType.METAL)
				.pushReaction(PushReaction.DESTROY)
				.lightLevel(b -> b.getValue(LIGHT_LEVEL))
		);
		this.type = type;
		registerDefaultState(
				this.stateDefinition.any()
						.setValue(FACING, Direction.NORTH)
						.setValue(WATERLOGGED, false)
						.setValue(HAS_SENSOR, false)
						.setValue(SENSOR_ACTIVE, false)
						.setValue(LIGHTED, false)
						.setValue(LIGHT_LEVEL, getDefaultLight())
		);
	}
	
	public int getDefaultLight(){
		return 0;
	}
	
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof MetalLadderBlockEntity be
				&& be.hasValidMorphState()
				&& be.morphState().getBlock() instanceof MetalLadderBlock tierLadder
		){
			tierLadder.doAnimateTick(be.morphState(), level, pos, random);
			return;
		}
		doAnimateTick(state, level, pos, random);
	}
	
	protected void doAnimateTick(BlockState state, Level level, BlockPos pos, RandomSource random){
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	public LadderType getType(){
		return type;
	}
	
	public double getSpeedMultiplier() {
		return type.getSpeedMultiplier();
	}
	
	@Override
	public boolean isSignalSource(BlockState state) {
		return state.getValue(HAS_SENSOR);
	}
	
	// Implementing output signal as regular redstone power instead of a comparator signal.
	// Ladders are awkward to connect to comparators in-game
	@Override
	public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		if (!state.getValue(HAS_SENSOR) || !state.getValue(SENSOR_ACTIVE)) {
			return 0;
		}
		Direction attachedSide = state.getValue(FACING);
		if (direction != attachedSide) return 0;
		int ret = 15;
		if (level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be){
			ret = be.redstoneSignal();
		}
		return ret;
	}
	
	@Override
	public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return getSignal(state, level, pos, direction);
	}

	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (!level.isClientSide) {
			if (state.getValue(HAS_SENSOR) && !state.getValue(SENSOR_ACTIVE)) {
				checkPressed(level, pos, state);
			}
		}
	}
	
	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!oldState.is(state.getBlock())) {
			this.checkPressed(level, pos, state);
		}
	}
	
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (state.getValue(HAS_SENSOR) && state.getValue(SENSOR_ACTIVE)) {
			this.checkPressed(level, pos, state);
		}
	}
	
	
	private void checkPressed(Level level, BlockPos pos, BlockState state) {
		if (!state.getValue(HAS_SENSOR)) return;
		
		boolean active = state.getValue(SENSOR_ACTIVE);
		List<LivingEntity> entities = getEntitiesInside(level, pos);
		
		int signal = 0;
		if (!entities.isEmpty()) {
			level.scheduleTick(pos, this, 20);
			for (var living : entities) {
				signal = Math.max(signal, getSignalFromEntity(living));
				if (signal == 15) break;
			}
		}
		boolean shouldUpdateNearby = false;
		if (level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be) {
			if (be.redstoneSignal() != signal) {
				be.setRedstoneSignal(signal);
				shouldUpdateNearby = true;
			}
		}
		
		boolean shouldBeActive = signal > 0;
		if (shouldBeActive != active) {
			BlockState newState = state.setValue(SENSOR_ACTIVE, shouldBeActive);
			level.setBlock(pos, newState, 3);
			level.setBlocksDirty(pos, state, newState);
			shouldUpdateNearby = true;
		}
		if (shouldUpdateNearby){
			BlockPos attachedPos = getAttachedPos(pos, state);
			level.updateNeighbourForOutputSignal(pos, this);
			level.updateNeighborsAt(pos, this);
			level.updateNeighborsAt(attachedPos, this);
			level.updateNeighborsAt(pos.below(), this);
			
		}
	}
	
	private int getSignalFromEntity(LivingEntity living){
		if (living instanceof Player){
			return 15;
		}
		if (living instanceof Enemy){
			return 14;
		}
		if (living instanceof Mob){
			return 13;
		}
		return 12;
	}
	
	private static boolean isFeetInsideBlock(Entity entity, BlockPos pos) {
		double minY = entity.getBoundingBox().minY;
		return minY >= pos.getY() && minY < (pos.getY() + 1);
	}
	
	private static BlockPos getAttachedPos(BlockPos pos, BlockState state) {
		return pos.relative(state.getValue(FACING).getOpposite());
	}

	private List<LivingEntity> getEntitiesInside(Level level, BlockPos pos){
		return level.getEntitiesOfClass(
				LivingEntity.class,
				this.getSearchBB(pos),
				EntitySelector.NO_SPECTATORS.and(
						entity -> ((LivingEntity)entity).onClimbable()
								&& isFeetInsideBlock(entity, pos)));
	}
	
	private AABB getSearchBB(BlockPos pos) {
		return new AABB(
				pos.getX(),
				pos.getY(),
				pos.getZ(),
				(pos.getX() + 1),
				(pos.getY() + 1),
				(pos.getZ() + 1));
	}

	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HAS_SENSOR, SENSOR_ACTIVE, LIGHTED, LIGHT_LEVEL);
	}
	
	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide) {
			level.getBlockEntity(pos, ILBlockEntities.METAL_LADDER.get()).ifPresent(be -> {
				for (var stack : be.getStacksToDrop()){
					popResource(level, pos, stack);
				}
			});
		}
		return super.playerWillDestroy(level, pos, state, player);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new MetalLadderBlockEntity(pos, state);
	}

}
