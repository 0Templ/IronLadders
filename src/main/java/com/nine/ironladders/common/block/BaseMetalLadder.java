package com.nine.ironladders.common.block;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.block.entity.MetalLadderEntity;
import com.nine.ironladders.common.utils.LadderPositions;
import com.nine.ironladders.common.utils.LadderProperties;
import com.nine.ironladders.common.utils.LadderType;
import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BaseMetalLadder extends LadderBlock implements EntityBlock {

    private final LadderType type;

    public BaseMetalLadder(Properties properties, LadderType type) {
        super(properties.forceSolidOff().noOcclusion());
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LadderProperties.STATE_IN_CHAIN, LadderPositions.SINGLE)
                .setValue(LadderProperties.HIDDEN, false)
                .setValue(LadderProperties.LIGHTED, false)
                .setValue(LadderProperties.HAS_SIGNAL, false)
                .setValue(LadderProperties.POWERED, false)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean supportsExternalFaceHiding(BlockState state) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        return pDirection == Direction.UP && pAdjacentBlockState.getBlock() instanceof LadderBlock;
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @org.jetbrains.annotations.Nullable Entity entity) {
        if (level.getBlockEntity(pos) instanceof MetalLadderEntity ladderEntity) {
            return ladderEntity.getMorphState() != null ? ladderEntity.getMorphState().getSoundType() : this.soundType;

        }
        return this.soundType;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MetalLadderEntity metalLadderEntity) {
            BlockState morphState = metalLadderEntity.getMorphState();
            if (metalLadderEntity.getMorphState() != null && (!(metalLadderEntity.getMorphState().getBlock() instanceof BaseMetalLadder) || metalLadderEntity.getMorphState().getBlock() instanceof CryingObsidianLadder)) {
                morphState.getBlock().animateTick(state, level, pos, random);
            }
        }
    }

    @Override
    public void neighborChanged(@NotNull BlockState blockState, Level level, BlockPos pos, Block block, BlockPos pos2, boolean b) {
        if (!level.isClientSide && blockState.getValue(LadderProperties.POWERED)) {
            updatePositionRelations(blockState, level, pos);
            boolean flag = blockState.getValue(LadderProperties.HAS_SIGNAL);
            boolean flag2 = level.hasNeighborSignal(pos);
            boolean flag3 = hasActiveInARow(level, pos);
            if (flag != (flag2 || flag3)) {
                if (flag) {
                    level.scheduleTick(pos, this, 1);
                    updateChain(level, pos);
                } else {
                    level.setBlock(pos, blockState.cycle(LadderProperties.HAS_SIGNAL), 3);
                    updateChain(level, pos);
                }
            }
        }
    }

    private void updatePositionRelations(BlockState state, Level level, BlockPos pos) {
        if (!state.getValue(LadderProperties.POWERED)) {
            return;
        }
        boolean hasPoweredAbove = false;
        boolean hasPoweredBelow = false;
        Direction startDirection = state.getValue(LadderProperties.FACING);
        if (level.getBlockState(pos.above()).getBlock() instanceof BaseMetalLadder && level.getBlockState(pos.above()).getValue(FACING) == startDirection) {
            hasPoweredAbove = level.getBlockState(pos.above()).getValue(LadderProperties.POWERED);
        }
        if (level.getBlockState(pos.below()).getBlock() instanceof BaseMetalLadder && level.getBlockState(pos.below()).getValue(FACING) == startDirection) {
            hasPoweredBelow = level.getBlockState(pos.below()).getValue(LadderProperties.POWERED);
        }
        if (hasPoweredAbove && hasPoweredBelow) {
            level.setBlock(pos, state.setValue(LadderProperties.STATE_IN_CHAIN, LadderPositions.HAS_DOWN_AND_UP_NEIGHBOUR), 3);
            return;
        }
        if (hasPoweredAbove) {
            level.setBlock(pos, state.setValue(LadderProperties.STATE_IN_CHAIN, LadderPositions.HAS_UP_NEIGHBOUR), 3);
            return;
        }
        if (hasPoweredBelow) {
            level.setBlock(pos, state.setValue(LadderProperties.STATE_IN_CHAIN, LadderPositions.HAS_DOWN_NEIGHBOUR), 3);
            return;
        }
        level.setBlock(pos, state.setValue(LadderProperties.STATE_IN_CHAIN, LadderPositions.SINGLE), 3);
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
            if (canGoUp) {
                if (blockAbove instanceof BaseMetalLadder) {
                    Direction currentUpFacingDirection = stateAbove.getValue(LadderProperties.FACING);
                    canGoUp = startFacingDirection == currentUpFacingDirection && stateAbove.getValue(LadderProperties.POWERED);
                } else {
                    canGoUp = false;
                }
            }
            if (canGoDown) {
                if (blockBelow instanceof BaseMetalLadder) {
                    Direction currentDownFacingDirection = stateBelow.getValue(LadderProperties.FACING);
                    canGoDown = startFacingDirection == currentDownFacingDirection && stateBelow.getValue(LadderProperties.POWERED);
                } else {
                    canGoDown = false;
                }
            }
            if (canGoDown || canGoUp) {
                if (canGoUp) {
                    if ((hasActiveInARow(level, posAbove) || level.hasNeighborSignal(posAbove)) != stateAbove.getValue(LadderProperties.HAS_SIGNAL)) {
                        level.setBlock(posAbove, stateAbove.cycle(LadderProperties.HAS_SIGNAL), 3);
                    }
                }
                if (canGoDown) {
                    if ((hasActiveInARow(level, posBelow) || level.hasNeighborSignal(posBelow)) != stateBelow.getValue(LadderProperties.HAS_SIGNAL)) {
                        level.setBlock(posBelow, stateBelow.cycle(LadderProperties.HAS_SIGNAL), 3);
                    }
                }
            } else {
                break;
            }
        }
    }

    private boolean hasActiveInARow(Level level, BlockPos pos) {
        int height = 1;
        boolean canGoDown = true;
        boolean canGoUp = true;
        Direction startFacingDirection = level.getBlockState(pos).getValue(FACING);
        while (height < level.getHeight()) {
            Block blockBelow = level.getBlockState(pos.below(height)).getBlock();
            Block blockAbove = level.getBlockState(pos.above(height)).getBlock();
            BlockState stateAbove = level.getBlockState(pos.above(height));
            BlockState stateBelow = level.getBlockState(pos.below(height));

            if (canGoUp) {
                if (blockAbove instanceof BaseMetalLadder) {
                    Direction currentUpFacingDirection = stateAbove.getValue(FACING);
                    canGoUp = startFacingDirection == currentUpFacingDirection && stateAbove.getValue(LadderProperties.POWERED);
                } else {
                    canGoUp = false;
                }
            }
            if (canGoDown) {
                if (blockBelow instanceof BaseMetalLadder) {
                    Direction currentDownFacingDirection = stateBelow.getValue(FACING);
                    canGoDown = startFacingDirection == currentDownFacingDirection && stateBelow.getValue(LadderProperties.POWERED);
                } else {
                    canGoDown = false;
                }
            }
            if (canGoUp || canGoDown) {
                if (canGoUp) {
                    if (level.hasNeighborSignal(pos.above(height))) {
                        return true;
                    }
                }
                if (canGoDown) {
                    if (level.hasNeighborSignal(pos.below(height))) {
                        return true;
                    }
                }
            } else {
                return false;
            }
            height++;
        }
        return false;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource p_221940_) {
        if (state.getValue(LadderProperties.POWERED)) {
            if (state.getValue(LadderProperties.HAS_SIGNAL) && !level.hasNeighborSignal(pos) && !hasActiveInARow(level, pos)) {
                level.setBlock(pos, state.cycle(LadderProperties.HAS_SIGNAL), 3);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LadderProperties.POWERED);
        builder.add(LadderProperties.LIGHTED);
        builder.add(LadderProperties.HIDDEN);
        builder.add(LadderProperties.STATE_IN_CHAIN);
        builder.add(LadderProperties.HAS_SIGNAL);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        int lightLevel = this == BlockRegistry.CRYING_OBSIDIAN_LADDER.get() ? 10 : 0;
        if (level.getBlockEntity(pos) instanceof MetalLadderEntity ladderEntity && ladderEntity.getMorphState() != null) {
            BlockState morphState = ladderEntity.getMorphState();
            if (morphState.getBlock() instanceof CryingObsidianLadder) {
                lightLevel = 10;
            } else if (!(morphState.getBlock() instanceof BaseMetalLadder)) {
                lightLevel = morphState.getLightEmission(level, pos);
            }
        }
        if (state.getValue(LadderProperties.LIGHTED)) {
            lightLevel = Math.max(lightLevel, 13);
        }
        return lightLevel;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState stateBefore, boolean bool) {
        if (!stateBefore.is(state.getBlock())) {
            level.updateNeighborsAt(pos,this);
        }
    }

    public LadderType getType() {
        return this.type;
    }

    public int getSpeedMultiplier() {
        boolean nonVanillaAllowed = ILConfig.nonVanillaLadders.get();
        return switch (this.type) {
            case COPPER -> ILConfig.copperLadderSpeedMultiplier.get();
            case IRON -> ILConfig.ironLadderSpeedMultiplier.get();
            case GOLD -> ILConfig.goldLadderSpeedMultiplier.get();
            case DIAMOND -> ILConfig.diamondLadderSpeedMultiplier.get();
            case OBSIDIAN, CRYING_OBSIDIAN, BEDROCK -> ILConfig.obsidianLadderSpeedMultiplier.get();
            case NETHERITE -> ILConfig.netheriteLadderSpeedMultiplier.get();
            case TIN -> nonVanillaAllowed ? ILConfig.tinLadderSpeedMultiplier.get() : 1;
            case BRONZE -> nonVanillaAllowed ? ILConfig.bronzeLadderSpeedMultiplier.get() : 1;
            case LEAD -> nonVanillaAllowed ? ILConfig.leadLadderSpeedMultiplier.get() : 1;
            case STEEL -> nonVanillaAllowed ? ILConfig.steelLadderSpeedMultiplier.get() : 1;
            case ALUMINUM -> nonVanillaAllowed ? ILConfig.aluminumLadderSpeedMultiplier.get() : 1;
            case SILVER -> nonVanillaAllowed ? ILConfig.silverLadderSpeedMultiplier.get() : 1;
            default -> 1;
        };
    }

    public boolean isPowered(BlockState blockState) {
        return blockState.getValue(LadderProperties.POWERED);
    }

    public boolean isPoweredAndHasSignal(BlockState blockState) {
        if (blockState.getBlock() instanceof BaseMetalLadder) {
            return isPowered(blockState) && blockState.getValue(LadderProperties.HAS_SIGNAL);
        }
        return false;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MetalLadderEntity(pos, state);
    }

    @Override
    public boolean canBeReplaced(@Nonnull BlockState state, @Nonnull BlockPlaceContext useContext) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag flag) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || blockGetter == null) {
            return;
        }
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("ironladders.tooltip.speed_bonus", getSpeedMultiplier()).withStyle(ChatFormatting.GRAY));
        }
    }

}
