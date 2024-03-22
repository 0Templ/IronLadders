package com.nine.ironladders.compat.jade;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.item.CustomUpgradeItem;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import com.nine.ironladders.common.item.UpgradeItem;
import com.nine.ironladders.common.utils.LadderType;
import com.nine.ironladders.common.utils.MorphType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

public enum MetalLadderComponentProvider implements IBlockComponentProvider {

    INSTANCE;
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig pluginConfig) {
        Player player = Minecraft.getInstance().player;
        Block block = blockAccessor.getBlock();
        if (blockAccessor.getBlock() instanceof LadderBlock && IronLadders.CONFIG.jadeIntegration && player!=null) {
            BlockState state = blockAccessor.getBlockState();
            ItemStack stack = player.getMainHandItem();
            if (block instanceof BaseMetalLadder baseMetalLadder) {
                if (baseMetalLadder.isPowered(state)) {
                    Component onOff = baseMetalLadder.isPoweredAndHasSignal(state) ?
                            Component.translatable("config.info.power_state_on").withStyle(ChatFormatting.GREEN) :
                            Component.translatable("config.info.power_state_off").withStyle(ChatFormatting.RED);
                    Component powerState = Component.translatable("config.info.power_state").withStyle(ChatFormatting.GRAY).append(onOff);
                    tooltip.add(powerState);
                }
                if (stack.getItem() instanceof MorphUpgradeItem) {
                    if (baseMetalLadder.isMorphed(state)) {
                        Component morphType = Component.translatable("tooltip.item.upgrade." +
                                state.getValue(EnumProperty.create("morph", MorphType.class)).toString()).withStyle(ChatFormatting.GRAY);
                        Component morphState = Component.translatable("config.info.morph_state").withStyle(ChatFormatting.GRAY).append(morphType);
                        tooltip.add(morphState);
                    }
                    Component component = Component.translatable("config.info.morph_upgrade").withStyle(ChatFormatting.GRAY);
                    tooltip.add(component);
                }

                else if (stack.getItem() instanceof CustomUpgradeItem) {
                    Component component = Component.translatable("config.info.other_upgrade").withStyle(ChatFormatting.GRAY);
                    tooltip.add(component);
                }
            }
            if (stack.getItem() instanceof UpgradeItem upgradeItem && stack.getDamageValue() != stack.getMaxDamage()-1) {
                if (block instanceof BaseMetalLadder baseMetalLadder && baseMetalLadder.getType() == upgradeItem.getType().getPreviousType()
                || (LadderType.DEFAULT == upgradeItem.getType().getPreviousType() && block == Blocks.LADDER)) {
                    Component component = Component.translatable("config.info.tier_upgrade").withStyle(ChatFormatting.GRAY);
                    tooltip.add(component);
                }
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(IronLadders.MODID);
    }

}