package com.nine.ironladders;

import com.nine.ironladders.client.tab.TabCopperIconPart;
import com.nine.ironladders.client.tab.TabIcon;
import com.nine.ironladders.client.tab.TabIconPart;
import com.nine.ironladders.common.util.LadderType;
import com.nine.ironladders.config.ConfigSyncManager;
import net.minecraft.client.player.LocalPlayer;

import javax.swing.plaf.basic.BasicButtonUI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ILClient {
	
	public static final String ALT_BLOCK_STATES_DIR = "blockstates_custom";
	
	public static void onPlayerLogout(LocalPlayer player) {
		ConfigSyncManager.SYNCED_VALUES.clear();
		LadderType.resetSpeedCache();
	}
}
