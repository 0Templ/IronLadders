package com.nine.ironladders.common;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.entity.MetalLadderEntity;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;


@EventBusSubscriber(modid = IronLadders.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void onItemRightClick(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack stack = event.getItemStack();
        Level level = event.getLevel();
        Player player = event.getEntity();
        if (event.getHand() == InteractionHand.MAIN_HAND && stack.getItem() instanceof MorphUpgradeItem morphUpgradeItem && !player.getCooldowns().isOnCooldown(morphUpgradeItem) && player.isShiftKeyDown()) {
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();
            if (block instanceof BaseMetalLadder baseMetalLadder) {
                MetalLadderEntity entity = (MetalLadderEntity) level.getBlockEntity(pos);
                if (entity != null) {
                    player.getCooldowns().addCooldown(morphUpgradeItem, 10);
                    if (morphUpgradeItem.morphSingleBlock(level, pos, stack)) {
                        level.playSound(null, pos, SoundEvents.LADDER_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                    morphUpgradeItem.morphMultipleLadders(entity, level, pos, state, stack);
                    baseMetalLadder.updateChain(level, pos);
                }
                event.setCanceled(true);
            }
        }
    }
}
