package com.nine.ironladders.common.block;

import com.nine.ironladders.common.utils.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;

public class BaseMetalLadder extends LadderBlock {
    private LadderType type;
  
    public BaseMetalLadder(BlockBehaviour.Properties settings, LadderType type) {
        super(settings);
        this.type = type;
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any())
                .setValue(LadderProperties.MORPH_TYPE, MorphType.NONE)
                .setValue(LadderProperties.STATE_IN_CHAIN, LadderPositions.SINGLE)
                .setValue(LadderProperties.HIDDEN, false)
                .setValue(LadderProperties.LIGHTED, false)
                .setValue(LadderProperties.HAS_SIGNAL, false)
                .setValue(LadderProperties.POWERED, false)
                .setValue(FACING, Direction.NORTH))
                .setValue(WATERLOGGED, false));
    }
    @Override
    public void neighborChanged(@NotNull BlockState blockState, Level level, BlockPos pos, Block block, BlockPos pos2, boolean p_49382_) {
        if (!level.isClientSide && blockState.getValue(LadderProperties.POWERED)) {
            updatePositionRelations(blockState,level,pos);
            boolean flag = blockState.getValue(LadderProperties.HAS_SIGNAL);
            boolean flag2 = level.hasNeighborSignal(pos);
            boolean flag3 = hasActiveInARow(level, pos);
            if (flag != (flag2 || flag3)) {
                if (flag) {
                    level.scheduleTick(pos, this, 1);
                    updateChain(level,pos);
                } else {
                    level.setBlock(pos, blockState.cycle(LadderProperties.HAS_SIGNAL), 3);
                    updateChain(level,pos);
                }
            }
        }
    }
    private void updatePositionRelations(BlockState state, Level level, BlockPos pos){
        if (!state.getValue(LadderProperties.POWERED)){
            return;
        }
        boolean hasPoweredAbove = false;
        boolean hasPoweredBelow = false;
        Direction startDirection = state.getValue(LadderProperties.FACING);
        if (level.getBlockState(pos.above()).getBlock() instanceof BaseMetalLadder && level.getBlockState(pos.above()).getValue(FACING) == startDirection){
            hasPoweredAbove = level.getBlockState(pos.above()).getValue(LadderProperties.POWERED);
        }
        if (level.getBlockState(pos.below()).getBlock() instanceof BaseMetalLadder && level.getBlockState(pos.below()).getValue(FACING) == startDirection){
            hasPoweredBelow = level.getBlockState(pos.below()).getValue(LadderProperties.POWERED);
        }
        if (hasPoweredAbove && hasPoweredBelow){
            level.setBlock(pos,state.setValue(LadderProperties.STATE_IN_CHAIN, LadderPositions.HAS_DOWN_AND_UP_NEIGHBOUR), 3);
            return;
        }
        if (hasPoweredAbove){
            level.setBlock(pos,state.setValue(LadderProperties.STATE_IN_CHAIN, LadderPositions.HAS_UP_NEIGHBOUR), 3);
            return;
        }
        if (hasPoweredBelow){
            level.setBlock(pos,state.setValue(LadderProperties.STATE_IN_CHAIN, LadderPositions.HAS_DOWN_NEIGHBOUR), 3);
            return;
        }
        level.setBlock(pos,state.setValue(LadderProperties.STATE_IN_CHAIN, LadderPositions.SINGLE), 3);
    }
    public void updateChain(Level level, BlockPos pos) {
        boolean canGoUp = true;
        boolean canGoDown = true;
        Direction startFacingDirection = level.getBlockState(pos).getValue(LadderProperties.FACING);
        for (int height = 1; height < 256; height++) {
            BlockPos posAbove = pos.above(height);
            BlockPos posBelow = pos.below(height);
            Block blockAbove = level.getBlockState(posAbove).getBlock();
            Block blockBelow = level.getBlockState(posBelow).getBlock();
            BlockState stateAbove = level.getBlockState(posAbove);
            BlockState stateBelow = level.getBlockState(posBelow);
            if(canGoUp) {
                if (blockAbove instanceof BaseMetalLadder) {
                    Direction currentUpFacingDirection = stateAbove.getValue(LadderProperties.FACING);
                    canGoUp = startFacingDirection == currentUpFacingDirection && stateAbove.getValue(LadderProperties.POWERED);
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof BaseMetalLadder){
                    Direction currentDownFacingDirection = stateBelow.getValue(LadderProperties.FACING);
                    canGoDown = startFacingDirection == currentDownFacingDirection && stateBelow.getValue(LadderProperties.POWERED);
                }
                else {
                    canGoDown = false;
                }
            }
            if (canGoDown || canGoUp){
                if (canGoUp) {
                    if ((hasActiveInARow(level, posAbove) || level.hasNeighborSignal(posAbove)) != stateAbove.getValue(LadderProperties.HAS_SIGNAL)) {
                        stateAbove = BlockStateUtils.getStateWithSyncedProps(stateAbove, stateAbove);
                        level.setBlock(posAbove, stateAbove.cycle(LadderProperties.HAS_SIGNAL), 3);
                    }
                }
                if (canGoDown) {
                    if ((hasActiveInARow(level,posBelow) || level.hasNeighborSignal(posBelow)) != stateBelow.getValue(LadderProperties.HAS_SIGNAL)){
                        stateBelow = BlockStateUtils.getStateWithSyncedProps(stateBelow, stateBelow);
                        level.setBlock(posBelow, stateBelow.cycle(LadderProperties.HAS_SIGNAL), 3);
                    }
                }
            }
            else {
                break;
            }
        }
    }
    private boolean hasActiveInARow(Level level , BlockPos pos) {
        int height = 1;
        boolean canGoDown = true;
        boolean canGoUp = true;
        Direction startFacingDirection = level.getBlockState(pos).getValue(FACING);
        while (height<256){
            Block blockBelow = level.getBlockState(pos.below(height)).getBlock();
            Block blockAbove = level.getBlockState(pos.above(height)).getBlock();
            BlockState stateAbove = level.getBlockState(pos.above(height));
            BlockState stateBelow = level.getBlockState(pos.below(height));

            if(canGoUp) {
                if (blockAbove instanceof BaseMetalLadder) {
                    Direction currentUpFacingDirection = stateAbove.getValue(FACING);
                    canGoUp =  startFacingDirection == currentUpFacingDirection && stateAbove.getValue(LadderProperties.POWERED);
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof BaseMetalLadder){
                    Direction currentDownFacingDirection = stateBelow.getValue(FACING);
                    canGoDown =  startFacingDirection == currentDownFacingDirection && stateBelow.getValue(LadderProperties.POWERED);
                }
                else {
                    canGoDown = false;
                }
            }
            if (canGoUp || canGoDown){
                if (canGoUp){
                    if (level.hasNeighborSignal(pos.above(height))){
                        return true;
                    }
                }
                if (canGoDown){
                    if (level.hasNeighborSignal(pos.below(height))){
                        return true;
                    }
                }
            }
            else {
                return false;
            }
            height++;
        }
        return false;
    }
    @Override
    public void tick(BlockState p_221937_, ServerLevel level, BlockPos pos, RandomSource p_221940_) {
        if (p_221937_.getValue(LadderProperties.POWERED)) {
            if (p_221937_.getValue(LadderProperties.HAS_SIGNAL) && !level.hasNeighborSignal(pos) && !hasActiveInARow(level, pos)) {
                level.setBlock(pos, p_221937_.cycle(LadderProperties.HAS_SIGNAL), 3);
            }
        }
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LadderProperties.POWERED);
        builder.add(LadderProperties.LIGHTED);
        builder.add(LadderProperties.HIDDEN);
        builder.add(LadderProperties.MORPH_TYPE);
        builder.add(LadderProperties.STATE_IN_CHAIN);
        builder.add(LadderProperties.HAS_SIGNAL);
    }
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState stateBefore, boolean bool) {
        if (!stateBefore.is(state.getBlock())) {
            level.updateNeighborsAt(pos,this);
        }
    }

    public LadderType getType(){
        return this.type;
    }
    public int getSpeedMultiplier() {
        return 0;
    }
    public boolean isMorphed(BlockState blockState){
        return blockState.getValue(LadderProperties.MORPH_TYPE) != MorphType.NONE;
    }
    public boolean isVines(BlockState blockState){
        return blockState.getValue(LadderProperties.MORPH_TYPE) == MorphType.VINES;
    }
    public boolean isPowered(BlockState blockState){
        return blockState.getValue(LadderProperties.POWERED);
    }
    public boolean isPoweredAndHasSignal(BlockState blockState){
        if (blockState.getBlock() instanceof BaseMetalLadder){
            return isPowered(blockState) && blockState.getValue(LadderProperties.HAS_SIGNAL);
        }
        return false;
    }
}
