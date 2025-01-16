/*
package com.nine.ironladders.compat.top;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.IronLadders;
import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.entity.MetalLadderEntity;
import com.nine.ironladders.common.item.CustomUpgradeItem;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import com.nine.ironladders.common.item.UpgradeItem;
import com.nine.ironladders.common.utils.LadderType;
import mcjty.theoneprobe.api.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class TOPBlockComponentProvider implements IProbeInfoProvider {

    @Override
    public ResourceLocation getID() {
        return ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, "block_info");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo info, Player player, Level level, BlockState state, IProbeHitData data) {
        ItemStack stack = player.getMainHandItem();
        Block block = state.getBlock();
        if (block instanceof LadderBlock && ILConfig.topIntegration.get()) {
            if (block instanceof BaseMetalLadder baseMetalLadder) {
                if (stack.getItem() instanceof CustomUpgradeItem) {
                    Component component = Component.translatable("ironladders.waila.multiple_use").withStyle(ChatFormatting.GRAY);
                    info.text((CompoundText.create().style(TextStyleClass.LABEL).text(component)));
                }
                if (baseMetalLadder.isPowered(state)) {
                    Component onOff = baseMetalLadder.isPoweredAndHasSignal(state) ?
                            Component.translatable("ironladders.waila.power_state_on").withStyle(ChatFormatting.GREEN) :
                            Component.translatable("ironladders.waila.power_state_off").withStyle(ChatFormatting.RED);
                    info.text(CompoundText.create().style(TextStyleClass.LABEL)
                            .text(Component.translatable("ironladders.waila.power_state"))
                            .style(baseMetalLadder.isPoweredAndHasSignal(state) ? TextStyleClass.OK : TextStyleClass.ERROR).text(onOff));
                }
                if (stack.getItem() instanceof MorphUpgradeItem && level.getBlockEntity(data.getPos()) instanceof MetalLadderEntity ladderEntity && ladderEntity.getMorphState() != null) {
                    Component namespace = ClientHelper.componentWithColor(MorphUpgradeItem.getMorphTypeMod(stack), 0x808080);
                    Component morphContent = MorphUpgradeItem.getMorphContent(stack);
                    if (!Objects.equals(MorphUpgradeItem.getMorphType(stack), "")) {
                        info.text((CompoundText.create().style(TextStyleClass.LABEL).text(Component.translatable("ironladders.waila.morph_type", namespace, morphContent).withStyle(ChatFormatting.GRAY))));
                    }
                }
            }
            if (stack.getItem() instanceof UpgradeItem upgradeItem && stack.getDamageValue() != stack.getMaxDamage() - 1) {
                if (block instanceof BaseMetalLadder baseMetalLadder && baseMetalLadder.getType() == upgradeItem.getType().getPreviousType()
                        || (LadderType.DEFAULT == upgradeItem.getType().getPreviousType() && block == Blocks.LADDER)) {
                    Component component = Component.translatable("ironladders.waila.multiple_use").withStyle(ChatFormatting.GRAY);
                    info.text((CompoundText.create().style(TextStyleClass.LABEL).text(component)));
                }
            }
        }
    }
}*/
