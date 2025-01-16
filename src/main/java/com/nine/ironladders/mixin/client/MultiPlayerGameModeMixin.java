package com.nine.ironladders.mixin.client;

import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @Inject(
            method = "destroyBlock",
            at = @At(value = "HEAD"
            ),
            cancellable = true
    )
    public void destroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Player player = Minecraft.getInstance().player;
        if (
                player != null &&
                        player.isShiftKeyDown() &&
                        player.getMainHandItem().getItem() instanceof MorphUpgradeItem &&
                        player.level().getBlockState(pos).getBlock() instanceof BaseMetalLadder
        ) {
            if (!player.getCooldowns().isOnCooldown(player.getMainHandItem())) {
                multipleMorph(player, pos);
            }
            cir.setReturnValue(false);
        }
    }

    @Unique
    private void multipleMorph(Player player, BlockPos loc) {
        if (player != null && player.isShiftKeyDown()) {
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof MorphUpgradeItem morphUpgradeItem && !player.getCooldowns().isOnCooldown(stack)) {
                morphUpgradeItem.morphMultipleLadders(player, stack, Minecraft.getInstance().level, loc);
            }
        }
    }
}