package com.nine.ironladders.network;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.network.packet.ILPacket;
import com.nine.ironladders.network.packet.PacketContext;
import com.nine.ironladders.network.packet.PacketHolder;
import com.nine.ironladders.network.packet.c2s.C2SPacket;
import com.nine.ironladders.network.packet.s2c.S2CPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class ILForgeNetwork {
	
	private static final int PROTOCOL_VERSION = ILCommon.NETWORK_PROTOCOL_VERSION;
	
	public static final SimpleChannel CHANNEL = ChannelBuilder
			.named(ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, "network"))
			.networkProtocolVersion(PROTOCOL_VERSION)
			.simpleChannel();
	
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
		CHANNEL.messageBuilder(holder.clazz(), index, NetworkDirection.PLAY_TO_SERVER)
				.codec(forgeCodec(holder))
				.consumerMainThread((packet, context) -> packet.handle(new PacketContext(context.getSender())))
				.add();
	}
	
	public static <T extends S2CPacket> void registerS2CPacket(int index, PacketHolder<T> holder) {
		CHANNEL.messageBuilder(holder.clazz(), index, NetworkDirection.PLAY_TO_CLIENT)
				.codec(forgeCodec(holder))
				.consumerMainThread((packet, context) ->
						DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
								packet.handle(new PacketContext(ClientHelper.player()))))
				.add();
	}

	@SuppressWarnings("unchecked")
	private static <T extends ILPacket> StreamCodec<RegistryFriendlyByteBuf, T> forgeCodec(PacketHolder<T> holder) {
		return (StreamCodec<RegistryFriendlyByteBuf, T>) holder.codec();
	}
	
}
