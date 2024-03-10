package com.nine.ironladders.compat.jade;
import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.item.MorphType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

public enum MetalLadderComponentProvider implements IBlockComponentProvider {

    INSTANCE;
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig pluginConfig) {
        if (blockAccessor.getBlock() instanceof BaseMetalLadder baseMetalLadder) {
            BlockState state = blockAccessor.getBlockState();
            if (baseMetalLadder.isPowered(state)){
                Component onOff = baseMetalLadder.isPoweredAndHasSignal(state) ?
                        Component.translatable("config.jade.power_state_on").withStyle(ChatFormatting.GREEN) :
                        Component.translatable("config.jade.power_state_off").withStyle(ChatFormatting.RED);
                Component powerState = Component.translatable("config.jade.power_state").withStyle(ChatFormatting.GRAY).append(onOff);
                tooltip.add(powerState);
            }
            if (baseMetalLadder.isMorphed(state)){
                Component morphType = Component.translatable("tooltip.item.upgrade." +
                        state.getValue(EnumProperty.create("morph", MorphType.class)).toString()).withStyle(ChatFormatting.GRAY);
                Component morphState = Component.translatable("config.jade.morph_state").withStyle(ChatFormatting.GRAY).append(morphType);
                tooltip.add(morphState);
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(IronLadders.MODID);
    }

}