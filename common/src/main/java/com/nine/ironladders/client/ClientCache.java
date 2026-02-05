package com.nine.ironladders.client;

import com.nine.ironladders.client.model.ModelType;
import com.nine.ironladders.client.tab.TabIcon;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.material.RecipeHelper;
import com.nine.ironladders.common.util.LadderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ClientCache {
	
	private ClientCache() {}
	
	public static final Map<ModelType, Map<BlockState, BakedModel>> CACHE = new EnumMap<>(ModelType.class);
	
	public static void register(ModelType type, BlockState state, BakedModel model) {
		CACHE.computeIfAbsent(type, k -> new IdentityHashMap<>()).put(state, model);
	}
	
	private static TabIcon TAB_ICON;
	
	public static TabIcon getTabIcon(){
		if (TAB_ICON != null){
			return TAB_ICON;
		}
		else {
			TAB_ICON = createTabIcon();
			return TAB_ICON;
			
		}
	}
	
	private static TabIcon createTabIcon(){
		Set<LadderType> hiddenTypes = RecipeHelper.hiddenStacks().stream()
				.map(ItemStack::getItem)
				.mapMulti((Item item, Consumer<LadderType> consumer) -> {
					if (item instanceof BlockItem blockItem){
						if (blockItem.getBlock() instanceof MetalLadderBlock ladder){
							consumer.accept(ladder.getType());
						}
					}
				})
				.collect(Collectors.toSet());
		var values = Arrays.stream(LadderType.values())
				.filter(type -> !hiddenTypes.contains(type))
				.filter(type -> type.tabIconPart != null)
				.map(type -> type.tabIconPart).toList();
		if (values.size() < 3){
			return new TabIcon(LadderType.IRON.tabIconPart, LadderType.GOLD.tabIconPart, LadderType.DIAMOND.tabIconPart);
		}
		var icons = new ArrayList<>(values);
		Collections.shuffle(icons);
		return new TabIcon(icons.get(0), icons.get(1), icons.get(2));
	}

}
