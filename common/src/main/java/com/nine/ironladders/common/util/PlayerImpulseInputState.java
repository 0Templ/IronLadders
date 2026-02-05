package com.nine.ironladders.common.util;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

public final class PlayerImpulseInputState {
	
	public static boolean forwardImpulse(Player player){
		return ((LocalPlayer) player).input.forwardImpulse > 0;
	}
	
	
}
