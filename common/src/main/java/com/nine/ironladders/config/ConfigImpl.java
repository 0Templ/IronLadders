package com.nine.ironladders.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.nine.ironladders.ILCommon;
import com.nine.ironladders.config.option.*;
import com.nine.ironladders.platform.Platform;
import com.nine.ironladders.platform.util.LoaderTarget;
import net.minecraft.util.Mth;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ConfigImpl {

	private static final File COMMON_FILE = getConfigPath("ironladders-common");
	private static final File CLIENT_FILE = getConfigPath("ironladders-client");

	public static final CommentedFileConfig COMMON = CommentedFileConfig.builder(COMMON_FILE)
			.autosave()
			.sync()
			.preserveInsertionOrder()
			.build();

	public static final CommentedFileConfig CLIENT = CommentedFileConfig.builder(CLIENT_FILE)
			.autosave()
			.sync()
			.preserveInsertionOrder()
			.build();

	static {
		load();
	}

	private static void load() {
		COMMON.load();
		CLIENT.load();
	}

	public static  <T> ConfigValue<T> register(String key, T defaultValue, ConfigOption... options) {
		return register(key, defaultValue, tryGetClass(defaultValue), tryGetListElementClass(defaultValue), options);
	}

	private static <T> ConfigValue<T> register(
			String key,
			T defaultValue,
			Class<T> clazz,
			Class<?> elementClass,
			ConfigOption... options
	) {
		ConfigSpec spec = new ConfigSpec();
		for (var option : options) {
			option.apply(spec);
		}
		if (!matchesLoaderTarget(spec.target)) {
			return new NoopConfigValue<>(key, defaultValue, clazz, elementClass);
		}

		CommentedFileConfig config = getConfig(spec.side);
		NightConfigValue<T> value = new NightConfigValue<>(
				config,
				key,
				defaultValue,
				spec.section,
				spec.side,
				spec.shouldSync,
				spec.range,
				clazz,
				elementClass
		);

		if (config.get(value.path()) == null) {
			config.set(value.path(), serialize(value.defaultValue(), value.clazz));
		}

		clampStored(config, value);

		String comment = buildComment(spec.comment, spec.range, spec.hideConstraints, clazz);
		if (!comment.isBlank()) {
			config.setComment(value.path(), comment);
		}
		normalizeTomlSpacing(spec.side);

		ConfigSyncManager.checkSyncable(value);
		return value;
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> tryGetClass(T defaultValue) {
		return (Class<T>) defaultValue.getClass();
	}

	private static Class<?> tryGetListElementClass(Object defaultValue) {
		if (defaultValue instanceof List<?> list && !list.isEmpty()) {
			return list.get(0).getClass();
		}
		return null;
	}

	private static boolean matchesLoaderTarget(LoaderTarget loaderTarget) {
		return loaderTarget == null
				|| loaderTarget == LoaderTarget.COMMON
				|| loaderTarget == Platform.CORE.currentLoader();
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(ConfigValue<T> value) {
		if (value instanceof NightConfigValue<?> nightValue) {
			return (T) nightValue.read();
		}
		return value.defaultValue();
	}

	@SuppressWarnings("unchecked")
	public static <T> void set(ConfigValue<T> value, T newValue) {
		if (!(value instanceof NightConfigValue<?> nightValue)) {
			return;
		}
		NightConfigValue<T> typedValue = (NightConfigValue<T>) nightValue;
		Object toSet = tryClamp(typedValue, newValue);
		typedValue.config.set(typedValue.path(), serialize(toSet, typedValue.clazz));
		typedValue.config.save();
	}

	private static CommentedFileConfig getConfig(ConfigSide side) {
		return switch (side) {
			case CLIENT -> CLIENT;
			case COMMON -> COMMON;
		};
	}

	private static String buildComment(
			ConfigComment comment,
			ConfigRange<?> range,
			boolean hideConstraints,
			Class<?> clazz
	) {
		List<String> lines = new ArrayList<>();
		if (comment != null && comment.value() != null && !comment.value().isBlank()) {
			lines.add(comment.value());
		}

		if (range != null && !hideConstraints) {
			lines.add("Range: " + range.min + " .. " + range.max);
		}

		if (clazz != null && clazz.isEnum()) {
			Object[] values = clazz.getEnumConstants();
			String[] names = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				names[i] = ((Enum<?>) values[i]).name();
			}
			lines.add("Available values: " + String.join(", ", names));
		}

		return String.join("\n", lines);
	}

	private static void clampStored(CommentedFileConfig config, ConfigValue<?> value) {
		Object current = config.get(value.path());
		Object clamped = tryClamp(value, current);
		if (clamped != null && !clamped.equals(current)) {
			config.set(value.path(), clamped);
		}
	}

	// TODO: REDO
	private static Object tryClamp(ConfigValue<?> value, Object input) {
		if (input == null) {
			return null;
		}
		Number min = value.min();
		Number max = value.max();
		if (min == null && max == null) {
			return input;
		}
		if (input instanceof Integer intValue) {
			return Mth.clamp(
					intValue,
					min != null ? min.intValue() : Integer.MIN_VALUE,
					max != null ? max.intValue() : Integer.MAX_VALUE
			);
		}
		if (input instanceof Double doubleValue) {
			return Mth.clamp(
					doubleValue,
					min != null ? min.doubleValue() : -Double.MAX_VALUE,
					max != null ? max.doubleValue() : Double.MAX_VALUE
			);
		}
		if (input instanceof Float floatValue) {
			return Mth.clamp(
					floatValue,
					min != null ? min.floatValue() : -Float.MAX_VALUE,
					max != null ? max.floatValue() : Float.MAX_VALUE
			);
		}
		return input;
	}

	private static Object serialize(Object value, Class<?> clazz) {
		if (value == null) {
			return null;
		}
		if (clazz != null && clazz.isEnum()) {
			return ((Enum<?>) value).name();
		}
		return value;
	}

	private static File getConfigPath(String id) {
		return Platform.CORE.getConfigPath(id);
	}

	private static void normalizeTomlSpacing(ConfigSide side) {
		File file = switch (side) {
			case CLIENT -> CLIENT_FILE;
			case COMMON -> COMMON_FILE;
		};
		if (!file.exists()) {
			return;
		}
		try {
			String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
			String lineSeparator = content.contains("\r\n") ? "\r\n" : "\n";
			String[] lines = content.split("\\R", -1);
			boolean changed = false;
			for (int i = 0; i < lines.length; i++) {
				var trimmed = lines[i].trim();
				if (trimmed.equals("#")) {
					lines[i] = "";
					changed = true;
				}
			}
			if (changed) {
				Files.writeString(file.toPath(), String.join(lineSeparator, lines), StandardCharsets.UTF_8);
			}
		} catch (IOException e) {
			ILCommon.LOGGER.warn("Failed to normalize: {}", file.getName(), e);
		}
	}

	private static final class NoopConfigValue<T> extends ConfigValue<T> {

		private NoopConfigValue(String key, T defaultValue, Class<T> clazz, Class<?> elementClass) {
			super(key, defaultValue, null, ConfigSection.NONE, ConfigSide.COMMON, false, clazz, elementClass);
		}

		@Override
		public T get() {
			return defaultValue();
		}

		@Override
		public void set(T value) {
		}
	}

	private static class NightConfigValue<T> extends ConfigValue<T> {

		private final CommentedFileConfig config;

		private NightConfigValue(
				CommentedFileConfig config,
				String key,
				T defaultValue,
				ConfigSection section,
				ConfigSide side,
				boolean shouldSync,
				ConfigRange<?> range,
				Class<T> clazz,
				Class<?> elementClass
		) {
			super(key, defaultValue, range, section, side, shouldSync, clazz, elementClass);
			this.config = config;
		}

		@Override
		public T get() {
			return read();
		}

		@SuppressWarnings("unchecked")
		private T read() {
			if (ConfigSyncManager.SYNCED_VALUES.containsKey(path())) {
				return (T) ConfigSyncManager.SYNCED_VALUES.get(path());
			}
			Object value = config.get(path());
			if (value instanceof Number number) {
				if (clazz == Double.class) {
					return (T) Double.valueOf(number.doubleValue());
				}
				if (clazz == Float.class) {
					return (T) Float.valueOf(number.floatValue());
				}
				if (clazz == Integer.class) {
					return (T) Integer.valueOf(number.intValue());
				}
				if (clazz == Long.class) {
					return (T) Long.valueOf(number.longValue());
				}
			}
			if (clazz != null && clazz.isEnum() && value instanceof String str) {
				return (T) Enum.valueOf((Class<Enum>) clazz, str);
			}
			return (T) value;
		}
	}
}
