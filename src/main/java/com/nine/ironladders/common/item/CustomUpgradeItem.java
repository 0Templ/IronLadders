package com.nine.ironladders.common.item;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.BlockStateUtils;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.LadderType;
import com.nine.ironladders.init.ItemRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomUpgradeItem extends Item {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    UpgradeType type;
    public CustomUpgradeItem(Settings settings, UpgradeType type) {
        super(settings);
        this.type = type;
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (player == null){
            return ActionResult.FAIL;
        }
        if (stack.getItem() == ItemRegistry.POWER_UPGRADE_ITEM && !IronLadders.CONFIG.enablePoweredLaddersUpgrade){
            return ActionResult.FAIL;
        }
        if (stack.getItem() == ItemRegistry.LIGHT_UPGRADE_ITEM && !IronLadders.CONFIG.enableGlowingLaddersUpgrade){
            return ActionResult.FAIL;
        }
        BlockPos blockPos = context.getBlockPos();
        World level = context.getWorld();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();

        BooleanProperty property = type.getProperty();
        if (block instanceof BaseMetalLadder && property != null) {
            if (!player.isSneaking()) {
                upgradeSingleBlock(blockState, level, blockPos);
            } else {
                upgradeSingleBlock(blockState, level, blockPos);
                upgradeMultipleLadders(level, blockPos, blockState);
            }
        }
        return ActionResult.FAIL;
    }
    private void upgradeSingleBlock(BlockState blockState, World level, BlockPos blockPos){
        BooleanProperty property = type.getProperty();
        BlockState upgradeState = blockState.with(property, !blockState.get(property));
        level.removeBlock(blockPos, false);
        level.playSound(null, blockPos, SoundEvents.BLOCK_LADDER_PLACE, SoundCategory.PLAYERS, 0.9F + level.random.nextFloat(), 1.0f);
        level.setBlockState(blockPos, upgradeState, 3);
        level.updateNeighbors(blockPos.up(),level.getBlockState(blockPos).getBlock());
    }
    public void upgradeMultipleLadders(World level, BlockPos blockPos, BlockState state) {
        int height = 1;
        boolean canGoUp = true;
        boolean canGoDown = true;
        Block startBlock = state.getBlock();
        LadderType startType = LadderType.DEFAULT;
        LadderType upperType = startType;
        LadderType bottomType = startType;
        Direction startFacingDirection = state.get(FACING);
        boolean startPropertyValue = state.get(type.getProperty());
        while (height < 256) {
            if (startBlock instanceof BaseMetalLadder metalLadder){
                startType = metalLadder.getType();
            }
            BlockState stateAbove = level.getBlockState(blockPos.up(height));
            BlockPos abovePos = blockPos.up(height);
            Block blockAbove = stateAbove.getBlock();

            BlockPos belowPos = blockPos.down(height);
            BlockState stateBelow = level.getBlockState(blockPos.down(height));
            Block blockBelow = stateBelow.getBlock();

            if (blockAbove instanceof BaseMetalLadder metalLadder){
                upperType = metalLadder.getType();
            }
            if (blockBelow instanceof BaseMetalLadder metalLadder){
                bottomType = metalLadder.getType();
            }
            if(canGoUp) {
                if (blockAbove instanceof BaseMetalLadder && startPropertyValue == stateAbove.get(type.getProperty())) {
                    Direction currentUpFacingDirection = stateAbove.get(FACING);
                    canGoUp =  startFacingDirection == currentUpFacingDirection;
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof BaseMetalLadder && startPropertyValue == stateBelow.get(type.getProperty())){
                    Direction currentDownFacingDirection = stateBelow.get(FACING);
                    canGoDown =  startFacingDirection == currentDownFacingDirection;
                }
                else {
                    canGoDown = false;
                }
            }
            if ((canGoUp || canGoDown)){
                if (canGoUp){
                    if (upperType == startType) {
                        state = BlockStateUtils.getStateWithSyncedPropsNoP(state, stateAbove);
                        upgradeSingleBlock(state,level,abovePos);
                    }
                }
                if (canGoDown){
                    if (bottomType == startType) {
                        state = BlockStateUtils.getStateWithSyncedPropsNoP(state, stateBelow);
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
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World level, List<Text> tooltip, TooltipContext context) {
        if (!IronLadders.CONFIG.enableTierLaddersUpgrade){
            tooltip.add(Text.translatable("tooltip.item.upgrade.disabled").formatted(Formatting.RED));
            return;
        }
        if (Screen.hasShiftDown()){
            tooltip.add(Text.translatable("tooltip.item.upgrade.upgrades_amount",stack.getMaxDamage() - stack.getDamage()).formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("tooltip.item.upgrade.additional_info").formatted(Formatting.GRAY));
        }
        boolean hasShiftDown = Screen.hasShiftDown();
        if (stack.getItem() == ItemRegistry.HIDE_UPGRADE_ITEM && hasShiftDown){
            tooltip.add(Text.translatable("tooltip.item.upgrade.hiding_info").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("tooltip.item.upgrade.hiding_info_2").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("tooltip.item.upgrade.additional_info_2").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("tooltip.item.upgrade.not_consumable").formatted(Formatting.GRAY));
            return;
        }
        if (stack.getItem() == ItemRegistry.POWER_UPGRADE_ITEM){
            if (!IronLadders.CONFIG.enablePoweredLaddersUpgrade){
                tooltip.add(Text.translatable("tooltip.item.upgrade.disabled").formatted(Formatting.RED));
                return;
            }
            if (hasShiftDown){
                tooltip.add(Text.translatable("tooltip.item.upgrade.powered_info").formatted(Formatting.GRAY));
                tooltip.add(Text.translatable("tooltip.item.upgrade.powered_info_2").formatted(Formatting.GRAY));
                tooltip.add(Text.translatable("tooltip.item.upgrade.additional_info_2").formatted(Formatting.GRAY));
                tooltip.add(Text.translatable("tooltip.item.upgrade.not_consumable").formatted(Formatting.GRAY));
                return;
            }
        }
        if (stack.getItem() == ItemRegistry.LIGHT_UPGRADE_ITEM){
            if (!IronLadders.CONFIG.enableGlowingLaddersUpgrade){
                tooltip.add(Text.translatable("tooltip.item.upgrade.disabled").formatted(Formatting.RED));
                return;
            }
            if (hasShiftDown) {
                tooltip.add(Text.translatable("tooltip.item.upgrade.light_info").formatted(Formatting.GRAY));
                tooltip.add(Text.translatable("tooltip.item.upgrade.additional_info_2").formatted(Formatting.GRAY));
                tooltip.add(Text.translatable("tooltip.item.upgrade.not_consumable").formatted(Formatting.GRAY));
                return;
            }
        }
        super.appendTooltip(stack, level, tooltip, context);
    }

}
