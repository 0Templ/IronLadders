package com.nine.ironladders.network;

import com.nine.ironladders.network.packet.PacketContext;
import com.nine.ironladders.network.packet.PacketHolder;
import com.nine.ironladders.network.packet.c2s.C2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ILFabricNetwork {
	
	public static void initServer() {
		ILNetwork.initServer();
		for (var holder : ILNetwork.C2S_PACKETS) {
			registerC2SPacket(holder);
		}

	}
	
	public static <T extends C2SPacket> void registerC2SPacket(PacketHolder<T> holder) {
		ServerPlayNetworking.registerGlobalReceiver(holder.id(),
				(server, player, handler, buf, responseSender) ->
				{
					var packet = holder.decoder().apply(buf);
					server.execute(()-> {
						holder.handler().accept(packet, new PacketContext(player));
					});
				
				});
	}
}
