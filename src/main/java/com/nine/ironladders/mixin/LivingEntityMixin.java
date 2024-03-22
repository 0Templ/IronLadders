package com.nine.ironladders.mixin;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow
    protected boolean jumping;
    @Inject(method = "handleOnClimbable", at = @At("TAIL"),cancellable = true)
    protected void ironladders$handleOnClimbable(Vec3 vec3, CallbackInfoReturnable ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        boolean shouldWork = !IronLadders.CONFIG.entitiesPoweredLadderBlackList.contains(livingEntity.getType().toString());
        if (!(livingEntity instanceof Player) && IronLadders.CONFIG.enableIronLaddersSpeedMultiplierForPlayersOnly){
            shouldWork = false;
        }
        if (livingEntity.onClimbable() && shouldWork && IronLadders.CONFIG.enableIronLaddersSpeedMultiplier) {
            if (livingEntity instanceof Player player && player.getAbilities().flying){
                return;
            }
            livingEntity.resetFallDistance();
            BlockState state = livingEntity.level().getBlockState(new BlockPos(livingEntity.getBlockX(), livingEntity.getBlockY(), livingEntity.getBlockZ()));
            Block block = state.getBlock();
            if (block instanceof BaseMetalLadder metalLadder) {
                double speedMultiplier = (double) metalLadder.getSpeedMultiplier() / 100;
                double yOffset;
                if (metalLadder.isPowered(state) && !(!(livingEntity instanceof Player) && IronLadders.CONFIG.enablePoweredLaddersSpeedMultiplierForPlayersOnly)){
                    if (metalLadder.isPoweredAndHasSignal(state) || livingEntity.horizontalCollision || this.jumping){
                        double b = livingEntity.horizontalCollision || this.jumping ? 0 : 0.2D;
                        yOffset = speedMultiplier+b;
                    }
                    else {
                        yOffset = -speedMultiplier;
                    }
                }
                else {
                    yOffset = livingEntity.horizontalCollision || this.jumping ? speedMultiplier : -speedMultiplier;
                }
                if (!livingEntity.isCrouching()) {
                    livingEntity.move(MoverType.SELF, new Vec3(0, yOffset, 0));
                }
            }
        }
    }
}