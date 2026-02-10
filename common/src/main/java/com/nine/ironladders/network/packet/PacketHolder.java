package com.nine.ironladders.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record PacketHolder<T extends ILPacket>(
		Class<T> clazz,
		CustomPacketPayload.Type<T> id,
		StreamCodec<? super RegistryFriendlyByteBuf, T> codec
) {
}
