package com.nine.ironladders.platform;

import com.nine.ironladders.network.ILForgeNetwork;
import com.nine.ironladders.network.packet.c2s.C2SPacket;
import com.nine.ironladders.network.packet.s2c.S2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class ForgePlatformNetworkHelper implements IPlatformNetworkHelper {
	
	@Override
	public void sendToServer(C2SPacket packet) {
		ILForgeNetwork.CHANNEL.send(packet, PacketDistributor.SERVER.noArg());
	}
	
	@Override
	public void sendToClient(ServerPlayer player, S2CPacket packet) {
		ILForgeNetwork.CHANNEL.send(packet, PacketDistributor.PLAYER.with(player));
	}
	
}
