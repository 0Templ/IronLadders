package com.nine.ironladders.network;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.network.packet.PacketContext;
import com.nine.ironladders.network.packet.PacketHolder;
import com.nine.ironladders.network.packet.c2s.C2SPacket;
import com.nine.ironladders.network.packet.s2c.S2CPacket;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ILNeoForgeNetwork {

	public static void init(RegisterPayloadHandlersEvent event) {
		ILNetwork.initServer();
		ILNetwork.initClient();
		PayloadRegistrar registrar = event.registrar(String.valueOf(ILCommon.NETWORK_PROTOCOL_VERSION));
		for (var holder : ILNetwork.C2S_PACKETS) {
			registerC2SPacket(registrar, holder);
		}
		for (var holder : ILNetwork.S2C_PACKETS) {
			registerS2CPacket(registrar, holder);
		}
	}

	private static <T extends C2SPacket> void registerC2SPacket(PayloadRegistrar registrar, PacketHolder<T> holder) {
		registrar.playToServer(holder.id(), holder.codec(), (packet, context) -> packet.handle(new PacketContext(context.player())));
	}

	private static <T extends S2CPacket> void registerS2CPacket(PayloadRegistrar registrar, PacketHolder<T> holder) {
		registrar.playToClient(holder.id(), holder.codec(), (packet, context) -> packet.handle(new PacketContext(context.player())));
	}
}
