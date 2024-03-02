package com.nine.ironladders.common.item;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.BlockStateUtils;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.LadderType;
import com.nine.ironladders.init.BlockRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MorphUpgradeItem extends Item {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    UpgradeType type;
    public MorphUpgradeItem(Settings settings, UpgradeType type) {
        super(settings);
        this.type = type;
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null){
            return ActionResult.FAIL;
        }
        if (!IronLadders.CONFIG.enableMorphLaddersUpgrade){
            return ActionResult.FAIL;
        }
        BlockPos blockPos = context.getBlockPos();
        World level = context.getWorld();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        ItemStack stack = context.getStack();
        EnumProperty<MorphType> property = type.getMorphProperty();
        if (block instanceof LadderBlock && property != null) {
            if (player.isSneaking() ) {
                if (BlockRegistry.getMorphId(block) != 0){
                    setMorphType(stack, BlockRegistry.getMorphId(block));
                    level.playSound(null, blockPos, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.PLAYERS, 0.8F + level.random.nextFloat(), 1.0f);
                }
            }
            else if(block instanceof BaseMetalLadder metalLadder){
                morphSingleBlock(blockState,level,blockPos,stack);
                metalLadder.updateChain(level,blockPos);
            }
        }

        return ActionResult.FAIL;
    }
    public void morphSingleBlock(BlockState blockState, World level, BlockPos blockPos, ItemStack stack){
        EnumProperty<MorphType> property = type.getMorphProperty();
        MorphType morphType = blockState.get(property);
        BlockState upgradeState;
        if (morphType == morphType.getTypeFromId(getMorphType(stack))){
            level.playSound(null, blockPos, SoundEvents.BLOCK_LADDER_BREAK, SoundCategory.PLAYERS, 0.9F + level.random.nextFloat(), 1.0f);
            upgradeState =  blockState.with(property, MorphType.NONE);
        }
        else {
            upgradeState =  blockState.with(property, morphType.getTypeFromId(getMorphType(stack)));
            level.playSound(null, blockPos, SoundEvents.BLOCK_LADDER_BREAK, SoundCategory.PLAYERS, 0.9F + level.random.nextFloat(), 1.0f);
        }
        level.removeBlock(blockPos, false);
        level.setBlockState(blockPos, upgradeState, 3);
    }
    public void morphMultipleLadders(World level, BlockPos blockPos, BlockState state, ItemStack stack) {
        int height = 1;
        boolean canGoUp = true;
        boolean canGoDown = true;
        Block startBlock = state.getBlock();
        LadderType startType = LadderType.DEFAULT;
        LadderType upperType = startType;
        LadderType bottomType = startType;
        Direction startFacingDirection = state.get(FACING);
        MorphType startMorphType = state.get(type.getMorphProperty());
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
                if (blockAbove instanceof BaseMetalLadder) {
                    Direction currentUpFacingDirection = stateAbove.get(FACING);
                    canGoUp =  startFacingDirection == currentUpFacingDirection && startMorphType == stateAbove.get(type.getMorphProperty());
                }
                else {
                    canGoUp = false;
                }
            }
            if(canGoDown){
                if (blockBelow instanceof BaseMetalLadder ) {
                    Direction currentDownFacingDirection = stateBelow.get(FACING);
                    canGoDown =  startFacingDirection == currentDownFacingDirection && startMorphType == stateBelow.get(type.getMorphProperty());
                }
                else {
                    canGoDown = false;
                }
            }
            if ((canGoUp || canGoDown)){
                if (canGoUp){
                    if (upperType == startType) {
                        state = BlockStateUtils.getStateWithSyncedProps(state,stateAbove);
                        morphSingleBlock(state,level,abovePos,stack);
                    }
                }
                if (canGoDown){
                    if (bottomType == startType) {
                        state = BlockStateUtils.getStateWithSyncedProps(state,stateBelow);
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
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
    public void setMorphType(ItemStack stack, int type) {
        stack.getOrCreateNbt().putInt("morph_type", type);
    }
    public int getMorphType(ItemStack stack) {
        return stack.getOrCreateNbt().getInt("morph_type");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World level, List<Text> tooltip, TooltipContext context) {
        if (!IronLadders.CONFIG.enableMorphLaddersUpgrade){
            tooltip.add(Text.translatable("tooltip.item.upgrade.disabled").formatted(Formatting.RED));
            return;
        }
        if (!Screen.hasShiftDown()){
            return;
        }
        if (getMorphType(stack)!=0){
            tooltip.add(Text.translatable("tooltip.item.upgrade.morph_info_4").formatted(Formatting.GRAY)
                    .append(Text.translatable("tooltip.item.upgrade." + MorphType.NONE.getTypeFromId(getMorphType(stack)).toString()).formatted(Formatting.GRAY)));
        }
        tooltip.add(Text.translatable("tooltip.item.upgrade.morph_info").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.item.upgrade.morph_info_3").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.item.upgrade.additional_info_2").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.item.upgrade.not_consumable").formatted(Formatting.GRAY));

        super.appendTooltip(stack, level, tooltip, context);
    }

}
