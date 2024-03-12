package com.nine.ironladders.common.block;

import com.nine.ironladders.common.utils.*;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.function.ToIntFunction;

public class BaseMetalLadder extends LadderBlock {
    private LadderType type;
  
    public BaseMetalLadder(Settings settings, LadderType type) {
        super(settings.luminance(litBlockEmission(13)));
        this.type = type;
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState())
                .with(LadderProperties.MORPH_TYPE, MorphType.NONE).with(LadderProperties.HIDDEN, false)
                .with(LadderProperties.STATE_IN_CHAIN, LadderPositions.SINGLE)
                .with(LadderProperties.HIDDEN, false)
                .with(LadderProperties.LIGHTED, false).with(LadderProperties.HAS_SIGNAL, false)
                .with(LadderProperties.POWERED, false)
                .with(FACING, Direction.NORTH))
                .with(WATERLOGGED, false));
    }
    @Override
    public void neighborUpdate(BlockState blockState, World level, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!level.isClient && blockState.get(LadderProperties.POWERED)) {
            updatePositionRelations(blockState,level,pos);
            boolean flag = blockState.get(LadderProperties.HAS_SIGNAL);
            boolean flag2 = level.isReceivingRedstonePower(pos);
            boolean flag3 = hasActiveInARow(level, pos);
            if (flag != (flag2 || flag3)) {
                if (flag) {
                    level.scheduleBlockTick(pos, this, 1);
                    updateChain(level,pos);
                } else {
                    level.setBlockState(pos, blockState.cycle(LadderProperties.HAS_SIGNAL), 3);
                    updateChain(level,pos);
                }
            }
        }
    }
    private void updatePositionRelations(BlockState state, World level, BlockPos pos){
        if (!state.get(LadderProperties.POWERED)){
            return;
        }
        boolean hasPoweredAbove = false;
        boolean hasPoweredBelow = false;
        Direction startDirection = state.get(LadderProperties.FACING);
        if (level.getBlockState(pos.up()).getBlock() instanceof BaseMetalLadder && level.getBlockState(pos.up()).get(FACING) == startDirection){
            hasPoweredAbove = level.getBlockState(pos.up()).get(LadderProperties.POWERED);
        }
        if (level.getBlockState(pos.down()).getBlock() instanceof BaseMetalLadder && level.getBlockState(pos.down()).get(FACING) == startDirection){
            hasPoweredBelow = level.getBlockState(pos.down()).get(LadderProperties.POWERED);
        }
        if (hasPoweredAbove && hasPoweredBelow){
            level.setBlockState(pos,state.with(LadderProperties.STATE_IN_CHAIN, LadderPositions.HAS_DOWN_AND_UP_NEIGHBOUR), 3);
            return;
        }
        if (hasPoweredAbove){
            level.setBlockState(pos,state.with(LadderProperties.STATE_IN_CHAIN, LadderPositions.HAS_UP_NEIGHBOUR), 3);
            return;
        }
        if (hasPoweredBelow){
            level.setBlockState(pos,state.with(LadderProperties.STATE_IN_CHAIN, LadderPositions.HAS_DOWN_NEIGHBOUR), 3);
            return;
        }
        level.setBlockState(pos,state.with(LadderProperties.STATE_IN_CHAIN, LadderPositions.SINGLE), 3);
    }
    public void updateChain(World level, BlockPos pos) {
        boolean canGoUp = true;
        boolean canGoDown = true;
        Direction startFacingDirection = level.getBlockState(pos).get(FACING);
        for (int height = 1; height < 256; height++) {
            BlockPos posAbove = pos.up(height);
            BlockPos posBelow = pos.down(height);
            Block blockAbove = level.getBlockState(posAbove).getBlock();
            Block blockBelow = level.getBlockState(posBelow).getBlock();
            BlockState stateAbove = level.getBlockState(posAbove);
            BlockState stateBelow = level.getBlockState(posBelow);
            if (canGoUp) {
                if (blockAbove instanceof BaseMetalLadder) {
                    Direction currentUpFacingDirection = stateAbove.get(FACING);
                    canGoUp = startFacingDirection == currentUpFacingDirection && stateAbove.get(LadderProperties.POWERED);
                } else {
                    canGoUp = false;
                }
            }
            if (canGoDown) {
                if (blockBelow instanceof BaseMetalLadder) {
                    Direction currentDownFacingDirection = stateBelow.get(FACING);
                    canGoDown = startFacingDirection == currentDownFacingDirection && stateBelow.get(LadderProperties.POWERED);
                } else {
                    canGoDown = false;
                }
            }
            if (canGoDown || canGoUp) {
                if (canGoUp) {
                    if ((hasActiveInARow(level, posAbove) || level.isReceivingRedstonePower(posAbove)) != stateAbove.get(LadderProperties.HAS_SIGNAL)) {
                        stateAbove = BlockStateUtils.getStateWithSyncedProps(stateAbove, stateAbove);
                        level.setBlockState(posAbove, stateAbove.cycle(LadderProperties.HAS_SIGNAL), 3);
                    }
                }
                if (canGoDown) {
                    if ((hasActiveInARow(level, posBelow) || level.isReceivingRedstonePower(posBelow)) != stateBelow.get(LadderProperties.HAS_SIGNAL)) {
                        stateBelow = BlockStateUtils.getStateWithSyncedProps(stateBelow, stateBelow);
                        level.setBlockState(posBelow, stateBelow.cycle(LadderProperties.HAS_SIGNAL), 3);
                    }
                }
            } else {
                break;
            }
        }
    }
    private boolean hasActiveInARow(World level , BlockPos pos) {
        int height = 1;
        boolean canGoDown = true;
        boolean canGoUp = true;
        Direction startFacingDirection = level.getBlockState(pos).get(FACING);
        while (height<256){
            Block blockBelow = level.getBlockState(pos.down(height)).getBlock();
            Block blockAbove = level.getBlockState(pos.up(height)).getBlock();
            BlockState stateAbove = level.getBlockState(pos.up(height));
            BlockState stateBelow = level.getBlockState(pos.down(height));

            if(canGoUp) {
                if (blockAbove instanceof BaseMetalLadder) {
                    Direction currentUpFacingDirection = stateAbove.get(FACING);
                    canGoUp =  startFacingDirection == currentUpFacingDirection && stateAbove.get(LadderProperties.POWERED);
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof BaseMetalLadder){
                    Direction currentDownFacingDirection = stateBelow.get(FACING);
                    canGoDown =  startFacingDirection == currentDownFacingDirection && stateBelow.get(LadderProperties.POWERED);
                }
                else {
                    canGoDown = false;
                }
            }
            if (canGoUp || canGoDown){
                if (canGoUp){
                    if (level.isReceivingRedstonePower(pos.up(height))){
                        return true;
                    }
                }
                if (canGoDown){
                    if (level.isReceivingRedstonePower(pos.down(height))){
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
    public void scheduledTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        if (state.get(LadderProperties.POWERED)) {
            if (state.get(LadderProperties.HAS_SIGNAL) && !level.isReceivingRedstonePower(pos) && !hasActiveInARow(level, pos)) {
                level.setBlockState(pos, state.cycle(LadderProperties.HAS_SIGNAL), 3);
            }
        }
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LadderProperties.POWERED);
        builder.add(LadderProperties.STATE_IN_CHAIN);
        builder.add(LadderProperties.LIGHTED);
        builder.add(LadderProperties.HIDDEN);
        builder.add(LadderProperties.MORPH_TYPE);
        builder.add(LadderProperties.HAS_SIGNAL);
    }
    public LadderType getType(){
        return this.type;
    }
    public int getSpeedMultiplier() {
        return 0;
    }
    public boolean isMorphed(BlockState blockState){
        return blockState.get(LadderProperties.MORPH_TYPE) != MorphType.NONE;
    }
    public boolean isVines(BlockState blockState){
        return blockState.get(LadderProperties.MORPH_TYPE) == MorphType.VINES;
    }
    public boolean isPowered(BlockState blockState){
        return blockState.get(LadderProperties.POWERED);
    }
    private static ToIntFunction<BlockState> litBlockEmission(int litLevel) {
        return (blockState) -> (Boolean)blockState.get(LadderProperties.LIGHTED) ? litLevel : 0;
    }
    public boolean isPoweredAndHasSignal(BlockState blockState){
        if (blockState.getBlock() instanceof BaseMetalLadder){
            return isPowered(blockState) && blockState.get(LadderProperties.HAS_SIGNAL);
        }
        return false;
    }
}
