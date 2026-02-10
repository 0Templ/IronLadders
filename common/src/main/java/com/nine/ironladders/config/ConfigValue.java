package com.nine.ironladders.config;

import com.nine.ironladders.config.option.ConfigRange;
import com.nine.ironladders.config.option.ConfigSection;
import com.nine.ironladders.config.option.ConfigSide;

//TODO: merge with NightConfigValue
public class ConfigValue<T> {

    private final T defaultValue;
    private final ConfigSection section;
    public final String key;
    public final Class<T> clazz;
    public final ConfigSide side;
    private final boolean shouldSync;
    private final Class<?> elementClass;
	private final ConfigRange<?> range;

    public ConfigValue(
			String key,
			T defaultValue,
			ConfigRange<?> range,
			ConfigSection section,
			ConfigSide side,
			boolean shouldSync,
			Class<T> clazz,
			Class<?> elementClass
	) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.range = range;
        this.section = section;
        this.side = side;
        this.shouldSync = shouldSync;
		this.clazz = clazz;
        this.elementClass = elementClass;
    }

    public Class<T> clazz(){
        return clazz;
    }

    public String path() {
        return section.getFullKey() + "." + key;
    }

    public T defaultValue() {
        return defaultValue;
    }

    public T get() {
        return ConfigImpl.get(this);
    }

    public void set(T value) {
		ConfigImpl.set(this, value);
    }

    public boolean shouldSync(){
        return shouldSync;
    }

    public Number min() {
        return range != null ? range.min() : null;
    }

    public Number max() {
        return range != null ? range.max() : null;
    }

    public Class<?> elementClass() {
        return elementClass;
    }
	
}
