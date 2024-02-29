package com.nine.ironladders.common;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IronLadders.MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void onItemRightClick(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack stack = event.getItemStack();
        Level level = event.getLevel();
        Player player = event.getEntity();
        if (event.getHand() == InteractionHand.MAIN_HAND && stack.getItem() instanceof MorphUpgradeItem morphUpgradeItem ) {
            BlockPos pos = event.getPos();
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();
            if (block instanceof BaseMetalLadder) {
                if (player.isShiftKeyDown() ) {
                    morphUpgradeItem.morphSingleBlock(state,level,pos,stack);
                    morphUpgradeItem.morphMultipleLadders(level,pos,state,stack);
                }
            }
            event.setCanceled(true);
        }
    }
}
