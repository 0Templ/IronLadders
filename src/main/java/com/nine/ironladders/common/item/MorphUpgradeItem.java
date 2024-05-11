package com.nine.ironladders.common.item;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.component.CustomComponents;
import com.nine.ironladders.common.utils.*;
import com.nine.ironladders.init.BlockRegistry;
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
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("DataFlowIssue")
public class MorphUpgradeItem extends Item {
    UpgradeType type;
    public MorphUpgradeItem(Properties p_41383_, UpgradeType type) {
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
        if (!ILConfig.enableMorphLaddersUpgrade.get()){
            return InteractionResult.FAIL;
        }
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        EnumProperty<MorphType> property = type.getMorphProperty();
        if ((block instanceof LadderBlock || block instanceof VineBlock) && property != null ) {
            if (player.isShiftKeyDown()) {
                if (BlockRegistry.getMorphId(block) != 0){
                    setMorphType(stack, BlockRegistry.getMorphId(block));
                    level.playSound(null, blockPos, SoundEvents.SLIME_SQUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
            else if(block instanceof BaseMetalLadder metalLadder){
                morphSingleBlock(blockState,level,blockPos,stack);
                metalLadder.updateChain(level,blockPos);
            }
        }

        return InteractionResult.FAIL;
    }
    public void morphSingleBlock(BlockState blockState, Level level, BlockPos blockPos, ItemStack stack){
        EnumProperty<MorphType> property = type.getMorphProperty();
        MorphType morphType = blockState.getValue(property);
        BlockState upgradeState;

        if (morphType == morphType.getTypeFromId(getMorphType(stack))){
            level.playSound(null, blockPos, SoundEvents.LADDER_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            upgradeState =  blockState.setValue(property, MorphType.NONE);
        }
        else {
            upgradeState =  blockState.setValue(property, morphType.getTypeFromId(getMorphType(stack)));
            level.playSound(null, blockPos, SoundEvents.LADDER_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        level.removeBlock(blockPos, false);
        level.setBlock(blockPos, upgradeState, 3);
        level.updateNeighborsAt(blockPos.above(),level.getBlockState(blockPos).getBlock());
    }
    public void morphMultipleLadders(Level level, BlockPos blockPos, BlockState state, ItemStack stack) {
        int height = 1;
        boolean canGoUp = true;
        boolean canGoDown = true;
        Block startBlock = state.getBlock();
        LadderType startType = LadderType.DEFAULT;
        LadderType upperType = startType;
        LadderType bottomType = startType;
        Direction startFacingDirection = state.getValue(LadderProperties.FACING);
        MorphType startMorphType = state.getValue(type.getMorphProperty());
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
                if (blockAbove instanceof BaseMetalLadder) {
                    Direction currentUpFacingDirection = stateAbove.getValue(LadderProperties.FACING);
                    canGoUp =  startFacingDirection == currentUpFacingDirection && startMorphType == stateAbove.getValue(type.getMorphProperty());
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof BaseMetalLadder ) {
                    Direction currentDownFacingDirection = stateBelow.getValue(LadderProperties.FACING);
                    canGoDown =  startFacingDirection == currentDownFacingDirection && startMorphType == stateBelow.getValue(type.getMorphProperty());
                }
                else {
                    canGoDown = false;
                }
            }
            if ((canGoUp || canGoDown)){
                if (canGoUp){
                    if (upperType == startType) {
                        state = BlockStateUtils.getStateWithSyncedProps(stateAbove,stateAbove);
                        morphSingleBlock(state,level,abovePos,stack);
                    }
                }
                if (canGoDown){
                    if (bottomType == startType) {
                        state = BlockStateUtils.getStateWithSyncedProps(stateBelow,stateBelow);
                        morphSingleBlock(state,level,belowPos,stack);
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
    public void setMorphType(ItemStack stack, int type) {
        stack.set(CustomComponents.MORPH_TYPE.get(), type);
    }
    public int getMorphType(ItemStack stack) {
        if (stack.get(CustomComponents.MORPH_TYPE.get())!=null){
            return stack.get(CustomComponents.MORPH_TYPE.get());
        }
        return 0;
    }



    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (!ILConfig.enableMorphLaddersUpgrade.get()){
            tooltip.add(Component.translatable("tooltip.item.upgrade.disabled").withStyle(ChatFormatting.RED));
            return;
        }
        if (!Screen.hasShiftDown()){
            return;
        }
        if (getMorphType(stack)!=0){
            tooltip.add(Component.translatable("tooltip.item.upgrade.morph_info_4").withStyle(ChatFormatting.GRAY)
                    .append(Component.translatable("tooltip.item.upgrade." + MorphType.NONE.getTypeFromId(getMorphType(stack)).toString()).withStyle(ChatFormatting.GRAY)));
        }
        tooltip.add(Component.translatable("tooltip.item.upgrade.morph_info").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.item.upgrade.morph_info_2").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.item.upgrade.morph_info_3").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.item.upgrade.additional_info_2").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.item.upgrade.not_consumable").withStyle(ChatFormatting.GRAY));
    }
}
