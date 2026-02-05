package com.nine.ironladders.common.util;

import com.nine.ironladders.platform.util.LoaderTarget;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PlatformObjects<T> {
	
	private final Map<LoaderTarget, Set<Supplier<T>>> objects = new EnumMap<>(LoaderTarget.class);
	
	public PlatformObjects() {
		for (var target : LoaderTarget.values()) {
			objects.put(target, new LinkedHashSet<>());
		}
	}
	
	public PlatformObjects<T> add(Supplier<T> obj, LoaderTarget... targets) {
		for (var target : targets) {
			objects.get(target).add(obj);
		}
		return this;
	}
	
	public Set<T> get(LoaderTarget target) {
		return objects.get(target).stream()
				.map(Supplier::get)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public Set<T> get(LoaderTarget... targets) {
		return Arrays.stream(targets)
				.flatMap(t -> objects.get(t).stream())
				.map(Supplier::get)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public Set<T> all() {
		return objects.values().stream()
				.flatMap(Set::stream)
				.map(Supplier::get)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}
}
