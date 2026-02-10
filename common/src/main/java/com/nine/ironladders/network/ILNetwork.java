package com.nine.ironladders.network;

import com.nine.ironladders.network.packet.PacketHolder;
import com.nine.ironladders.network.packet.c2s.C2SPacket;
import com.nine.ironladders.network.packet.c2s.ModelTypePacket;
import com.nine.ironladders.network.packet.c2s.PlayerImpulseInputPacket;
import com.nine.ironladders.network.packet.s2c.ConfigSyncPacket;
import com.nine.ironladders.network.packet.s2c.S2CPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.ArrayList;
import java.util.List;

public class ILNetwork {
	
	public static final List<PacketHolder<? extends C2SPacket>> C2S_PACKETS = new ArrayList<>();
	
	public static final List<PacketHolder<? extends S2CPacket>> S2C_PACKETS = new ArrayList<>();
	
	private static boolean c2sInitialized = false;
	
	private static boolean s2cInitialized = false;
	
	public static void initServer() {
		if (c2sInitialized) {
			return;
		}
		setupC2SPackets();
		c2sInitialized = true;
	}
	
	public static void initClient() {
		if (s2cInitialized) {
			return;
		}
		setupS2CPackets();
		s2cInitialized = true;
	}
	
	private static void setupC2SPackets(){
		addC2SPacket(
				PlayerImpulseInputPacket.class,
				PlayerImpulseInputPacket.ID,
				PlayerImpulseInputPacket.CODEC
		);
		
		addC2SPacket(
				ModelTypePacket.class,
				ModelTypePacket.ID,
				ModelTypePacket.CODEC
		);
		
		
	}
	
	private static void setupS2CPackets(){
		addS2CPacket(
				ConfigSyncPacket.class,
				ConfigSyncPacket.ID,
				ConfigSyncPacket.CODEC
		);
		
	}
	
	
	public static <T extends C2SPacket> void addC2SPacket(Class<T> clazz, CustomPacketPayload.Type<T> id, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
		C2S_PACKETS.add(new PacketHolder<>(clazz, id, codec));
	}
	
	public static <T extends S2CPacket> void addS2CPacket(Class<T> clazz, CustomPacketPayload.Type<T> id, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
		S2C_PACKETS.add(new PacketHolder<>(clazz, id, codec));
	}
	

}
