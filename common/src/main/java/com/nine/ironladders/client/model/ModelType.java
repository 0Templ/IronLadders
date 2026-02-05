package com.nine.ironladders.client.model;

import com.nine.ironladders.common.util.Cyclable;
import com.nine.ironladders.config.ILConfig;

import java.util.function.BooleanSupplier;

public enum ModelType implements Cyclable<ModelType> {

	V1("v1", () -> ILConfig.ENABLE_MULTIVARIANT_MODELS.get()),
	V2("v2", () -> ILConfig.ENABLE_MULTIVARIANT_MODELS.get()),
	V3("v3", () -> ILConfig.ENABLE_MULTIVARIANT_MODELS.get()),

	VANILLA("vanilla", () -> false),
	;

	public final String key;
	private final BooleanSupplier multivariantSupplier;

	ModelType(String key, BooleanSupplier multivariantSupplier) {
		this.key = key;
		this.multivariantSupplier = multivariantSupplier;
	}
	
	public static ModelType byKey(String key) {
		for (ModelType type : values()) {
			if (type.key.equals(key)) {
				return type;
			}
		}
		return V1;
	}

	public boolean isMultivariant() {
		return multivariantSupplier.getAsBoolean();
	}

	public String blockStateKey() {
		return blockStateKey(isMultivariant());
	}

	public String blockStateKey(boolean multiRequired) {
		return multiRequired && isMultivariant() ? "multivariant_" + key : key;
	}
	
	public String blockStateSuffix(boolean multivariant) {
		return "_" + blockStateKey(multivariant);
	}
	
}
