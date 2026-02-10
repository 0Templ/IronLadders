package com.nine.ironladders.network;

import com.nine.ironladders.network.packet.PacketContext;
import com.nine.ironladders.network.packet.PacketHolder;
import com.nine.ironladders.network.packet.c2s.C2SPacket;
import com.nine.ironladders.network.packet.s2c.S2CPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ILFabricNetwork {
	
	public static void initServer() {
		ILNetwork.initServer();
		ILNetwork.initClient();
		for (var holder : ILNetwork.C2S_PACKETS) {
			registerC2SPacket(holder);
		}
		for (var holder : ILNetwork.S2C_PACKETS) {
			registerS2CPacketType(holder);
		}

	}
	
	public static <T extends C2SPacket> void registerC2SPacket(PacketHolder<T> holder) {
		PayloadTypeRegistry.playC2S().register(holder.id(), holder.codec());
		ServerPlayNetworking.registerGlobalReceiver(holder.id(), (packet, ctx) ->
				ctx.server().execute(() -> {
					if (ctx.player() != null) {
						packet.handle(new PacketContext(ctx.player()));
					}
				})
		);
	}

	private static <T extends S2CPacket> void registerS2CPacketType(PacketHolder<T> holder) {
		PayloadTypeRegistry.playS2C().register(holder.id(), holder.codec());
	}
}
