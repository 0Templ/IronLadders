package com.nine.ironladders.common.material;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.config.ILConfig;
import com.nine.ironladders.init.ILBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class RecipeHelper {
	
	public static final Set<Item> UNCRAFTABLE_ITEMS = new HashSet<>();
	
	private static List<ItemStack> hiddenStacksCache = null;
	
	public static void init() {
		UNCRAFTABLE_ITEMS.clear();
		Multimap<ILMaterial, Block> set = HashMultimap.create();
		Set<Block> missing = new HashSet<>();
		
		for (var block : ILBlocks.AVAILABLE_LADDERS.all()) {
			if (!(block instanceof MetalLadderBlock ladder)) continue;
			for (var mat : ladder.getType().materials) {
				set.put(mat, ladder);
			}
		}
		
		for (var material : set.keySet()){
			if (material instanceof ILItemMaterial itemMaterial) {
				// Possibly has no sense?
				{
					var reqKey = BuiltInRegistries.ITEM.getKey(itemMaterial.item().asItem());
					if (!BuiltInRegistries.ITEM.containsKey(reqKey)) {
						missing.addAll(set.get(material));
					}
				}
			}
			else if (material instanceof ILTagMaterial tagMaterial) {
				var opt = BuiltInRegistries.ITEM.getTag(tagMaterial.tag());
				if (opt.isEmpty() || opt.get().size() == 0) {
					missing.addAll(set.get(material));
				}
			}
		}
		
		for (var uncraftable : missing){
			UNCRAFTABLE_ITEMS.add(uncraftable.asItem());
		}
		hiddenStacksCache = null;
	}

	
	public static List<ItemStack> hiddenStacks() {
		if (!ILConfig.HIDE_UNCRAFTABLE_LADDERS.get()) {
			return Collections.emptyList();
		}
		
		if (hiddenStacksCache != null) {
			return hiddenStacksCache;
		}
		
		if (UNCRAFTABLE_ITEMS.isEmpty()) {
			init();
		}
		
		List<ItemStack> stacks = new ArrayList<>();
		
		for (var item : UNCRAFTABLE_ITEMS) {
			var stack = new ItemStack(item);
			if (stack.isEmpty()) continue;
			stacks.add(stack);
		}
		
		hiddenStacksCache = stacks;
		return hiddenStacksCache;
	}
	
	
}
