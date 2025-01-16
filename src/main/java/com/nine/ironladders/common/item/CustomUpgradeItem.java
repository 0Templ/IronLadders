package com.nine.ironladders.common.item;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.entity.MetalLadderEntity;
import com.nine.ironladders.common.utils.BlockStateUtils;
import com.nine.ironladders.common.utils.LadderType;
import com.nine.ironladders.common.utils.UpgradeType;
import com.nine.ironladders.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class CustomUpgradeItem extends Item {

    UpgradeType type;

    public CustomUpgradeItem(Properties props, UpgradeType type) {
        super(props);
        this.type = type;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        if (stack.getItem() == ItemRegistry.POWER_UPGRADE_ITEM.get() && !ILConfig.enablePoweredLaddersUpgrade.get()) {
            return InteractionResult.FAIL;
        }
        if (stack.getItem() == ItemRegistry.LIGHT_UPGRADE_ITEM.get() && !ILConfig.enableGlowingLaddersUpgrade.get()) {
            return InteractionResult.FAIL;
        }
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();

        BooleanProperty property = type.getProperty();
        if (block instanceof BaseMetalLadder && property != null) {
            if (!player.isShiftKeyDown()) {
                upgradeSingleBlock(blockState, level, blockPos);
            } else {
                upgradeSingleBlock(blockState, level, blockPos);
                upgradeMultipleLadders(level, blockPos, blockState);
            }
        }
        return InteractionResult.FAIL;
    }

    private void upgradeSingleBlock(BlockState blockState, Level level, BlockPos blockPos) {
        BooleanProperty property = type.getProperty();
        BlockState upgradeState = blockState.setValue(property, !blockState.getValue(property));
        MetalLadderEntity metalLadderEntity = new MetalLadderEntity(blockPos, upgradeState);
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof MetalLadderEntity metalLadderEntity2) {
            metalLadderEntity.setMorphState(metalLadderEntity2.getMorphState());
            level.removeBlockEntity(blockPos);
            blockEntity.setRemoved();
            level.playSound(null, blockPos, SoundEvents.LADDER_PLACE, SoundSource.BLOCKS, 1F, 0.9F + level.random.nextFloat() * 0.2F);
            level.setBlock(blockPos, upgradeState, 3);
            level.setBlockEntity(metalLadderEntity);
            level.updateNeighborsAt(blockPos.above(), level.getBlockState(blockPos).getBlock());
        }
    }

    public void upgradeMultipleLadders(Level level, BlockPos blockPos, BlockState state) {
        int height = 1;
        boolean canGoUp = true;
        boolean canGoDown = true;
        Block startBlock = state.getBlock();
        LadderType startType = LadderType.DEFAULT;
        LadderType upperType = startType;
        LadderType bottomType = startType;
        Direction startFacingDirection = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        boolean startPropertyValue = state.getValue(type.getProperty());
        while (height < level.getMaxY()) {
            if (startBlock instanceof BaseMetalLadder metalLadder) {
                startType = metalLadder.getType();
            }
            BlockState stateAbove = level.getBlockState(blockPos.above(height));
            BlockPos abovePos = blockPos.above(height);
            Block blockAbove = stateAbove.getBlock();
            BlockPos belowPos = blockPos.below(height);
            BlockState stateBelow = level.getBlockState(blockPos.below(height));
            Block blockBelow = stateBelow.getBlock();
            if (blockAbove instanceof BaseMetalLadder metalLadder) {
                upperType = metalLadder.getType();
            }
            if (blockBelow instanceof BaseMetalLadder metalLadder) {
                bottomType = metalLadder.getType();
            }
            if (canGoUp) {
                if (blockAbove instanceof BaseMetalLadder && blockAbove == startBlock && startPropertyValue == stateAbove.getValue(type.getProperty())) {
                    Direction currentUpFacingDirection = stateAbove.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    canGoUp = startFacingDirection == currentUpFacingDirection;
                } else {
                    canGoUp = false;
                }
            }
            if (canGoDown) {
                if (blockBelow instanceof BaseMetalLadder && blockBelow == startBlock && startPropertyValue == stateBelow.getValue(type.getProperty())) {
                    Direction currentDownFacingDirection = stateBelow.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    canGoDown = startFacingDirection == currentDownFacingDirection;
                } else {
                    canGoDown = false;
                }
            }
            if ((canGoUp || canGoDown)) {
                if (canGoUp) {
                    if (upperType == startType) {
                        state = BlockStateUtils.getStateWithSyncedPropsNoP(blockAbove.defaultBlockState(), stateAbove);
                        upgradeSingleBlock(state, level, abovePos);
                    }
                }
                if (canGoDown) {
                    if (bottomType == startType) {
                        state = BlockStateUtils.getStateWithSyncedPropsNoP(blockBelow.defaultBlockState(), stateBelow);
                        upgradeSingleBlock(state, level, belowPos);
                    }
                }
                height++;
            } else {
                break;
            }
        }
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (ItemRegistry.checkDisabled(stack.getItem())) {
            tooltip.add(Component.translatable("ironladders.tooltip.disabled").withStyle(ChatFormatting.RED));
            return;
        }
        boolean shiftDown = Screen.hasShiftDown();
        Style style = Style.EMPTY.withColor(shiftDown ? 0xcbcbcb : 0x808080);
        tooltip.add(
                Component.translatable("ironladders.tooltip.hold_for",
                        Component.translatable("ironladders.tooltip.shift").withStyle(style)).withStyle(ChatFormatting.GRAY)
        );
        if (shiftDown) {
            String key = this.getDescriptionId().split("\\.")[2];
            int i = 0;
            key = "ironladders.tooltip." + key + ".info_";
            while (Language.getInstance().has(key + i)) {
                tooltip.add(Component.translatable(key + i).withStyle(ChatFormatting.GRAY));
                i++;
            }
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
