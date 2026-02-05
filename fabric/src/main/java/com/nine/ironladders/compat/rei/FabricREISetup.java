package com.nine.ironladders.compat.rei;

import com.nine.ironladders.compat.nei.NeiHelper;
import dev.architectury.event.EventResult;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FabricREISetup implements REIClientPlugin {

	@Override
	public void registerEntries(EntryRegistry registry) {
		var hidden = NeiHelper.hiddenNEIStacks();
		for (var stack : hidden) {
			registry.removeEntry(EntryStacks.of(stack));
		}
	}
	
	@Override
	public void registerDisplays(DisplayRegistry registry) {
		var hidden = NeiHelper.hiddenNEIStacks();
		for (var stack : hidden) {
			var item = stack.getItem();
			registry.registerVisibilityPredicate((category, display) -> {
				if (hasItem(display.getInputEntries(), item) ||
						hasItem(display.getOutputEntries(), item)) {
					return EventResult.interruptFalse();
				}
				return EventResult.pass();
			});
		}
		for (var info : NeiHelper.NEI_ITEM_INFO_MAP.entrySet()){
			registry.add(DefaultInformationDisplay.createFromEntry(EntryStacks.of(info.getKey()), info.getKey().asItem().getDescription())
					.lines(info.getValue()));
		}
	}
	
	private static boolean hasItem(Iterable<EntryIngredient> entries, Item item) {
		for (EntryIngredient ing : entries) {
			for (EntryStack<?> stack : ing) {
				if (stack.getType() == VanillaEntryTypes.ITEM) {
					ItemStack is = (ItemStack) stack.getValue();
					if (!is.isEmpty() && is.is(item)) return true;
				}
			}
		}
		return false;
	}
	
}
