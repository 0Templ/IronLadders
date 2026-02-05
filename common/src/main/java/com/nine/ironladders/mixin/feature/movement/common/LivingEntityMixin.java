package com.nine.ironladders.mixin.feature.movement.common;

import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.util.PlayerImpulseInputProvider;
import com.nine.ironladders.common.util.PlayerImpulseInputState;
import com.nine.ironladders.config.ILConfig;
import com.nine.ironladders.network.packet.c2s.PlayerImpulseInputPacket;
import com.nine.ironladders.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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
	
	
	@Inject(method = "handleRelativeFrictionAndCalculateMovement",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V",
					shift = At.Shift.AFTER))
	protected void il$handleRelativeFrictionAndCalculateMovement(
			Vec3 deltaMovement,
			float friction,
			CallbackInfoReturnable<Vec3> cir) {
		LivingEntity entity = (LivingEntity) (Object) this;
		
		if (ILConfig.GLOBAL_SPEED_MULTIPLIER.get() == 0 || entity.isCrouching() || !entity.onClimbable()) return;
		
		BlockState state = entity.level().getBlockState(new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ()));
		if (!(state.getBlock() instanceof MetalLadderBlock ladder)) return;
		
		boolean isPlayer = entity instanceof Player;
		if (isPlayer && ((Player) entity).getAbilities().flying) return;
		if (!isPlayer && ILConfig.LADDERS_SPEED_MULTIPLYING_ONLY_FOR_PLAYERS.get()) return;
		
		entity.resetFallDistance();
		
		double baseSpeed = ladder.getSpeedMultiplier() * ILConfig.GLOBAL_SPEED_MULTIPLIER.get();
		double yOffset = il$calculateScale(entity, baseSpeed);
		
		if (yOffset != 0){
			entity.move(MoverType.SELF, new Vec3(0, yOffset, 0));
		}
	}
	
	@Unique
	private double il$calculateScale(LivingEntity entity, double baseSpeed) {
		if (!(entity.horizontalCollision || this.jumping)) {
			return -baseSpeed;
		} else {
			if (entity instanceof Player player) {
				if (il$forwardImpulse(player)) {
					return il$applyNarrowMultiplying(player, baseSpeed);
				}
			}
			return baseSpeed;
		}
	}
	
	@Unique
	private static boolean il$forwardImpulse(Player player){
		PlayerImpulseInputProvider provider = (PlayerImpulseInputProvider) player;
		boolean forwardImpulse;
		if (player.level().isClientSide) {
			forwardImpulse = PlayerImpulseInputState.forwardImpulse(player);
			if (provider.il$forwardImpulse() != forwardImpulse) {
				provider.il$setForwardImpulse(forwardImpulse);
				Platform.NETWORK.sendToServer(new PlayerImpulseInputPacket(forwardImpulse));
			}
		} else {
			forwardImpulse = provider.il$forwardImpulse();
		}
		return forwardImpulse;
	}
	
	@Unique
	private static double il$applyNarrowMultiplying(Player player, double speed) {
		if (!ILConfig.ENABLE_NARROW_OPENING_DETECTION.get()) {
			return speed;
		}
		BlockPos basePos = player.blockPosition().relative(player.getDirection());
		double currentY = player.getY();
		int blocksToCheck = (int) Math.floor(currentY + speed) - (int) Math.floor(currentY);
		
		Level level = player.level();
		if (blocksToCheck >= 1){
			for (int i = 0; i <= blocksToCheck; i++) {
				BlockPos pos = basePos.above(i);
				
				// [solid - air - air]
				if (!level.getBlockState(pos).isAir()
						&& level.getBlockState(pos.above()).isAir()
						&& level.getBlockState(pos.above(2)).isAir()
				)
				{
					int openingY = pos.getY() + 1;
					double target = currentY + speed;
					double diff = target - (openingY);
					if (diff > 0){
						speed -= diff;
					}
					break;
				}
			}
		}
		return speed;
	}
}
