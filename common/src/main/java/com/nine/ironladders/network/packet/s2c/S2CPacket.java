package com.nine.ironladders.network.packet.s2c;

import com.nine.ironladders.network.packet.ILPacket;
import com.nine.ironladders.network.packet.PacketContext;

public interface S2CPacket extends ILPacket {

	void handle(PacketContext context);
	
}
