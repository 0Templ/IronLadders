package com.nine.ironladders.network.packet.s2c;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.common.util.LadderType;
import com.nine.ironladders.config.ConfigSyncManager;
import com.nine.ironladders.network.packet.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record ConfigSyncPacket(Map<String, Object> map) implements S2CPacket {
	
	public static final ResourceLocation ID = new ResourceLocation(ILCommon.MODID, "config_sync_packet");
	
	// Map is filled in ConfigSyncManager
	public ConfigSyncPacket(){
		this(new HashMap<>());
	}
	
	@Override
	public ResourceLocation id() {
		return ID;
	}
	
	@Override
	public void encode(FriendlyByteBuf buf) {
		ConfigSyncManager.encode(buf);
	}
	
	public static ConfigSyncPacket decode(FriendlyByteBuf buf) {
		return new ConfigSyncPacket(ConfigSyncManager.decode(buf));
	}
	
	@Override
	public void handle(PacketContext context) {
		ConfigSyncManager.SYNCED_VALUES.clear();
		LadderType.resetSpeedCache();
		ConfigSyncManager.SYNCED_VALUES.putAll(map);
	}
	
}
