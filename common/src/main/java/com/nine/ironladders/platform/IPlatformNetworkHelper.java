package com.nine.ironladders.platform;

import com.nine.ironladders.network.packet.c2s.C2SPacket;
import com.nine.ironladders.network.packet.s2c.S2CPacket;
import net.minecraft.server.level.ServerPlayer;

public interface IPlatformNetworkHelper {
	
	void sendToServer(C2SPacket packet);
	
	void sendToClient(ServerPlayer player, S2CPacket packet);
	
	
}
