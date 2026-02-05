package com.nine.ironladders.platform.util;

public record FabricRegistryProvider<T>(T value) implements RegistryProvider<T> {
	
	@Override
	public T get() {
		return value;
	}
	
}
