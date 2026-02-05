package com.nine.ironladders.network.packet;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PacketContext {

	private final Player player;

	public PacketContext(Player player) {
		this.player = player;
	}

	public Player player() {
		return player;
	}

	public Level level() {
		return player.level();
	}

	public boolean isClient() {
		return level().isClientSide();
	}

	public ServerPlayer serverPlayer() {
		return player instanceof ServerPlayer server ? server : null;
	}
}
