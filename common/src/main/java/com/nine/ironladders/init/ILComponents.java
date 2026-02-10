package com.nine.ironladders.init;

import com.mojang.serialization.Codec;
import com.nine.ironladders.platform.Platform;
import com.nine.ironladders.platform.util.RegistryProvider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

public class ILComponents {
	
	private ILComponents() {
	}
	
	public static final RegistryProvider<DataComponentType<String>> MORPH_TYPE_KEY = Platform.REGISTRY.registerComponent(
			"morph_type_key",
			DataComponentType.<String>builder()
					.persistent(Codec.STRING)
					.networkSynchronized(ByteBufCodecs.STRING_UTF8)
					.cacheEncoding()
					.build()
	);
	
	public static final RegistryProvider<DataComponentType<Integer>> MORPH_TYPE_ID = Platform.REGISTRY.registerComponent(
			"morph_type_id",
			DataComponentType.<Integer>builder()
					.persistent(Codec.INT)
					.networkSynchronized(ByteBufCodecs.INT)
					.cacheEncoding()
					.build()
	);
	
	public static final RegistryProvider<DataComponentType<String>> STYLER_MODEL_TYPE = Platform.REGISTRY.registerComponent(
			"styler_model_type",
			DataComponentType.<String>builder()
					.persistent(Codec.STRING)
					.networkSynchronized(ByteBufCodecs.STRING_UTF8)
					.cacheEncoding()
					.build()
	);
	
	public static final RegistryProvider<DataComponentType<Integer>> SAVED_CHARGES_COUNT = Platform.REGISTRY.registerComponent(
			"saved_charges_count",
			DataComponentType.<Integer>builder()
					.persistent(Codec.INT)
					.networkSynchronized(ByteBufCodecs.INT)
					.cacheEncoding()
					.build()
	);
	
	public static final RegistryProvider<DataComponentType<Integer>> TEST_INT = Platform.REGISTRY.registerComponent(
			"test_int",
			DataComponentType.<Integer>builder()
					.persistent(Codec.INT)
					.networkSynchronized(ByteBufCodecs.INT)
					.cacheEncoding()
					.build()
	);
	
	public static void init() {
	}

}
