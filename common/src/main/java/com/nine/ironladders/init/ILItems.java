package com.nine.ironladders.init;

import com.nine.ironladders.common.item.*;
import com.nine.ironladders.platform.Platform;
import com.nine.ironladders.platform.util.RegistryProvider;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ILItems {
	
	private ILItems(){}
	
	public static final List<RegistryProvider<Item>> ITEMS = new ArrayList<>();

	public static final RegistryProvider<Item> MORPH_TOOL = register(
			"ladder_morph_tool", () -> new MorphToolItem(new Item.Properties().stacksTo(1)));
	
	public static final RegistryProvider<Item> SENSOR_TOOL = register(
			"ladder_sensor_tool", () -> new SensorToolItem(new Item.Properties().stacksTo(1)));
		
	public static final RegistryProvider<Item> LIGHT_TOOL = register(
			"ladder_light_tool", () -> new LightToolItem(new Item.Properties().stacksTo(1)));
			
	public static final RegistryProvider<Item> STYLER_TOOL = register(
			"ladder_styler_tool", () -> new StylerToolItem(new Item.Properties().stacksTo(1)));
	
	public static final RegistryProvider<Item> CASING_TOOL = register(
			"ladder_casing_tool", () -> new CasingToolItem(new Item.Properties().stacksTo(1)));
	
	private static RegistryProvider<Item> register(String id, Supplier<Item> item) {
		var ret = Platform.REGISTRY.registerItem(id, item);
		ITEMS.add(ret);
		return ret;
	}
	
	public static void init(){
	}
	
}
