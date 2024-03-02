package com.nine.ironladders.common.block;

import com.nine.ironladders.common.BlockStateUtils;
import com.nine.ironladders.common.item.MorphType;
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
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final BooleanProperty HAS_SIGNAL = BooleanProperty.of("has_signal");
    public static final BooleanProperty LIGHTED = BooleanProperty.of("lighted");
    public static final BooleanProperty HIDDEN = BooleanProperty.of("hidden");
    public static final EnumProperty<MorphType> MORPH_TYPE = EnumProperty.of("morph", MorphType.class);

    public BaseMetalLadder(Settings settings, LadderType type) {
        super(settings.luminance(litBlockEmission(13)));
        this.type = type;
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(MORPH_TYPE, MorphType.NONE).with(HIDDEN, false).with(LIGHTED, false).with(HAS_SIGNAL, false).with(POWERED, false).with(FACING, Direction.NORTH)).with(WATERLOGGED, false));
    }
    @Override
    public void neighborUpdate(BlockState blockState, World level, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!level.isClient && blockState.get(POWERED)) {
            boolean flag = blockState.get(HAS_SIGNAL);
            boolean flag2 = level.isReceivingRedstonePower(pos);
            boolean flag3 = hasActiveInARow(level, pos);
            if (flag != (flag2 || flag3)) {
                if (flag) {
                    level.scheduleBlockTick(pos, this, 4);
                    updateChain(level,pos);
                } else {
                    level.setBlockState(pos, blockState.cycle(HAS_SIGNAL), 2);
                    updateChain(level,pos);
                }
            }
        }
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
                    canGoUp = startFacingDirection == currentUpFacingDirection && stateAbove.get(POWERED);
                } else {
                    canGoUp = false;
                }
            }
            if (canGoDown) {
                if (blockBelow instanceof BaseMetalLadder) {
                    Direction currentDownFacingDirection = stateBelow.get(FACING);
                    canGoDown = startFacingDirection == currentDownFacingDirection && stateBelow.get(POWERED);
                } else {
                    canGoDown = false;
                }
            }
            if (canGoDown || canGoUp) {
                if (canGoUp) {
                    if ((hasActiveInARow(level, posAbove) || level.isReceivingRedstonePower(posAbove)) != stateAbove.get(HAS_SIGNAL)) {
                        stateAbove = BlockStateUtils.getStateWithSyncedProps(stateAbove, stateAbove);
                        level.setBlockState(posAbove, stateAbove.cycle(HAS_SIGNAL), 2);
                    }
                }
                if (canGoDown) {
                    if ((hasActiveInARow(level, posBelow) || level.isReceivingRedstonePower(posBelow)) != stateBelow.get(HAS_SIGNAL)) {
                        stateBelow = BlockStateUtils.getStateWithSyncedProps(stateBelow, stateBelow);
                        level.setBlockState(posBelow, stateBelow.cycle(HAS_SIGNAL), 2);
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
                    canGoUp =  startFacingDirection == currentUpFacingDirection && stateAbove.get(POWERED);
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof BaseMetalLadder){
                    Direction currentDownFacingDirection = stateBelow.get(FACING);
                    canGoDown =  startFacingDirection == currentDownFacingDirection && stateBelow.get(POWERED);
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
        if (state.get(POWERED)) {
            if (state.get(HAS_SIGNAL) && !level.isReceivingRedstonePower(pos) && !hasActiveInARow(level, pos)) {
                level.setBlockState(pos, state.cycle(HAS_SIGNAL), 2);
            }
        }
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POWERED);
        builder.add(LIGHTED);
        builder.add(HIDDEN);
        builder.add(MORPH_TYPE);
        builder.add(HAS_SIGNAL);
    }
    public LadderType getType(){
        return this.type;
    }
    public int getSpeedMultiplier() {
        return 0;
    }
    public boolean isPowered(BlockState blockState){
        return blockState.get(POWERED);
    }
    private static ToIntFunction<BlockState> litBlockEmission(int litLevel) {
        return (blockState) -> (Boolean)blockState.get(LIGHTED) ? litLevel : 0;
    }
    public boolean isPoweredAndHasSignal(BlockState blockState){
        if (blockState.getBlock() instanceof BaseMetalLadder){
            return isPowered(blockState) && blockState.get(HAS_SIGNAL);
        }
        return false;
    }
}
