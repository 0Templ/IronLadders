package com.nine.ironladders.common.material;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public record ILTagMaterial(TagKey<Item> tag) implements ILMaterial {
	
	@Override
	public Ingredient ingredient() {
		return Ingredient.of(tag);
	}
	
}
