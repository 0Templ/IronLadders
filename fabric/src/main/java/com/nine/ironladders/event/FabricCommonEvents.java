package com.nine.ironladders.event;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.common.recipe.ILRecipeCache;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.RegistryAccess;

public final class FabricCommonEvents {
	
	private FabricCommonEvents() {}
	
	public static void registerCommon() {
		CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> ILCommon.tagsLoadEvent());
		ServerPlayConnectionEvents.JOIN.register(
				(handler, sender, server) -> ILCommon.onPlayerLogin(handler.getPlayer()));
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			RegistryAccess access = server.registryAccess();
			ILRecipeCache.onResourcesReload(server.getRecipeManager(), access);
		});
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
			if (success) {
				RegistryAccess access = server.registryAccess();
				ILRecipeCache.onResourcesReload(server.getRecipeManager(), access);
			}
		});
	}

	
}
