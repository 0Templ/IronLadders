package com.nine.ironladders.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface ILPacket {

	ResourceLocation id();
	
	void encode(FriendlyByteBuf buf);
}
