package com.nine.ironladders.compat.jade;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.IronLadders;
import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.entity.MetalLadderEntity;
import com.nine.ironladders.common.item.CustomUpgradeItem;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import com.nine.ironladders.common.item.UpgradeItem;
import com.nine.ironladders.common.utils.LadderType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.Objects;

public enum MetalLadderComponentProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig pluginConfig) {
        Player player = Minecraft.getInstance().player;
        Block block = blockAccessor.getBlock();
        if (blockAccessor.getBlock() instanceof BaseMetalLadder && ILConfig.jadeIntegration.get() && player != null) {
            BlockState state = blockAccessor.getBlockState();
            ItemStack stack = player.getMainHandItem();
            if (block instanceof BaseMetalLadder baseMetalLadder) {
                if (stack.getItem() instanceof CustomUpgradeItem) {
                    Component component = Component.translatable("ironladders.waila.multiple_use").withStyle(ChatFormatting.GRAY);
                    tooltip.add(component);
                }
                if (baseMetalLadder.isPowered(state)) {
                    Component onOff = baseMetalLadder.isPoweredAndHasSignal(state) ?
                            Component.translatable("ironladders.waila.power_state_on").withStyle(ChatFormatting.GREEN) :
                            Component.translatable("ironladders.waila.power_state_off").withStyle(ChatFormatting.RED);
                    Component powerState = Component.translatable("ironladders.waila.power_state").withStyle(ChatFormatting.GRAY).append(onOff);
                    tooltip.add(powerState);
                }
                if (stack.getItem() instanceof MorphUpgradeItem && blockAccessor.getBlockEntity() instanceof MetalLadderEntity ladderEntity && ladderEntity.getMorphState() != null) {
                    Component namespace = ClientHelper.componentWithColor(MorphUpgradeItem.getMorphTypeMod(stack), 0x808080);
                    Component morphContent = MorphUpgradeItem.getMorphContent(stack);
                    if (!Objects.equals(MorphUpgradeItem.getMorphType(stack), "")) {
                        tooltip.add(Component.translatable("ironladders.waila.morph_type", namespace, morphContent).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
            if (stack.getItem() instanceof UpgradeItem upgradeItem && stack.getDamageValue() != stack.getMaxDamage() - 1) {
                if (block instanceof BaseMetalLadder baseMetalLadder && baseMetalLadder.getType() == upgradeItem.getType().getPreviousType()
                        || (LadderType.DEFAULT == upgradeItem.getType().getPreviousType() && block == Blocks.LADDER)) {
                    Component component = Component.translatable("ironladders.waila.multiple_use").withStyle(ChatFormatting.GRAY);
                    tooltip.add(component);
                }
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.parse(IronLadders.MODID);
    }

}