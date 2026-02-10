package com.nine.ironladders.platform;

import com.nine.ironladders.platform.util.LoaderTarget;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public LoaderTarget currentLoader() {
		return LoaderTarget.NEOFORGE;
	}

	@Override
	public String getModName(String modId) {
		return ModList.get().getModContainerById(modId)
				.map(container -> container.getModInfo().getDisplayName())
				.orElse(modId);
	}

	@Override
	public String getModVersion(String modId) {
		return ModList.get().getModContainerById(modId)
				.map(container -> container.getModInfo().getVersion().toString())
				.orElse("unknown");
	}

	@Override
	public File getConfigPath(String id) {
		return FMLPaths.CONFIGDIR.get().resolve(id + ".toml").toFile();
	}
}
