package com.nine.ironladders.compat.modmenu;

import com.nine.ironladders.ILCommon;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.api.UpdateChecker;

import java.util.Map;

public class ModMenuSetup implements ModMenuApi {
	
	@Override
	public Map<String, UpdateChecker> getProvidedUpdateCheckers() {
		return java.util.Map.of(ILCommon.MODID, new ILUpdateChecker());
	}

}