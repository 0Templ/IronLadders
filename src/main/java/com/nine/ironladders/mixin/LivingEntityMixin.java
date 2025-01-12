package com.nine.ironladders.mixin;


import com.nine.ironladders.ILConfig;
import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.utils.PlayerInputDataProvider;
import com.nine.ironladders.network.SyncPlayerInputData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow
    protected boolean jumping;

    @Inject(method = "handleOnClimbable", at = @At("TAIL"))
    protected void ironladders$handleOnClimbable(Vec3 vec3, CallbackInfoReturnable ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        boolean shouldWork = !ILConfig.entitiesPoweredLadderBlackList.get().contains(livingEntity.getType().toString());
        if (!(livingEntity instanceof Player) && ILConfig.enableIronLaddersSpeedMultiplierForPlayersOnly.get()) {
            shouldWork = false;
        }
        if (livingEntity.onClimbable() && shouldWork && ILConfig.enableIronLaddersSpeedMultiplier.get()) {
            if (livingEntity instanceof Player player && player.getAbilities().flying) {
                return;
            }
            livingEntity.resetFallDistance();
            BlockState state = livingEntity.level().getBlockState(new BlockPos(livingEntity.getBlockX(), livingEntity.getBlockY(), livingEntity.getBlockZ()));
            Block block = state.getBlock();
            float m = Math.abs(livingEntity.getXRot() / 80);
            if (block instanceof BaseMetalLadder metalLadder) {
                if (checkPlayerDirection(state, livingEntity)) {
                    m = 1;
                }
                double speedMultiplier = (double) metalLadder.getSpeedMultiplier() / 100;
                if (livingEntity instanceof Player player) {
                    boolean playerGoForward;
                    if (player.level().isClientSide) {
                        playerGoForward = ClientHelper.checkGoForward(player);
                        PacketDistributor.sendToServer(new SyncPlayerInputData(playerGoForward));
                    } else {
                        playerGoForward = ((PlayerInputDataProvider) player).forwardImpulse();
                    }
                    speedMultiplier = playerGoForward && m < 0.3 ? 0 : speedMultiplier;
                }
                double yOffset;
                if (metalLadder.isPowered(state) && !(!(livingEntity instanceof Player) && ILConfig.enablePoweredLaddersSpeedMultiplierForPlayersOnly.get())) {
                    if (metalLadder.isPoweredAndHasSignal(state) || livingEntity.horizontalCollision || this.jumping) {
                        double b = livingEntity.horizontalCollision || this.jumping ? 0 : 0.2D;
                        yOffset = speedMultiplier + b;
                    } else {
                        yOffset = livingEntity.horizontalCollision || this.jumping ? -speedMultiplier : -speedMultiplier * m * 0.5D;
                    }
                } else {
                    yOffset = livingEntity.horizontalCollision || this.jumping ? speedMultiplier : -speedMultiplier * m * 0.5D;
                }
                if (!livingEntity.isCrouching()) {
                    livingEntity.move(MoverType.SELF, new Vec3(0, yOffset, 0));
                }
            }
        }
    }

    @Unique
    private static boolean checkPlayerDirection(BlockState state, LivingEntity player) {
        Direction blockDirection = state.getValue(LadderBlock.FACING);
        Direction playerDirection = player.getDirection().getOpposite();
        return playerDirection == blockDirection ||
                playerDirection == blockDirection.getClockWise() ||
                playerDirection == blockDirection.getCounterClockWise();
    }
}