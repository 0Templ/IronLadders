package com.nine.ironladders.common.item;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.BlockStateUtils;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.LadderType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UpgradeItem extends Item {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    UpgradeType type;
    public UpgradeItem(Settings settings, UpgradeType type) {
        super(settings);
        this.type = type;
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.getDamage() == stack.getMaxDamage() && !world.isClient) {
            stack.decrement(1);
        }
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!IronLadders.CONFIG.enableTierLaddersUpgrade){
            return ActionResult.FAIL;
        }
        PlayerEntity player = context.getPlayer();
        BlockPos blockPos = context.getBlockPos();
        World level = context.getWorld();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        BlockState upgradeState;
        UpgradeItem upgradeItem = this;
        ItemStack stack = context.getStack();
        if (player == null || level.isClient || stack.getDamage() == stack.getMaxDamage()){
            return ActionResult.FAIL;
        }
        if (stack.getDamage() == stack.getMaxDamage()){
            remove(player,stack);
        }
        if (block instanceof LadderBlock) {
            upgradeState = type.getUpgradeType().getBlock(type.getUpgradeType()).getStateWithProperties(blockState);
            boolean defaultLadder = block == Blocks.LADDER && type.getPreviousType() == LadderType.DEFAULT;
            boolean upgradeableLadder = block instanceof BaseMetalLadder metalLadder && type.getPreviousType() == metalLadder.getType();
            if (!player.isSneaking()){
                if (defaultLadder || upgradeableLadder){
                    upgradeSingleBlock(level,player,blockPos,upgradeState,stack);
                    return ActionResult.SUCCESS;
                }
            }
            else {
                if (defaultLadder || upgradeableLadder){
                    upgradeSingleBlock(level,player,blockPos,upgradeState,stack);
                    upgradeMultipleLadders(player, level, blockPos, blockState, upgradeState, upgradeItem, stack);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.FAIL;
    }
    private static void upgradeSingleBlock(World level,PlayerEntity player, BlockPos blockPos, BlockState upgradeState, ItemStack stack){
        level.removeBlock(blockPos,false);
        level.setBlockState(blockPos,upgradeState,3);
        level.addParticle(ParticleTypes.HAPPY_VILLAGER, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 10, 0.5, 0);

        level.playSound(null, blockPos, SoundEvents.BLOCK_LADDER_PLACE, SoundCategory.PLAYERS, 0.9F + level.random.nextFloat(), 1.0f);
        if (!player.getAbilities().creativeMode){
            stack.damage(1, level.getRandom(), null);
        }
    }
    public void upgradeMultipleLadders(PlayerEntity player, World level, BlockPos blockPos, BlockState oldState, BlockState newState, UpgradeItem upgradeItem, ItemStack stack) {
        int ladderCount = 0;
        int height = 1;
        boolean canGoUp = true;
        boolean canGoDown = true;
        while (ladderCount < 128) {
            Block startBlock = oldState.getBlock();
            LadderType startType = LadderType.DEFAULT;
            LadderType upperType = startType;
            LadderType bottomType = startType;
            Direction startFacingDirection = oldState.get(FACING);

            if (startBlock instanceof BaseMetalLadder metalLadder){
                startType = metalLadder.getType();
            }

            BlockState stateAbove = level.getBlockState(blockPos.offset(Direction.UP, height));
            BlockPos abovePos = blockPos.offset(Direction.UP, height);
            Block blockAbove = stateAbove.getBlock();

            BlockPos belowPos = blockPos.offset(Direction.DOWN, height);
            BlockState stateBelow = level.getBlockState(blockPos.offset(Direction.DOWN, height));
            Block blockBelow = stateBelow.getBlock();
            if (blockAbove instanceof BaseMetalLadder metalLadder){
                upperType = metalLadder.getType();
            }
            if (blockBelow instanceof BaseMetalLadder metalLadder){
                bottomType = metalLadder.getType();
            }

            if(canGoUp) {
                if (blockAbove instanceof LadderBlock) {
                    Direction currentUpFacingDirection = stateAbove.get(FACING);
                    canGoUp =  startFacingDirection == currentUpFacingDirection;
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof LadderBlock){
                    Direction currentDownFacingDirection = stateBelow.get(FACING);
                    canGoDown =  startFacingDirection == currentDownFacingDirection;
                }
                else {
                    canGoDown = false;
                }
            }
            if ((canGoUp || canGoDown) && stack.getDamage() < stack.getMaxDamage()){
                if (canGoUp){
                    Direction currentUpFacingDirection = stateAbove.get(FACING);
                    if (currentUpFacingDirection == startFacingDirection && upperType == startType) {
                        newState = BlockStateUtils.getStateWithSyncedProps(newState,stateAbove);
                        upgradeSingleBlock(level,player,abovePos,newState,stack);
                        ladderCount++;
                    }
                }

                if (canGoDown && ladderCount != stack.getMaxDamage()){
                    Direction currentDownFacingDirection = stateBelow.get(FACING);
                    if (currentDownFacingDirection == startFacingDirection && bottomType == startType) {
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
        if (stack.getDamage() == stack.getMaxDamage()){
            upgradeItem.remove(player,stack);
        }
    }

    private void remove(PlayerEntity player, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!IronLadders.CONFIG.enableTierLaddersUpgrade){
            tooltip.add(Text.translatable("tooltip.item.upgrade.disabled").formatted(Formatting.RED));
            return;
        }
        if (Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip.item.upgrade.upgrades_amount",stack.getMaxDamage() - stack.getDamage()).formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("tooltip.item.upgrade.additional_info").formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

}
