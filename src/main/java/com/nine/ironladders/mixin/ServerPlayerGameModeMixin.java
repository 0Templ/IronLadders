package com.nine.ironladders.mixin;

import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Shadow @Final protected ServerPlayer player;

    @Shadow protected ServerLevel level;

    @Inject(method = "handleBlockBreakAction", at = @At("HEAD"), cancellable = true)
    public void ironladders$handleBlockBreakAction(BlockPos pos, ServerboundPlayerActionPacket.Action action, Direction face, int maxBuildHeight, int sequence, CallbackInfo ci) {
        ItemStack stack = player.getMainHandItem();
        if (action == ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK &&
                stack.getItem() instanceof MorphUpgradeItem morphUpgradeItem &&
                player.isShiftKeyDown() &&
                level.getBlockState(pos).getBlock() instanceof BaseMetalLadder
        ){
            if (!player.getCooldowns().isOnCooldown(morphUpgradeItem)) {
                morphUpgradeItem.morphMultipleLadders(player, stack, level, pos);
            }
            if (player.isCreative()){
                ci.cancel();
            }
        }
    }
}
