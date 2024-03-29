package com.nine.ironladders.common;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.WeatheringLadder;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IronLadders.MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void axeInteractEvent(BlockEvent.BlockToolModificationEvent event) {
        Block block = event.getState().getBlock();
        BlockState blockState = event.getState();
        Player player = event.getPlayer();
        if (block instanceof WeatheringLadder) {
            if (event.getToolAction().equals(ToolActions.AXE_WAX_OFF)) {
                WeatheringLadder.getUnWaxed(blockState).ifPresent(event::setFinalState);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, event.getPos(), event.getHeldItemStack());
                }
            } else if (event.getToolAction().equals(ToolActions.AXE_SCRAPE)) {
                WeatheringLadder.getPrevious(blockState).ifPresent(event::setFinalState);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, event.getPos(), event.getHeldItemStack());
                }
            }
        }
    }
    @SubscribeEvent
    public static void onItemRightClick(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack stack = event.getItemStack();
        Level level = event.getLevel();
        Player player = event.getEntity();
        if (event.getHand() == InteractionHand.MAIN_HAND && stack.getItem() instanceof MorphUpgradeItem morphUpgradeItem ) {
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();
            if (block instanceof BaseMetalLadder baseMetalLadder) {
                if (player.isShiftKeyDown() ) {
                    morphUpgradeItem.morphSingleBlock(state,level,pos,stack);
                    morphUpgradeItem.morphMultipleLadders(level,pos,state,stack);
                    baseMetalLadder.updateChain(level,pos);
                }
            }
            event.setCanceled(true);
        }
    }
}
