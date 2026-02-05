package com.nine.ironladders.mixin.feature.movement.common;

import com.nine.ironladders.common.util.PlayerImpulseInputProvider;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerImpulseInputProvider {
	
	@Unique
	private boolean il$forwardImpulse;
	
	@Override
	public boolean il$forwardImpulse() {
		return this.il$forwardImpulse;
	}
	
	@Override
	public void il$setForwardImpulse(Boolean data) {
		this.il$forwardImpulse = data;
	}
}