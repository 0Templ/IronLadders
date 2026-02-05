package com.nine.ironladders.common.recipe;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.init.ILBlocks;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class ILRecipeCache {

	private ILRecipeCache() {}
	
	public static final Set<LadderRecipe> unitRecipes = new HashSet<>();
	public static final Map<Item, Set<LadderRecipe>> recipesByBase = new HashMap<>();
	public static final Map<Item, List<List<LadderRecipe>>> tierCache = new HashMap<>();
	
	
	public static Set<LadderRecipe> getRecipes(){
		return unitRecipes;
	}

	public static Set<LadderRecipe> getRecipesByBase(Item baseItem) {
		return recipesByBase.getOrDefault(baseItem, Set.of());
	}

	public static List<List<LadderRecipe>> getRecipeTiers(Item baseItem) {
		return tierCache.computeIfAbsent(baseItem, ILRecipeCache::buildRecipeTiers);
	}
	
	public static void onResourcesReload(RecipeManager manager, RegistryAccess access) {
		unitRecipes.clear();
		recipesByBase.clear();
		tierCache.clear();
		
		Set<Item> ladders = new HashSet<>(ILBlocks.AVAILABLE_LADDERS.all().stream()
				.map(Block::asItem)
				.toList());
		ladders.add(Items.LADDER);
		
		for (var recipe : manager.getAllRecipesFor(RecipeType.CRAFTING)) {
			
			ItemStack out = recipe.getResultItem(access);
			if (out.isEmpty()) continue;
			
			if (!ILCommon.MODID.equals(recipe.getId().getNamespace())) continue;
			
			Ingredient base = null;
			int baseSlots = 0;
			
			Map<List<ResourceLocation>, MatAcc> mats = new HashMap<>();
			
			for (Ingredient ing : recipe.getIngredients()) {
				if (ing.isEmpty()) continue;
				
				if (isOnlyLadders(ing, ladders)) {
					baseSlots++;
					if (base == null) base = ing;
					continue;
				}
				
				List<ResourceLocation> key = ingredientKey(ing);
				if (key.isEmpty()) {
					base = null;
					break;
				}
				
				mats.computeIfAbsent(key, k -> new MatAcc(ing)).slots++;
			}
			
			if (base == null || mats.isEmpty() || baseSlots != out.getCount()) continue;

			Set<Item> baseItems = ingredientItems(base);
			if (baseItems.isEmpty()) continue;
			
			for (MatAcc acc : mats.values()) {
				LadderRecipe ladderRecipe = new LadderRecipe(
						out.getItem(),
						base,
						new LadderRecipe.UnitMaterial(acc.ingredient, acc.slots)
				);
				unitRecipes.add(ladderRecipe);
				indexByBase(baseItems, ladderRecipe);
			}
		}
		for (var l : ILBlocks.AVAILABLE_LADDERS.all()){
			var item = l.asItem();
			if (l instanceof MetalLadderBlock metalLadderBlock){
//				System.out.println(metalLadderBlock.getType() + " (" + item + ")" + getRecipesByBase(metalLadderBlock.asItem()));
			}
			
		}
	}

	private static List<List<LadderRecipe>> buildRecipeTiers(Item baseItem) {
		if (recipesByBase.isEmpty()) return List.of();

		Set<Item> visited = new HashSet<>();
		Set<Item> frontier = new HashSet<>();
		frontier.add(baseItem);
		visited.add(baseItem);

		List<List<LadderRecipe>> tiers = new ArrayList<>();
		while (!frontier.isEmpty()) {
			Set<LadderRecipe> tierRecipes = new LinkedHashSet<>();
			Set<Item> nextFrontier = new HashSet<>();

			for (Item item : frontier) {
				Set<LadderRecipe> recipes = recipesByBase.get(item);
				if (recipes == null) continue;

				for (LadderRecipe recipe : recipes) {
					if (tierRecipes.add(recipe) && visited.add(recipe.result())) {
						nextFrontier.add(recipe.result());
					}
				}
			}

			if (tierRecipes.isEmpty()) break;
			tiers.add(List.copyOf(tierRecipes));
			frontier = nextFrontier;
		}

		return List.copyOf(tiers);
	}

	private static Set<Item> ingredientItems(Ingredient ingredient) {
		ItemStack[] stacks = ingredient.getItems();
		if (stacks.length == 0) return Set.of();

		Set<Item> items = new HashSet<>();
		for (ItemStack stack : stacks) {
			items.add(stack.getItem());
		}
		return items;
	}

	private static void indexByBase(Set<Item> baseItems, LadderRecipe recipe) {
		for (Item baseItem : baseItems) {
			recipesByBase.computeIfAbsent(baseItem, k -> new LinkedHashSet<>()).add(recipe);
		}
	}

	private static List<ResourceLocation> ingredientKey(Ingredient ing) {
		return Arrays.stream(ing.getItems())
				.map(s -> BuiltInRegistries.ITEM.getKey(s.getItem()))
				.distinct()
				.sorted(Comparator.comparing(ResourceLocation::toString))
				.toList();
	}
	
	private static final class MatAcc {
		final Ingredient ingredient;
		int slots;
		MatAcc(Ingredient ingredient) { this.ingredient = ingredient; }
	}
	
	private static boolean isOnlyLadders(Ingredient ing, Set<Item> ladderItems) {
		ItemStack[] variants = ing.getItems();
		if (variants.length == 0) return false;
		
		for (ItemStack v : variants) {
			if (!ladderItems.contains(v.getItem())) return false;
		}
		return true;
	}
	
	
}
