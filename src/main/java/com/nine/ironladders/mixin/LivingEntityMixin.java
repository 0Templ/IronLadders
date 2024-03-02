package com.nine.ironladders.mixin;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow
    protected boolean jumping;
    @Inject(method = "applyClimbingSpeed", at = @At("TAIL"),cancellable = true)
    protected void ironladders$applyClimbingSpeed(Vec3d vec3, CallbackInfoReturnable ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        
        boolean shouldWork = !IronLadders.CONFIG.entitiesPoweredLadderBlackList.contains(livingEntity.getType().toString());
        if (!(livingEntity instanceof PlayerEntity) && IronLadders.CONFIG.enableIronLaddersSpeedMultiplierForPlayersOnly){
            shouldWork = false;
        }
        if (livingEntity.isClimbing() && shouldWork && IronLadders.CONFIG.enableIronLaddersSpeedMultiplier) {
            if (livingEntity instanceof PlayerEntity player && player.getAbilities().flying){
                return;
            }
            livingEntity.fallDistance = 0;
            BlockState state = livingEntity.getWorld().getBlockState(new BlockPos(livingEntity.getBlockX(), livingEntity.getBlockY(), livingEntity.getBlockZ()));
            Block block = state.getBlock();
            if (block instanceof BaseMetalLadder metalLadder) {
                double speedMultiplier = (double) metalLadder.getSpeedMultiplier() / 100;
                double yOffset;
                if (metalLadder.isPowered(state) && !(!(livingEntity instanceof PlayerEntity) && IronLadders.CONFIG.enablePoweredLaddersSpeedMultiplierForPlayersOnly)){
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
                if (!livingEntity.isSneaking()) {
                    livingEntity.move(MovementType.SELF, new Vec3d(0, yOffset, 0));
                }
            }
        }
    }
}