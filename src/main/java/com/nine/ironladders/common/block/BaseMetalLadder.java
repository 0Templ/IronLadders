package com.nine.ironladders.common.block;

import com.nine.ironladders.common.BlockStateUtils;
import com.nine.ironladders.common.item.MorphType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import org.jetbrains.annotations.NotNull;

public class BaseMetalLadder extends LadderBlock {
    private LadderType type;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty HAS_SIGNAL = BooleanProperty.create("has_signal");
    public static final BooleanProperty LIGHTED = BooleanProperty.create("lighted");
    public static final BooleanProperty HIDDEN = BooleanProperty.create("hidden");
    public static final EnumProperty<MorphType> MORPH_TYPE = EnumProperty.create("morph", MorphType.class);

    public BaseMetalLadder(Properties p_54345_, LadderType type) {
        super(p_54345_);
        this.type = type;
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(MORPH_TYPE, MorphType.NONE).setValue(HIDDEN, false).setValue(LIGHTED, false).setValue(HAS_SIGNAL, false).setValue(POWERED, false).setValue(FACING, Direction.NORTH)).setValue(WATERLOGGED, false));
    }
    @Override
    public void neighborChanged(@NotNull BlockState blockState, Level level, BlockPos pos, Block block, BlockPos pos2, boolean p_49382_) {
        if (!level.isClientSide && blockState.getValue(POWERED)) {
            boolean flag = blockState.getValue(HAS_SIGNAL);
            boolean flag2 = level.hasNeighborSignal(pos);
            boolean flag3 = hasActiveInARow(level, pos);
            if (flag != (flag2 || flag3)) {
                if (flag) {
                    level.scheduleTick(pos, this, 4);
                    updateChain(level,pos);
                } else {
                    level.setBlock(pos, blockState.cycle(HAS_SIGNAL), 2);
                    updateChain(level,pos);
                }
            }
        }
    }
    public void updateChain(Level level, BlockPos pos) {
        boolean canGoUp = true;
        boolean canGoDown = true;
        Direction startFacingDirection = level.getBlockState(pos).getValue(FACING);
        for (int height = 1; height < 256; height++) {
            BlockPos posAbove = pos.above(height);
            BlockPos posBelow = pos.below(height);
            Block blockAbove = level.getBlockState(posAbove).getBlock();
            Block blockBelow = level.getBlockState(posBelow).getBlock();
            BlockState stateAbove = level.getBlockState(posAbove);
            BlockState stateBelow = level.getBlockState(posBelow);
            if(canGoUp) {
                if (blockAbove instanceof BaseMetalLadder) {
                    Direction currentUpFacingDirection = stateAbove.getValue(FACING);
                    canGoUp = startFacingDirection == currentUpFacingDirection && stateAbove.getValue(POWERED);
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                    if (blockBelow instanceof BaseMetalLadder){
                    Direction currentDownFacingDirection = stateBelow.getValue(FACING);
                    canGoDown = startFacingDirection == currentDownFacingDirection && stateBelow.getValue(POWERED);
                }
                else {
                    canGoDown = false;
                }
            }
            if (canGoDown || canGoUp){
                if (canGoUp) {
                    if ((hasActiveInARow(level, posAbove) || level.hasNeighborSignal(posAbove)) != stateAbove.getValue(HAS_SIGNAL)) {
                        stateAbove = BlockStateUtils.getStateWithSyncedProps(stateAbove, stateAbove);
                        level.setBlock(posAbove, stateAbove.cycle(HAS_SIGNAL), 2);
                    }
                }
                if (canGoDown) {
                    if ((hasActiveInARow(level,posBelow) || level.hasNeighborSignal(posBelow)) != stateBelow.getValue(HAS_SIGNAL)){
                        stateBelow = BlockStateUtils.getStateWithSyncedProps(stateBelow, stateBelow);
                        level.setBlock(posBelow, stateBelow.cycle(HAS_SIGNAL), 2);
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
                    canGoUp =  startFacingDirection == currentUpFacingDirection && stateAbove.getValue(POWERED);
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof BaseMetalLadder){
                    Direction currentDownFacingDirection = stateBelow.getValue(FACING);
                    canGoDown =  startFacingDirection == currentDownFacingDirection && stateBelow.getValue(POWERED);
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
        if (p_221937_.getValue(POWERED)) {
            if (p_221937_.getValue(HAS_SIGNAL) && !level.hasNeighborSignal(pos) && !hasActiveInARow(level, pos)) {
                level.setBlock(pos, p_221937_.cycle(HAS_SIGNAL), 2);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
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
        return blockState.getValue(POWERED);
    }

    public boolean isPoweredAndHasSignal(BlockState blockState){
        if (blockState.getBlock() instanceof BaseMetalLadder){
            return isPowered(blockState) && blockState.getValue(HAS_SIGNAL);
        }
        return false;
    }
}
