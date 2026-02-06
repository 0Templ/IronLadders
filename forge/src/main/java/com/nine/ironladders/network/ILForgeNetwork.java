package com.nine.ironladders.network;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.network.packet.PacketContext;
import com.nine.ironladders.network.packet.PacketHolder;
import com.nine.ironladders.network.packet.c2s.C2SPacket;
import com.nine.ironladders.network.packet.s2c.S2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ILForgeNetwork {
	
	private static final String PROTOCOL_VERSION = String.valueOf(ILCommon.NETWORK_PROTOCOL_VERSION);
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, "network"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);
	
	public static void init() {
		ILNetwork.initServer();
		ILNetwork.initClient();
		int packetId = 0;
		for (var holder : ILNetwork.C2S_PACKETS) {
			registerC2SPacket(packetId++, holder);
		}
		for (var holder : ILNetwork.S2C_PACKETS) {
			registerS2CPacket(packetId++, holder);
		}
	}
	
	public static <T extends C2SPacket> void registerC2SPacket(int index, PacketHolder<T> holder) {
		CHANNEL.registerMessage(
				index,
				holder.clazz(),
				holder.encoder(),
				holder.decoder(),
				(packet, contextSupplier) -> {
					var ctx = contextSupplier.get();
					ctx.enqueueWork(() -> holder.handler().accept(packet, new PacketContext(ctx.getSender())));
				});
	}
	
	public static <T extends S2CPacket> void registerS2CPacket(int index, PacketHolder<T> holder) {
		CHANNEL.registerMessage(
				index,
				holder.clazz(),
				holder.encoder(),
				holder.decoder(),
				(packet, contextSupplier) -> {
					var ctx = contextSupplier.get();
					ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
						holder.handler().accept(packet, new PacketContext(ClientHelper.player()));
					}));
				});
	}
	
}
