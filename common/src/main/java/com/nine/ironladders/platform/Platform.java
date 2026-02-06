package com.nine.ironladders.platform;

import java.util.ServiceLoader;

public class Platform {
	
	public static final IPlatformHelper CORE = load(IPlatformHelper.class);
	
	public static final IPlatformRegistryHelper REGISTRY = load(IPlatformRegistryHelper.class);
	
	public static final IPlatformNetworkHelper NETWORK = load(IPlatformNetworkHelper.class);
	
	public static <T> T load(Class<T> clazz) {
		return ServiceLoader.load(clazz)
				.findFirst()
				.orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
	}
}
