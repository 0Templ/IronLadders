package com.nine.ironladders.datagen.provider.recipe;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.common.material.ILItemMaterial;
import com.nine.ironladders.common.material.ILMaterials;
import com.nine.ironladders.common.material.ILMaterial;
import com.nine.ironladders.common.material.ILTagMaterial;
import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.init.ILItems;
import com.nine.ironladders.platform.util.LoaderTarget;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class ILRecipeProvider extends FabricRecipeProvider {
	
	protected Consumer<FinishedRecipe> consumer;
	
	protected final LoaderTarget target;
	protected final ILMaterials materials;
	protected final Set<Item> available;
	
	private ILRecipeProvider(FabricDataOutput output, LoaderTarget target) {
		super(output);
		this.target = target;
		this.materials = new ILMaterials(target);
		this.available = ILBlocks.AVAILABLE_LADDERS.get(target, LoaderTarget.COMMON).stream()
				.map(Block::asItem).collect(Collectors.toSet());
	}
	
	@Override
	public void buildRecipes(Consumer<FinishedRecipe> consumer) {
		this.consumer = consumer;
		addRecipes();
	}
	
	protected void addRecipes(){
		commonRecipes();
	}

	private static final int BASE_AMOUNT = 8; 
	
	protected void commonRecipes(){
		
		// Waxed
		waxed(ILBlocks.COPPER_LADDER.get(), ILBlocks.WAXED_COPPER_LADDER.get());
		waxed(ILBlocks.EXPOSED_COPPER_LADDER.get(), ILBlocks.WAXED_EXPOSED_COPPER_LADDER.get());
		waxed(ILBlocks.WEATHERED_COPPER_LADDER.get(), ILBlocks.WAXED_WEATHERED_COPPER_LADDER.get());
		waxed(ILBlocks.OXIDIZED_COPPER_LADDER.get(), ILBlocks.WAXED_OXIDIZED_COPPER_LADDER.get());
		
		// Copper
		ladder(materials.COPPER_INGOT, ILBlocks.COPPER_LADDER.get(), BASE_AMOUNT);
		ladder(materials.TIN_INGOT, ILBlocks.TIN_LADDER.get(), BASE_AMOUNT);
		
		// Iron
		ladder(materials.IRON_INGOT, ILBlocks.IRON_LADDER.get(), BASE_AMOUNT);
		ladder(materials.BRONZE_INGOT, ILBlocks.BRONZE_LADDER.get(), BASE_AMOUNT);
		ladder(materials.LEAD_INGOT, ILBlocks.LEAD_LADDER.get(), BASE_AMOUNT);
		ladder(materials.ALUMINUM_INGOT, ILBlocks.ALUMINIUM_LADDER.get(), BASE_AMOUNT);
		ladder(materials.SILVER_INGOT, ILBlocks.SILVER_LADDER.get(), BASE_AMOUNT);
		ladder(materials.STEEL_INGOT, ILBlocks.STEEL_LADDER.get(), BASE_AMOUNT);
		
		// Gold
		ladder(materials.GOLD_INGOT, ILBlocks.GOLDEN_LADDER.get(), BASE_AMOUNT);
		
		// Diamond
		ladder(materials.PLATINUM_INGOT, ILBlocks.PLATINUM_LADDER.get(), BASE_AMOUNT);
		ladder(materials.DIAMOND, ILBlocks.DIAMOND_LADDER.get(), BASE_AMOUNT);
		
		// Extra materials
		ladder(materials.CHROMIUM_INGOT, ILBlocks.CHROMIUM_LADDER.get(), BASE_AMOUNT);
		ladder(materials.ADVANCED_ALLOY_INGOT, ILBlocks.ADVANCED_ALLOY_LADDER.get(), BASE_AMOUNT);
		ladder(materials.NICKEL_INGOT, ILBlocks.NICKEL_LADDER.get(), BASE_AMOUNT);
		ladder(materials.TUNGSTEN_INGOT, ILBlocks.TUNGSTEN_LADDER.get(), BASE_AMOUNT);
		ladder(materials.TUNGSTEN_STEEL_INGOT, ILBlocks.TUNGSTEN_STEEL_LADDER.get(), BASE_AMOUNT);
		ladder(materials.TITANIUM_INGOT, ILBlocks.TITANIUM_LADDER.get(), BASE_AMOUNT);
		ladder(materials.ZINC_INGOT, ILBlocks.ZINC_LADDER.get(), BASE_AMOUNT);
		ladder(materials.INVAR_INGOT, ILBlocks.INVAR_LADDER.get(), BASE_AMOUNT);
		ladder(materials.ELECTRUM_INGOT, ILBlocks.ELECTRUM_LADDER.get(), BASE_AMOUNT);
		
		// Netherite
		ladder(materials.NETHERITE, ILBlocks.NETHERITE_LADDER.get(), BASE_AMOUNT * 4);
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ILBlocks.NETHERITE_LADDER.get(), BASE_AMOUNT)
				.define('G', materials.GOLD_INGOT.ingredient())
				.define('S', materials.NETHERITE_SCRAP.ingredient())
				.define('L', Ingredient.of(Items.LADDER))
				.pattern("SL").pattern("LG")
				.unlockedBy("has_material", has(materials.GOLD_INGOT))
				.save(consumer, new ResourceLocation(ILCommon.MODID,
						RecipeProvider.getItemName(ILBlocks.NETHERITE_LADDER.get()) + "_another_variant"));
		
		// Custom
		ladder(materials.OBSIDIAN, ILBlocks.OBSIDIAN_LADDER.get(), BASE_AMOUNT);
		ladder(materials.CRYING_OBSIDIAN, ILBlocks.CRYING_OBSIDIAN_LADDER.get(), BASE_AMOUNT, 
				has(ILBlocks.CRYING_OBSIDIAN_LADDER.get()));

		// Items
		ShapedRecipeBuilder
				.shaped(RecipeCategory.MISC, ILItems.MORPH_TOOL.get(), 1)
				.define('S', materials.SLIME_BALL.ingredient())
				.define('P', materials.PAPPER.ingredient())
				.define('L', Items.LADDER)
				.pattern(" PS").pattern(" PP").pattern("L  ")
				.unlockedBy("has_material", has(materials.SLIME_BALL))
				.save(consumer, new ResourceLocation(ILCommon.MODID, RecipeProvider.getItemName(ILItems.MORPH_TOOL.get())));
		
		ShapedRecipeBuilder
				.shaped(RecipeCategory.MISC, ILItems.SENSOR_TOOL.get(), 1)
				.define('D', materials.REDSTONE_DUST.ingredient())
				.define('T', materials.REDSTONE_TORCH.ingredient())
				.define('O', materials.OBSERVER.ingredient())
				.define('L', Items.LADDER)
				.pattern(" TO").pattern(" DD").pattern("L  ")
				.unlockedBy("has_material", has(materials.REDSTONE_TORCH))
				.save(consumer, new ResourceLocation(ILCommon.MODID, RecipeProvider.getItemName(ILItems.SENSOR_TOOL.get())));
		
		ShapedRecipeBuilder
				.shaped(RecipeCategory.MISC, ILItems.STYLER_TOOL.get(), 1)
				.define('B', materials.LAPIS.ingredient())
				.define('T', materials.TRIM_TEMPLATE.ingredient())
				.define('L', Items.LADDER)
				.pattern(" BT").pattern(" BB").pattern("L  ")
				.unlockedBy("has_material", has(materials.TRIM_TEMPLATE))
				.save(consumer, new ResourceLocation(ILCommon.MODID, RecipeProvider.getItemName(ILItems.STYLER_TOOL.get())));
		
		ShapedRecipeBuilder
				.shaped(RecipeCategory.MISC, ILItems.LIGHT_TOOL.get(), 1)
				.define('D', materials.GLOWSTONE_DUST.ingredient())
				.define('B', materials.GLOWSTONE.ingredient())
				.define('L', Items.LADDER)
				.pattern(" DB").pattern(" DD").pattern("L  ")
				.unlockedBy("has_material", has(materials.GLOWSTONE_DUST))
				.save(consumer, new ResourceLocation(ILCommon.MODID, RecipeProvider.getItemName(ILItems.LIGHT_TOOL.get())));
		
		ShapedRecipeBuilder
				.shaped(RecipeCategory.MISC, ILItems.CASING_TOOL.get(), 1)
				.define('D', materials.DYE.ingredient())
				.define('G', materials.GLASS_BLOCK.ingredient())
				.define('L', Items.LADDER)
				.pattern(" GD").pattern(" GG").pattern("L  ")
				.unlockedBy("has_material", has(materials.GLASS_BLOCK))
				.save(consumer, new ResourceLocation(ILCommon.MODID, RecipeProvider.getItemName(ILItems.CASING_TOOL.get())));
	}
	
	private boolean isAvailable(ItemLike itemLike) {
		return itemLike != null && available.contains(itemLike.asItem());
	}
	
	protected void ladder(ILMaterial material, ItemLike result, int amount) {
		ladder(material, Ingredient.of(Items.LADDER), result, amount, has(material));
	}
	
	protected void ladder(ILMaterial material, ItemLike result, int amount, CriterionTriggerInstance criterion) {
		ladder(material, Ingredient.of(Items.LADDER), result, amount, criterion);
	}
	
	protected void ladder(ILMaterial material, Ingredient ladderBefore, ItemLike result, int amount,
						  CriterionTriggerInstance criterionTrigger
	) {
		if (!isAvailable(result)) return;
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, amount)
				.define('M', material.ingredient())
				.define('L', ladderBefore)
				.pattern("LLL").pattern("LML").pattern("LLL")
				.unlockedBy("has_material", criterionTrigger)
				.save(consumer, new ResourceLocation(ILCommon.MODID,
						RecipeProvider.getItemName(result)));
	}
	
	public CriterionTriggerInstance has(ILMaterial material){
		if (material instanceof ILItemMaterial itemMaterial){
			return has(itemMaterial.item());
		}
		else if(material instanceof ILTagMaterial tagMaterial){
			return has(tagMaterial.tag());
		}
		return null;
	}
	
	protected void waxed(ItemLike before, ItemLike after){
		if (!isAvailable(before) || !isAvailable(after)) return;
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, after)
				.requires(before)
				.requires(Items.HONEYCOMB)
				.group(getItemName(after))
				.unlockedBy(getHasName(before), has(before))
				.save(consumer, getConversionRecipeName(after, Items.HONEYCOMB));
	}
	
	public static class Fabric extends ILRecipeProvider {
		
		public Fabric(FabricDataOutput output) {
			super(output, LoaderTarget.FABRIC);
		}
		
	}
	
	public static class Forge extends ILRecipeProvider {
		
		public Forge(FabricDataOutput output) {
			super(output, LoaderTarget.FORGE);
		}
	}
	
	
	
}
