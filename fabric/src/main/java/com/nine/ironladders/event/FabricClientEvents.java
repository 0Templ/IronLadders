package com.nine.ironladders.event;

import com.nine.ironladders.ILClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

@Environment(EnvType.CLIENT)
public final class FabricClientEvents {
	
	private FabricClientEvents() {}
	
	public static void registerClient() {
		ClientPlayConnectionEvents.DISCONNECT.register(
				(listener, minecraft) -> ILClient.onPlayerLogout(minecraft.player));
		
	}
	
}
