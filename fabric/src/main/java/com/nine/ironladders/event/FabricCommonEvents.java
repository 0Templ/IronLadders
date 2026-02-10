package com.nine.ironladders.event;

import com.nine.ironladders.ILCommon;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public final class FabricCommonEvents {
	
	private FabricCommonEvents() {}
	
	public static void registerCommon() {
		CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> ILCommon.tagsLoadEvent());
		ServerPlayConnectionEvents.JOIN.register(
				(handler, sender, server) -> ILCommon.onPlayerLogin(handler.getPlayer()));
	}

	
}
