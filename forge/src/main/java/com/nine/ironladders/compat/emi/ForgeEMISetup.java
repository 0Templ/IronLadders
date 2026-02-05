package com.nine.ironladders.compat.emi;

import com.nine.ironladders.compat.nei.NeiHelper;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EmiEntrypoint
public class ForgeEMISetup implements EmiPlugin {
	
	@Override
	public void register(EmiRegistry registry) {
		var hidden = NeiHelper.hiddenNEIStacks();
		if (hidden.isEmpty()) return;
		
		for (var stack : hidden) {
			registry.removeEmiStacks(EmiStack.of(stack));
		}
		
		registry.removeRecipes(recipe -> hasHiddenItem(recipe,
				hidden.stream().map(ItemStack::getItem).collect(Collectors.toSet())));
		
		NeiHelper.NEI_ITEM_INFO_MAP.forEach((item, component) ->
				registry.addRecipe(new EmiInfoRecipe(
						List.of(EmiIngredient.of(Ingredient.of(item))), List.of(component), null)));
	}
	
	private static boolean hasHiddenItem(EmiRecipe recipe, Set<Item> hiddenItems) {
		for (var ingredient : recipe.getInputs()) {
			for (var stack : ingredient.getEmiStacks()) {
				ItemStack itemStack = stack.getItemStack();
				if (!itemStack.isEmpty() && hiddenItems.contains(itemStack.getItem())) {
					return true;
				}
			}
		}
		for (var stack : recipe.getOutputs()) {
			ItemStack itemStack = stack.getItemStack();
			if (!itemStack.isEmpty() && hiddenItems.contains(itemStack.getItem())) {
				return true;
			}
		}
		return false;
	}
}
