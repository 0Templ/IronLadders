package com.nine.ironladders.common.material;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public record ILItemMaterial(ItemLike item) implements ILMaterial {
	
	@Override
	public Ingredient ingredient() {
		return Ingredient.of(item);
	}

}
