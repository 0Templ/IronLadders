package com.nine.ironladders.common.item;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.utils.BlockStateUtils;
import com.nine.ironladders.common.utils.LadderType;
import com.nine.ironladders.common.utils.UpgradeType;
import com.nine.ironladders.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomUpgradeItem extends Item {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    UpgradeType type;
    public CustomUpgradeItem(Properties p_41383_, UpgradeType type) {
        super(p_41383_);
        this.type=type;
    }
    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context)
    {
        Player player = context.getPlayer();
        if (player == null){
            return InteractionResult.FAIL;
        }
        if (stack.getItem() == ItemRegistry.POWER_UPGRADE_ITEM.get() && !ILConfig.enablePoweredLaddersUpgrade.get()){
            return InteractionResult.FAIL;
        }
        if (stack.getItem() == ItemRegistry.LIGHT_UPGRADE_ITEM.get() && !ILConfig.enableGlowingLaddersUpgrade.get()){
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
    private void upgradeSingleBlock(BlockState blockState, Level level, BlockPos blockPos){
        BooleanProperty property = type.getProperty();
        BlockState upgradeState = blockState.setValue(property, !blockState.getValue(property));
        level.removeBlock(blockPos, false);
        level.playSound(null, blockPos, SoundEvents.LADDER_PLACE, SoundSource.BLOCKS, 1F, 0.9F + level.random.nextFloat() * 0.2F);
        level.setBlock(blockPos, upgradeState, 3);
        level.updateNeighborsAt(blockPos.above(),level.getBlockState(blockPos).getBlock());
    }
    public void upgradeMultipleLadders(Level level, BlockPos blockPos, BlockState state) {
        int height = 1;
        boolean canGoUp = true;
        boolean canGoDown = true;
        Block startBlock = state.getBlock();
        LadderType startType = LadderType.DEFAULT;
        LadderType upperType = startType;
        LadderType bottomType = startType;
        Direction startFacingDirection = state.getValue(FACING);
        boolean startPropertyValue = state.getValue(type.getProperty());
        while (height < 256) {
            if (startBlock instanceof BaseMetalLadder metalLadder){
                startType = metalLadder.getType();
            }
            BlockState stateAbove = level.getBlockState(blockPos.above(height));
            BlockPos abovePos = blockPos.above(height);
            Block blockAbove = stateAbove.getBlock();

            BlockPos belowPos = blockPos.below(height);
            BlockState stateBelow = level.getBlockState(blockPos.below(height));
            Block blockBelow = stateBelow.getBlock();
            if (blockAbove instanceof BaseMetalLadder metalLadder){
                upperType = metalLadder.getType();
            }
            if (blockBelow instanceof BaseMetalLadder metalLadder){
                bottomType = metalLadder.getType();
            }
            if(canGoUp) {
                if (blockAbove instanceof BaseMetalLadder && blockAbove == startBlock && startPropertyValue == stateAbove.getValue(type.getProperty())) {
                    Direction currentUpFacingDirection = stateAbove.getValue(FACING);
                    canGoUp = startFacingDirection == currentUpFacingDirection;
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof BaseMetalLadder && blockBelow == startBlock && startPropertyValue == stateBelow.getValue(type.getProperty())){
                    Direction currentDownFacingDirection = stateBelow.getValue(FACING);
                    canGoDown = startFacingDirection == currentDownFacingDirection;
                }
                else {
                    canGoDown = false;
                }
            }
            if ((canGoUp || canGoDown)){
                if (canGoUp){
                    if (upperType == startType) {
                        state = BlockStateUtils.getStateWithSyncedPropsNoP(blockAbove.defaultBlockState(), stateAbove);
                        upgradeSingleBlock(state,level,abovePos);
                    }
                }
                if (canGoDown){
                    if (bottomType == startType) {
                        state = BlockStateUtils.getStateWithSyncedPropsNoP(blockBelow.defaultBlockState(), stateBelow);
                        upgradeSingleBlock(state,level,belowPos);
                    }
                }
                height++;
            }
            else {
                break;
            }
        }
    }
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        boolean hasShiftDown = Screen.hasShiftDown();
        if (stack.getItem() == ItemRegistry.HIDE_UPGRADE_ITEM.get() && hasShiftDown){
            tooltip.add(Component.translatable("tooltip.item.upgrade.hiding_info").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.item.upgrade.hiding_info_2").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.item.upgrade.additional_info_2").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.item.upgrade.not_consumable").withStyle(ChatFormatting.GRAY));
            return;
        }
        if (stack.getItem() == ItemRegistry.POWER_UPGRADE_ITEM.get()){
            if (!ILConfig.enablePoweredLaddersUpgrade.get()){
                tooltip.add(Component.translatable("tooltip.item.upgrade.disabled").withStyle(ChatFormatting.RED));
                return;
            }
            if (hasShiftDown){
                tooltip.add(Component.translatable("tooltip.item.upgrade.powered_info").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("tooltip.item.upgrade.powered_info_2").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("tooltip.item.upgrade.additional_info_2").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("tooltip.item.upgrade.not_consumable").withStyle(ChatFormatting.GRAY));
                return;
            }
        }
        if (stack.getItem() == ItemRegistry.LIGHT_UPGRADE_ITEM.get()){
            if (!ILConfig.enableGlowingLaddersUpgrade.get()){
                tooltip.add(Component.translatable("tooltip.item.upgrade.disabled").withStyle(ChatFormatting.RED));
                return;
            }
            if (hasShiftDown) {
                tooltip.add(Component.translatable("tooltip.item.upgrade.light_info").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("tooltip.item.upgrade.additional_info_2").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("tooltip.item.upgrade.not_consumable").withStyle(ChatFormatting.GRAY));
                return;
            }
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
