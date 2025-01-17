package com.nine.ironladders.mixin.client;

import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @Inject(method = "destroyBlock", at = @At(value = "HEAD"), cancellable = true)
    public void ironladders$destroyBlock(BlockPos loc, CallbackInfoReturnable<Boolean> cir) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack stack = player.getMainHandItem();
        if (player.isShiftKeyDown() &&
                stack.getItem() instanceof MorphUpgradeItem morphUpgradeItem &&
                player.level().getBlockState(loc).getBlock() instanceof BaseMetalLadder &&
                player.isCreative()
        ) {
            if (!player.getCooldowns().isOnCooldown(player.getMainHandItem())) {
                morphUpgradeItem.morphMultipleLadders(player, stack, Minecraft.getInstance().level, loc);
            }
            cir.setReturnValue(false);
        }
    }
}