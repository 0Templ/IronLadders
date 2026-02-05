package com.nine.ironladders.platform;

import com.nine.ironladders.network.packet.c2s.C2SPacket;
import com.nine.ironladders.network.packet.s2c.S2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class FabricPlatformNetworkHelper implements IPlatformNetworkHelper {
	
	@Override
	public void sendToServer(C2SPacket packet) {
		FriendlyByteBuf buf = PacketByteBufs.create();
		packet.encode(buf);
		ClientPlayNetworking.send(packet.id(), buf);
	}
	
	@Override
	public void sendToClient(ServerPlayer player, S2CPacket packet) {
		FriendlyByteBuf buf = PacketByteBufs.create();
		packet.encode(buf);
		ServerPlayNetworking.send(player, packet.id(), buf);
	}
	
}
