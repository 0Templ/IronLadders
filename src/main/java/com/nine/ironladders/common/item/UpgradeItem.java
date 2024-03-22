package com.nine.ironladders.common.item;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.utils.BlockStateUtils;
import com.nine.ironladders.common.utils.LadderProperties;
import com.nine.ironladders.common.utils.LadderType;
import com.nine.ironladders.common.utils.UpgradeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UpgradeItem extends Item {
    UpgradeType type;
    public UpgradeItem(Properties p_41383_, UpgradeType type) {
        super(p_41383_);
        this.type = type;
    }
    @Override
    public void inventoryTick(ItemStack stack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (stack.getDamageValue() == stack.getMaxDamage() && !pLevel.isClientSide) {
            stack.shrink(1);
        }
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();

        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (!IronLadders.CONFIG.enableTierLaddersUpgrade){
            return InteractionResult.FAIL;
        }

        BlockState upgradeState;
        UpgradeItem upgradeItem = this;
        if (player == null || level.isClientSide || stack.getDamageValue() == stack.getMaxDamage() || !IronLadders.CONFIG.enableTierLaddersUpgrade){
            return InteractionResult.FAIL;
        }
        if (stack.getDamageValue() == stack.getMaxDamage()){
            remove(player,stack);
        }

        if (block instanceof LadderBlock){
            upgradeState = type.getUpgradeType().getBlock(type.getUpgradeType()).withPropertiesOf(blockState);
            boolean defaultLadder = block == Blocks.LADDER && type.getPreviousType() == LadderType.DEFAULT;
            boolean upgradeableLadder = block instanceof BaseMetalLadder metalLadder && type.getPreviousType() == metalLadder.getType();
            if (!player.isShiftKeyDown()){
                if (defaultLadder || upgradeableLadder){
                    upgradeSingleBlock(level,player,blockPos,upgradeState,stack);
                    return InteractionResult.SUCCESS;
                }
            }
            else {
                if (defaultLadder || upgradeableLadder){
                    upgradeSingleBlock(level,player,blockPos,upgradeState,stack);
                    upgradeMultipleLadders(player, level, blockPos, blockState, upgradeState, upgradeItem, stack);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }
    private static void upgradeSingleBlock(Level level,Player player, BlockPos blockPos, BlockState upgradeState, ItemStack stack){
        level.removeBlock(blockPos,false);
        level.setBlock(blockPos,upgradeState,3);
        level.levelEvent(2005, blockPos, 0);
        level.playSound((Player)null, blockPos, SoundEvents.LADDER_PLACE, SoundSource.BLOCKS, 1F, 0.9F + level.random.nextFloat() * 0.2F);
        if (!player.getAbilities().instabuild){
            stack.hurt(1,level.getRandom(),null);
        }
    }

    public void upgradeMultipleLadders(Player player, Level level, BlockPos blockPos, BlockState oldState, BlockState newState, UpgradeItem upgradeItem, ItemStack stack) {
        int ladderCount = 0;
        int height = 1;
        boolean canGoUp = true;
        boolean canGoDown = true;
        while (ladderCount < 128) {
            Block startBlock = oldState.getBlock();
            LadderType startType = LadderType.DEFAULT;
            LadderType upperType = startType;
            LadderType bottomType = startType;
            Direction startFacingDirection = oldState.getValue(LadderProperties.FACING);

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
                if (blockAbove instanceof LadderBlock) {
                    Direction currentUpFacingDirection = stateAbove.getValue(LadderProperties.FACING);
                    canGoUp =  startFacingDirection == currentUpFacingDirection;
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof LadderBlock){
                    Direction currentDownFacingDirection = stateBelow.getValue(LadderProperties.FACING);
                    canGoDown =  startFacingDirection == currentDownFacingDirection;
                }
                else {
                    canGoDown = false;
                }
            }
            if ((canGoUp || canGoDown) && stack.getDamageValue() < stack.getMaxDamage()){
                if (canGoUp){
                    if (upperType == startType) {
                        newState = BlockStateUtils.getStateWithSyncedProps(newState,stateAbove);
                        upgradeSingleBlock(level,player,abovePos,newState,stack);
                        ladderCount++;
                    }
                }
                if (canGoDown && ladderCount != stack.getMaxDamage()){
                    if (bottomType == startType) {
                        newState = BlockStateUtils.getStateWithSyncedProps(newState,stateBelow);
                        upgradeSingleBlock(level,player,belowPos,newState,stack);
                        ladderCount++;
                    }
                }
                height++;
            }
            else {
                break;
            }
        }
        if (stack.getDamageValue() == stack.getMaxDamage()){
            upgradeItem.remove(player,stack);
        }
    }
    public UpgradeType getType(){
        return this.type;
    }

    private void remove(Player player,ItemStack stack){
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }
    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (!IronLadders.CONFIG.enableTierLaddersUpgrade){
            tooltip.add(Component.translatable("tooltip.item.upgrade.disabled").withStyle(ChatFormatting.RED));
            return;
        }
        if (Screen.hasShiftDown()){
            tooltip.add(Component.translatable("tooltip.item.upgrade.upgrades_amount",stack.getMaxDamage() - stack.getDamageValue()).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.item.upgrade.additional_info").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
