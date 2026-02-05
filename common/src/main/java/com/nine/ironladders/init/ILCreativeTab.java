package com.nine.ironladders.init;

import com.nine.ironladders.common.material.RecipeHelper;
import com.nine.ironladders.platform.Platform;
import com.nine.ironladders.platform.util.RegistryProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.HashSet;

public class ILCreativeTab {

    public static RegistryProvider<CreativeModeTab> TAB = Platform.REGISTRY.registerCreativeTab("iron_ladders",
            CreativeModeTab.builder(null, -1).title(Component.translatable("itemgroup.ironladders"))
                    .icon(() -> new ItemStack(ILBlocks.IRON_LADDER.get()))
                    .displayItems((params, output) -> {
						
						TabAcceptor acceptor = new TabAcceptor(params, output);
						
						// Vanilla ladders (same order as ILConfig; copper variants grouped together)
						acceptor.accept(ILBlocks.COPPER_LADDER.get());
						acceptor.accept(ILBlocks.EXPOSED_COPPER_LADDER.get());
						acceptor.accept(ILBlocks.WEATHERED_COPPER_LADDER.get());
						acceptor.accept(ILBlocks.OXIDIZED_COPPER_LADDER.get());
						acceptor.accept(ILBlocks.WAXED_COPPER_LADDER.get());
						acceptor.accept(ILBlocks.WAXED_EXPOSED_COPPER_LADDER.get());
						acceptor.accept(ILBlocks.WAXED_WEATHERED_COPPER_LADDER.get());
						acceptor.accept(ILBlocks.WAXED_OXIDIZED_COPPER_LADDER.get());
						
						acceptor.accept(ILBlocks.IRON_LADDER.get());
						acceptor.accept(ILBlocks.GOLDEN_LADDER.get());
						acceptor.accept(ILBlocks.DIAMOND_LADDER.get());
						acceptor.accept(ILBlocks.OBSIDIAN_LADDER.get());
						acceptor.accept(ILBlocks.CRYING_OBSIDIAN_LADDER.get());
						acceptor.accept(ILBlocks.NETHERITE_LADDER.get());
						acceptor.accept(ILBlocks.BEDROCK_LADDER.get());
						
						// Non-vanilla ladders (by the same tier groups/order as ILConfig)
						acceptor.accept(ILBlocks.TIN_LADDER.get());
						acceptor.accept(ILBlocks.ZINC_LADDER.get());
						acceptor.accept(ILBlocks.ALUMINIUM_LADDER.get());
						
						acceptor.accept(ILBlocks.BRONZE_LADDER.get());
						acceptor.accept(ILBlocks.LEAD_LADDER.get());
						acceptor.accept(ILBlocks.SILVER_LADDER.get());
						acceptor.accept(ILBlocks.STEEL_LADDER.get());
						acceptor.accept(ILBlocks.NICKEL_LADDER.get());
						
						acceptor.accept(ILBlocks.ELECTRUM_LADDER.get());
						acceptor.accept(ILBlocks.ADVANCED_ALLOY_LADDER.get());
						acceptor.accept(ILBlocks.INVAR_LADDER.get());
						
						acceptor.accept(ILBlocks.CHROMIUM_LADDER.get());
						acceptor.accept(ILBlocks.PLATINUM_LADDER.get());
						acceptor.accept(ILBlocks.TITANIUM_LADDER.get());
						acceptor.accept(ILBlocks.TUNGSTEN_LADDER.get());
						
						acceptor.accept(ILBlocks.TUNGSTEN_STEEL_LADDER.get());
						
						// Tools
						acceptor.accept(ILItems.LIGHT_TOOL.get());
						acceptor.accept(ILItems.MORPH_TOOL.get());
						acceptor.accept(ILItems.SENSOR_TOOL.get());
						acceptor.accept(ILItems.STYLER_TOOL.get());
						acceptor.accept(ILItems.CASING_TOOL.get());
					}));
	
        public record TabAcceptor(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {
               
                public void accept(ItemLike itemLike) {
					if (itemLike == null) {
						return;
					}
					var toHide = new HashSet<>(
							RecipeHelper.hiddenStacks().stream().map(ItemStack::getItem).toList()
					);
					if (toHide.contains(itemLike.asItem())) {
						return;
					}
					output.accept(itemLike);
                }
		
	}

    public static void init(){

    }
}
