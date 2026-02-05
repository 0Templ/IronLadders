package com.nine.ironladders.platform.util;

import net.minecraftforge.registries.RegistryObject;

public record ForgeRegistryObject<T>(RegistryObject<T> value) implements RegistryProvider<T> {
	
	@Override
	public T get() {
		return value.get();
	}
	
}
