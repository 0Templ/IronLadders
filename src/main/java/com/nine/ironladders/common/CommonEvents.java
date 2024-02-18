package com.nine.ironladders.common;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.WeatheringLadder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
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
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, event.getPos(), event.getHeldItemStack());
                }
            }
            else if (event.getToolAction().equals(ToolActions.AXE_SCRAPE)) {
                WeatheringLadder.getPrevious(blockState).ifPresent(event::setFinalState);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, event.getPos(), event.getHeldItemStack());
                }
            }
        }
    }
}
