package com.nine.ironladders;

import com.nine.ironladders.common.material.RecipeHelper;
import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.network.packet.s2c.ConfigSyncPacket;
import com.nine.ironladders.platform.Platform;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ILCommon {

	public static final String MODID = "ironladders";
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	public static final boolean IS_DATAGEN = Boolean.parseBoolean(System.getProperty("datagen"));
	
	public static final String MOD_VERSION = Platform.CORE.getModVersion(MODID);
	
	public static final int NETWORK_PROTOCOL_VERSION = 5;
	
	public static void tagsLoadEvent(){
		RecipeHelper.init();
	}
	
	public static void onPlayerLogin(ServerPlayer player) {
		Platform.NETWORK.sendToClient(player, new ConfigSyncPacket());
	}
	
}