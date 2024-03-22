
package com.nine.ironladders.common;

import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;


public class CommonEvents {

    public static void init() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof BaseMetalLadder && player.isShiftKeyDown()) {
                ItemStack stack = player.getMainHandItem();
                if (stack.getItem() instanceof MorphUpgradeItem morphUpgradeItem && !player.getCooldowns().isOnCooldown(morphUpgradeItem)) {
                    player.getCooldowns().addCooldown(morphUpgradeItem,10);
                    morphUpgradeItem.morphSingleBlock(state, world, pos, stack);
                    morphUpgradeItem.morphMultipleLadders(world, pos, state, stack);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.FAIL;
            }
            return InteractionResult.SUCCESS;
        });
    }
}

