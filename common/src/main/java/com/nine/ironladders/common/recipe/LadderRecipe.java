package com.nine.ironladders.common.recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public record LadderRecipe(Item result, Ingredient base, UnitMaterial unitMaterials) {
	
	public record UnitMaterial(Ingredient ingredient, int units) {
		
		@Override
		public String toString() {
			return units + ": " + ingredient;
		}
	}
	
	@Override
	public String toString() {
		return "["+result + "]";
	}
	
}
