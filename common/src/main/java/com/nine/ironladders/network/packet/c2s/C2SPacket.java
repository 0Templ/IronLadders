package com.nine.ironladders.network.packet.c2s;

import com.nine.ironladders.network.packet.ILPacket;
import com.nine.ironladders.network.packet.PacketContext;

public interface C2SPacket extends ILPacket {

	void handle(PacketContext context);
	
}
