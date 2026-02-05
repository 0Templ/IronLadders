package com.nine.ironladders.network;

import com.nine.ironladders.network.packet.PacketHolder;
import com.nine.ironladders.network.packet.PacketContext;
import com.nine.ironladders.network.packet.c2s.C2SPacket;
import com.nine.ironladders.network.packet.c2s.ModelTypePacket;
import com.nine.ironladders.network.packet.c2s.PlayerImpulseInputPacket;
import com.nine.ironladders.network.packet.s2c.ConfigSyncPacket;
import com.nine.ironladders.network.packet.s2c.S2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ILNetwork {
	
	public static final List<PacketHolder<? extends C2SPacket>> C2S_PACKETS = new ArrayList<>();
	
	public static final List<PacketHolder<? extends S2CPacket>> S2C_PACKETS = new ArrayList<>();
	
	public static void initServer() {
		setupC2SPackets();
	}
	
	public static void initClient() {
		setupS2CPackets();
	}
	
	private static void setupC2SPackets(){
		addC2SPacket(
				PlayerImpulseInputPacket.class,
				PlayerImpulseInputPacket.ID,
				PlayerImpulseInputPacket::decode,
				PlayerImpulseInputPacket::encode,
				PlayerImpulseInputPacket::handle
		);
		addC2SPacket(
				ModelTypePacket.class,
				ModelTypePacket.ID,
				ModelTypePacket::decode,
				ModelTypePacket::encode,
				ModelTypePacket::handle
		);
		
		
	}
	
	private static void setupS2CPackets(){
		addS2CPacket(
				ConfigSyncPacket.class,
				ConfigSyncPacket.ID,
				ConfigSyncPacket::decode,
				ConfigSyncPacket::encode,
				ConfigSyncPacket::handle
		);
		
	}
	
	
	public static <T extends S2CPacket> void addS2CPacket(
			Class<T> clazz,
			ResourceLocation id,
			Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, FriendlyByteBuf> encoder,
			BiConsumer<T, PacketContext> handler)
	{
		S2C_PACKETS.add(new PacketHolder<>(clazz, id, decoder, encoder, handler));
	}
	
	public static <T extends C2SPacket> void addC2SPacket(
			Class<T> clazz,
			ResourceLocation id,
			Function<FriendlyByteBuf, T> decoder,
			BiConsumer<T, FriendlyByteBuf> encoder,
			BiConsumer<T, PacketContext> handler)
	{
		C2S_PACKETS.add(new PacketHolder<>(clazz, id, decoder, encoder, handler));
	}
	

}

