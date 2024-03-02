/*
package com.nine.ironladders.common;

import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.impl.event.interaction.InteractionEventsRouter;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class CommonEvents {

    public static void init() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            System.out.println(world);

            if (hand != Hand.MAIN_HAND || world.isClient) {
                return ActionResult.PASS;
            }
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof BaseMetalLadder && player.isSneaking()) {
                ItemStack stack = player.getStackInHand(hand);
                if (stack.getItem() instanceof MorphUpgradeItem morphUpgradeItem) {
                    morphUpgradeItem.morphSingleBlock(state, world, pos, stack);
                    morphUpgradeItem.morphMultipleLadders(world, pos, state, stack);
                }
                return ActionResult.FAIL;
            }
            return ActionResult.FAIL;
        });
    }
}
*/
