package com.nine.ironladders.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Function;

public record PacketHolder<T extends ILPacket>(
		Class<T> clazz,
		ResourceLocation id,
		Function<FriendlyByteBuf, T> decoder,
		BiConsumer<T, FriendlyByteBuf> encoder,
		BiConsumer<T, PacketContext> handler
) {
}
