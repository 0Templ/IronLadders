package com.nine.ironladders.platform;

import com.nine.ironladders.platform.util.LoaderTarget;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public LoaderTarget currentLoader() {
		return LoaderTarget.FABRIC;
	}
	
	@Override
	public String getModName(String modId) {
		return FabricLoader.getInstance().getModContainer(modId)
				.map(mod -> mod.getMetadata().getName())
				.orElse(modId);
	}

	@Override
	public String getModVersion(String modId) {
		return FabricLoader.getInstance().getModContainer(modId)
				.map(mod -> mod.getMetadata().getVersion().getFriendlyString())
				.orElse("unknown");
	}

	@Override
	public File getConfigPath(String id) {
		return FabricLoader.getInstance()
				.getConfigDir()
				.resolve(id + ".toml")
				.toFile();
	}
	
}
