package com.nine.ironladders.mixin;



import com.nine.ironladders.common.block.BaseMetalLadder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
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
    protected void ironladders$handleOnClimbable(Vec3 p_21298_, CallbackInfoReturnable ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity.onClimbable()) {
            if (livingEntity instanceof  Player player && player.getAbilities().flying){
                return;
            }
            livingEntity.resetFallDistance();
            Block block = livingEntity.level().getBlockState(new BlockPos(livingEntity.getBlockX(), livingEntity.getBlockY(), livingEntity.getBlockZ())).getBlock();
            if (block instanceof BaseMetalLadder metalLadder) {
                double speedMultiplier = (double) metalLadder.getSpeedMultiplier() / 100;
                double yOffset = livingEntity.horizontalCollision || this.jumping ? speedMultiplier : -speedMultiplier;
                if (!livingEntity.isCrouching()) {
                    livingEntity.move(MoverType.SELF, new Vec3(0, yOffset, 0));
                }
            }
        }
    }


}