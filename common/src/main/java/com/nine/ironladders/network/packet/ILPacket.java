package com.nine.ironladders.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface ILPacket extends CustomPacketPayload {

	void encode(FriendlyByteBuf buf);

	void handle(PacketContext context);
}
