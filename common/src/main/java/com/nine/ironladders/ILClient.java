package com.nine.ironladders;

import com.nine.ironladders.common.util.LadderType;
import com.nine.ironladders.config.ConfigSyncManager;
import net.minecraft.client.player.LocalPlayer;

public class ILClient {
	
	public static final String ALT_BLOCK_STATES_DIR = "blockstates_custom";
	
	public static void onPlayerLogout(LocalPlayer player) {
		ConfigSyncManager.SYNCED_VALUES.clear();
		LadderType.resetSpeedCache();
	}
}
