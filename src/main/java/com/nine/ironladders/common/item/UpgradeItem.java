package com.nine.ironladders.common.item;

import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.LadderType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UpgradeItem extends Item {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
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
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context)
    {
        Player player = context.getPlayer();
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        BlockState upgradeState = null;
        UpgradeItem upgradeItem = this;

        if (player == null || level.isClientSide || stack.getDamageValue() == stack.getMaxDamage()){
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
                    upgradeSingleBlock(level,blockPos,upgradeState,stack);
                    return InteractionResult.SUCCESS;
                }
            }
            else {
                if (defaultLadder || upgradeableLadder){
                    upgradeSingleBlock(level,blockPos,upgradeState,stack);
                    upgradeMultipleLadders(player, level, blockPos, blockState, upgradeState, upgradeItem, stack);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }
    private static void upgradeSingleBlock(Level level, BlockPos blockPos, BlockState upgradeState, ItemStack stack){
        level.removeBlock(blockPos,false);
        level.setBlock(blockPos,upgradeState,3);
        level.levelEvent(2005, blockPos, 0);
        stack.hurt(1,level.getRandom(),null);
    }

    public void upgradeMultipleLadders(Player player, Level level, BlockPos blockPos, BlockState oldState, BlockState newState, UpgradeItem upgradeItem, ItemStack stack) {
        int ladderCount = 0;
        int height = 1;
        while (true) {
            Block startBlock = oldState.getBlock();
            LadderType startType = LadderType.DEFAULT;
            LadderType upperType = startType;
            LadderType bottomType = startType;
            Direction startFacingDirection = oldState.getValue(FACING);

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
            boolean canGoUp = blockAbove instanceof LadderBlock;
            boolean canGoDown = blockBelow instanceof LadderBlock;
            if ((canGoUp || canGoDown) && stack.getDamageValue() < stack.getMaxDamage()){
                if (canGoUp){
                    Direction currentUpFacingDirection = stateAbove.getValue(FACING);
                    if (currentUpFacingDirection == startFacingDirection && upperType == startType) {
                        newState = newState.setValue(WATERLOGGED, stateAbove.getValue(WATERLOGGED));
                        upgradeSingleBlock(level,abovePos,newState,stack);
                        ladderCount++;
                    }
                }
                if (canGoDown && ladderCount != stack.getMaxDamage()){
                    Direction currentDownFacingDirection = stateBelow.getValue(FACING);
                    if (currentDownFacingDirection == startFacingDirection && bottomType == startType) {
                        newState = newState.setValue(WATERLOGGED, stateBelow.getValue(WATERLOGGED));
                        upgradeSingleBlock(level,belowPos,newState,stack);
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
    private void remove(Player player,ItemStack stack){
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
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
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (Screen.hasShiftDown()){
            tooltip.add(Component.translatable("tooltip.item.upgrade.upgrades_amount",stack.getMaxDamage() - stack.getDamageValue()).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.item.upgrade.additional_info").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
