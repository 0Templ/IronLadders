package com.nine.ironladders.compat.jade;
import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.utils.MorphType;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

public enum MetalLadderComponentProvider implements IBlockComponentProvider {

    INSTANCE;
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig pluginConfig) {
        if (blockAccessor.getBlock() instanceof BaseMetalLadder baseMetalLadder) {
            BlockState state = blockAccessor.getBlockState();
            if (baseMetalLadder.isPowered(state)){
                Text onOff = baseMetalLadder.isPoweredAndHasSignal(state) ?
                        Text.translatable("config.jade.power_state_on").formatted(Formatting.GREEN) :
                        Text.translatable("config.jade.power_state_off").formatted(Formatting.RED);
                Text powerState = Text.translatable("config.jade.power_state").formatted(Formatting.GRAY).append(onOff);
                tooltip.add(powerState);
            }
            if (baseMetalLadder.isMorphed(state)){
                Text morphType = Text.translatable("tooltip.item.upgrade." +
                        state.get(EnumProperty.of("morph", MorphType.class)).toString()).formatted(Formatting.GRAY);
                Text morphState = Text.translatable("config.jade.morph_state").formatted(Formatting.GRAY).append(morphType);
                tooltip.add(morphState);
            }
        }
    }

    @Override
    public Identifier getUid() {
        return new Identifier(IronLadders.MODID);
    }

}