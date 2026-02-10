package com.nine.ironladders.event;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.nine.ironladders.ILCommon;
import com.nine.ironladders.init.ILBlocks;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Supplier;

public class CopperInjector {

	private static final String[] WEATHERING_NEXT_BY_BLOCK = {"NEXT_BY_BLOCK", "f_154886_"};
	private static final String[] WEATHERING_PREVIOUS_BY_BLOCK = {"PREVIOUS_BY_BLOCK", "f_154887_"};
	private static final String[] HONEYCOMB_WAXABLES = {"WAXABLES", "f_150863_"};
	private static final String[] HONEYCOMB_WAX_OFF_BY_BLOCK = {"WAX_OFF_BY_BLOCK", "f_150864_"};

	public static void injectCopperLadders() {
		var weatheringOld = WeatheringCopper.NEXT_BY_BLOCK.get();
		if (!modifyImmutableBiMap(WeatheringCopper.class, Suppliers.memoize(() -> {
			ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
			for (var el : weatheringOld.entrySet()) {
				if (el.getKey() == null || el.getValue() == null) continue;
				builder.put(el.getKey(), el.getValue());
			}
			builder.put(ILBlocks.COPPER_LADDER.get(), ILBlocks.EXPOSED_COPPER_LADDER.get())
					.put(ILBlocks.EXPOSED_COPPER_LADDER.get(), ILBlocks.WEATHERED_COPPER_LADDER.get())
					.put(ILBlocks.WEATHERED_COPPER_LADDER.get(), ILBlocks.OXIDIZED_COPPER_LADDER.get());
			return builder.build();
		}), WEATHERING_NEXT_BY_BLOCK)) {
			return;
		}

		var weatheringInverseOld = WeatheringCopper.NEXT_BY_BLOCK.get().inverse();
		if (!modifyImmutableBiMap(WeatheringCopper.class, Suppliers.memoize(() -> weatheringInverseOld), WEATHERING_PREVIOUS_BY_BLOCK)) {
			return;
		}

		var waxablesOld = HoneycombItem.WAXABLES;
		if (!modifyImmutableBiMap(HoneycombItem.class, Suppliers.memoize(() -> {
			ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
			for (var entry : waxablesOld.get().entrySet()) {
				if (entry.getKey() == null || entry.getValue() == null) continue;
				builder.put(entry.getKey(), entry.getValue());
			}
			builder.put(ILBlocks.COPPER_LADDER.get(), ILBlocks.WAXED_COPPER_LADDER.get())
					.put(ILBlocks.EXPOSED_COPPER_LADDER.get(), ILBlocks.WAXED_EXPOSED_COPPER_LADDER.get())
					.put(ILBlocks.WEATHERED_COPPER_LADDER.get(), ILBlocks.WAXED_WEATHERED_COPPER_LADDER.get())
					.put(ILBlocks.OXIDIZED_COPPER_LADDER.get(), ILBlocks.WAXED_OXIDIZED_COPPER_LADDER.get());

			return builder.build();
		}), HONEYCOMB_WAXABLES)) {
			return;
		}

		var waxablesOffOld = HoneycombItem.WAXABLES.get().inverse();
		modifyImmutableBiMap(HoneycombItem.class, Suppliers.memoize(() -> waxablesOffOld), HONEYCOMB_WAX_OFF_BY_BLOCK);
	}

	private static boolean modifyImmutableBiMap(Class<?> clazz, Supplier<BiMap<Block, Block>> newSupplier, String... possibleFieldNames) {
		try {
			Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
			unsafeField.setAccessible(true);
			Unsafe unsafe = (Unsafe) unsafeField.get(null);
			Field field = findField(clazz, possibleFieldNames);
			if (field == null) {
				ILCommon.LOGGER.error("Couldn't find {}.{} with names {}. Available fields: {}", clazz.getSimpleName(), possibleFieldNames[0], Arrays.toString(possibleFieldNames), Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).toList());
				return false;
			}
			field.setAccessible(true);

			unsafe.putObject(
					unsafe.staticFieldBase(field),
					unsafe.staticFieldOffset(field),
					newSupplier);
			return true;
		} catch (NoSuchFieldException | IllegalAccessException e) {
			ILCommon.LOGGER.error("Couldn't modify {}.{} with names {}", clazz.getSimpleName(), possibleFieldNames[0], Arrays.toString(possibleFieldNames), e);
			return false;
		}
	}

	private static Field findField(Class<?> clazz, String... possibleFieldNames) {
		for (String fieldName : possibleFieldNames) {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException ignored) {
			}
		}
		return null;
	}
}
