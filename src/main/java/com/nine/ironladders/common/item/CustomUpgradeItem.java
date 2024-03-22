package com.nine.ironladders.common.item;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.BlockStateUtils;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.utils.LadderType;
import com.nine.ironladders.common.utils.UpgradeType;
import com.nine.ironladders.init.ItemRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomUpgradeItem extends Item {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    UpgradeType type;
    public CustomUpgradeItem(Properties p_41383_, UpgradeType type) {
        super(p_41383_);
        this.type=type;
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();

        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (player == null){
            return InteractionResult.FAIL;
        }
        if (stack.getItem() == ItemRegistry.POWER_UPGRADE_ITEM && !IronLadders.CONFIG.enablePoweredLaddersUpgrade){
            return InteractionResult.FAIL;
        }
        if (stack.getItem() == ItemRegistry.LIGHT_UPGRADE_ITEM && !IronLadders.CONFIG.enableGlowingLaddersUpgrade) {
            return InteractionResult.FAIL;
        }

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
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        Vec3 lookVector = player.getLookAngle();
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 traceEnd = eyePosition.add(lookVector.x * 5.0D, lookVector.y * 5.0D, lookVector.z * 5.0D);
        HitResult hitResult = level.clip(new ClipContext(eyePosition, traceEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (stack.getItem() instanceof MorphUpgradeItem morphUpgradeItem ) {
            Block block = state.getBlock();
            if (block instanceof BaseMetalLadder baseMetalLadder) {
                if (player.isShiftKeyDown() ) {
                    morphUpgradeItem.morphSingleBlock(state,level,pos,stack);
                    morphUpgradeItem.morphMultipleLadders(level,pos,state,stack);
                    baseMetalLadder.updateChain(level,pos);
                    return InteractionResultHolder.success(stack);
                }
            }
            return InteractionResultHolder.pass(stack);
        }
        return InteractionResultHolder.pass(stack);
    }
    private void upgradeSingleBlock(BlockState blockState, Level level, BlockPos blockPos){
        BooleanProperty property = type.getProperty();
        BlockState upgradeState = blockState.setValue(property, !blockState.getValue(property));
        level.removeBlock(blockPos, false);
        level.playSound((Player)null, blockPos, SoundEvents.LADDER_PLACE, SoundSource.BLOCKS, 1F, 0.9F + level.random.nextFloat() * 0.2F);
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
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        boolean hasShiftDown = Screen.hasShiftDown();
        if (stack.getItem() == ItemRegistry.HIDE_UPGRADE_ITEM && hasShiftDown){
            tooltip.add(Component.translatable("tooltip.item.upgrade.hiding_info").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.item.upgrade.hiding_info_2").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.item.upgrade.additional_info_2").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.item.upgrade.not_consumable").withStyle(ChatFormatting.GRAY));
            return;
        }
        if (stack.getItem() == ItemRegistry.POWER_UPGRADE_ITEM){
            if (!IronLadders.CONFIG.enablePoweredLaddersUpgrade){
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
        if (stack.getItem() == ItemRegistry.LIGHT_UPGRADE_ITEM){
            if (!IronLadders.CONFIG.enableGlowingLaddersUpgrade){
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
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
