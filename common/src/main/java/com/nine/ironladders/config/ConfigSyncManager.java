package com.nine.ironladders.config;

import com.nine.ironladders.ILCommon;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigSyncManager {
	
	private static final byte BOOLEAN = 0;
	private static final byte INTEGER = 1;
	private static final byte DOUBLE = 2;
	private static final byte STRING = 3;
	private static final byte ENUM = 4;
	private static final byte LIST = 5;
	private static final byte FLOAT = 6;
//	..
	
	private static final Map<String, ConfigValue<?>> ALL_CONFIGS = new HashMap<>();
	private static final Map<String, ConfigValue<?>> SYNC_CONFIGS = new HashMap<>();
	
	public static final Map<String, Object> SYNCED_VALUES = new HashMap<>();
	
	public static void checkSyncable(ConfigValue<?> config) {
		ALL_CONFIGS.put(config.path(), config);
		if (config.shouldSync()) {
			SYNC_CONFIGS.put(config.path(), config);
		}
	}
	
	public static FriendlyByteBuf encode(FriendlyByteBuf buf) {
		buf.writeVarInt(SYNC_CONFIGS.size());
		for (Map.Entry<String, ConfigValue<?>> entry : SYNC_CONFIGS.entrySet()) {
			String path = entry.getKey();
			ConfigValue<?> config = entry.getValue();
			Object value = config.get();
			buf.writeUtf(path);
			buf.writeByte(getId(config.clazz()));
			writeTypedValue(buf, value, config);
		}
		return buf;
	}
	
	public static Map<String, Object> decode(FriendlyByteBuf buf) {
		int size = buf.readVarInt();
		Map<String, Object> ret = new HashMap<>();
		for (int i = 0; i < size; i++) {
			String path = buf.readUtf();
			byte type = buf.readByte();
			ConfigValue<?> config = ALL_CONFIGS.get(path);
			Object value = readTypedValue(buf, config, type);
			if (config != null) {
				value = clampValue(config, value);
				if (value != null) {
					ret.put(path, value);
				}
			} else {
				ILCommon.LOGGER.warn("Could not sync config value: {}", path);
			}
		}
		return ret;
	}
	
	private static void writeTypedValue(FriendlyByteBuf buf, Object value, ConfigValue<?> config) {
		writeTypedValue(buf, value, config.clazz(), config.elementClass());
	}

	private static void writeTypedValue(FriendlyByteBuf buf, Object value, Class<?> clazz, Class<?> elementClass) {
		if (clazz == Boolean.class) {
			buf.writeBoolean((Boolean) value);
		} else if (clazz == Integer.class) {
			buf.writeInt((Integer) value);
		} else if (clazz == Double.class) {
			buf.writeDouble((Double) value);
		} else if (clazz == String.class) {
			buf.writeUtf((String) value);
		} else if (clazz == Float.class) {
			buf.writeFloat((Float) value);
		} else if (Enum.class.isAssignableFrom(clazz)) {
			buf.writeUtf(((Enum<?>) value).name());
		} else if (List.class.isAssignableFrom(clazz)) {
			List<?> list = (List<?>) value;
			buf.writeVarInt(list.size());
			if (list.isEmpty()) return;
			Class<?> contentClass = elementClass != null ? elementClass : list.get(0).getClass();
			buf.writeByte(getId(contentClass));
			for (Object el : list) {
				if (!contentClass.isInstance(el) && !(contentClass.isEnum() && el instanceof Enum<?>)) {
					throw new IllegalArgumentException("List contains mixed element types: " + el.getClass().getName());
				}
				writeTypedValue(buf, el, contentClass, null);
			}
		} else {
			throw new IllegalArgumentException("Unsupported config type: " + clazz);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static Object readTypedValue(FriendlyByteBuf buf, ConfigValue<?> config, byte type) {
		Class<?> clazz = config != null ? config.clazz() : null;
		Class<?> elementClass = config != null ? config.elementClass() : null;
		return readTypedValue(buf, clazz, elementClass, type);
	}

	@SuppressWarnings("unchecked")
	private static Object readTypedValue(FriendlyByteBuf buf, Class<?> clazz, Class<?> elementClass, byte type) {
		return switch (type) {
			case BOOLEAN -> buf.readBoolean();
			case INTEGER -> buf.readInt();
			case DOUBLE -> buf.readDouble();
			case STRING -> buf.readUtf();
			case FLOAT -> buf.readFloat();
			case ENUM -> {
				String name = buf.readUtf();
				if (clazz != null && clazz.isEnum()) {
					yield Enum.valueOf((Class<? extends Enum>) clazz, name);
				}
				yield name;
			}
			case LIST -> {
				int size = buf.readVarInt();
				List<Object> ret = new ArrayList<>();
				if (size > 0) {
					byte contentType = buf.readByte();
					Class<?> contentClass = elementClass;
					for (int i = 0; i < size; i++) {
						Object val = readTypedValue(buf, contentClass, null, contentType);
						if (contentType == ENUM && contentClass != null && contentClass.isEnum() && val instanceof String name) {
							val = Enum.valueOf((Class<? extends Enum>) contentClass, name);
						}
						ret.add(val);
					}
				}
				yield ret;
			}
			default -> throw new IllegalArgumentException("Unknown type ID: " + type);
		};
	}
	
	private static byte getId(Class<?> clazz) {
		if (clazz == Boolean.class) return BOOLEAN;
		if (clazz == Double.class) return DOUBLE;
		if (clazz == Integer.class) return INTEGER;
		if (clazz == String.class) return STRING;
		if (clazz == Float.class) return FLOAT;
		if (Enum.class.isAssignableFrom(clazz)) return ENUM;
		if (List.class.isAssignableFrom(clazz)) return LIST;
		throw new RuntimeException("Unsupported config type: " + clazz);
	}

	private static Object clampValue(ConfigValue<?> config, Object value) {
		if (config == null || value == null) return value;
		Number min = config.min();
		Number max = config.max();
		if (min == null && max == null) return value;
		if (value instanceof Integer intValue) {
			int minVal = min != null ? min.intValue() : Integer.MIN_VALUE;
			int maxVal = max != null ? max.intValue() : Integer.MAX_VALUE;
			return Math.max(minVal, Math.min(maxVal, intValue));
		}
		if (value instanceof Double doubleValue) {
			double minVal = min != null ? min.doubleValue() : -Double.MAX_VALUE;
			double maxVal = max != null ? max.doubleValue() : Double.MAX_VALUE;
			return Math.max(minVal, Math.min(maxVal, doubleValue));
		}
		if (value instanceof Float floatValue) {
			float minVal = min != null ? min.floatValue() : -Float.MAX_VALUE;
			float maxVal = max != null ? max.floatValue() : Float.MAX_VALUE;
			return Math.max(minVal, Math.min(maxVal, floatValue));
		}
		return value;
	}
	
}
