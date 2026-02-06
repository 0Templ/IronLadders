package com.nine.ironladders.network;

import com.nine.ironladders.network.packet.PacketContext;
import com.nine.ironladders.network.packet.PacketHolder;
import com.nine.ironladders.network.packet.s2c.S2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class ILFabricClientNetwork {


	public static void initClient() {
		ILNetwork.initClient();
		for (var holder : ILNetwork.S2C_PACKETS) {
			registerS2CPacket(holder);
		}
	}

	private static <T extends S2CPacket> void registerS2CPacket(PacketHolder<T> holder) {
		ClientPlayNetworking.registerGlobalReceiver(
				holder.id(),
				(client, handler, buf, responseSender) -> {
					var packet = holder.decoder().apply(buf);
					client.execute(() -> holder.handler().accept(packet, new PacketContext(client.player)));
				}
		);
	}
}
