package com.nine.ironladders.datagen;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.TagHelper;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ILRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ILRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {


        //Aluminum
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.ALUMINUM_LADDER.get(), 35).define('L', TagHelper.ALUMINUM).pattern("L L").pattern("LLL").pattern("L L").unlockedBy("has_material", has(TagHelper.ALUMINUM)).save(consumer, new ResourceLocation(IronLadders.MODID, VanillaRecipeProvider.getItemName(BlockRegistry.ALUMINUM_LADDER.get())));

        //Copper
        ladder(TagHelper.COPPER, BlockRegistry.COPPER_LADDER.get(), Items.LADDER, consumer);
        waxedLadder(BlockRegistry.WAXED_COPPER_LADDER.get(), BlockRegistry.COPPER_LADDER.get(), consumer);
        waxedLadder(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get(), BlockRegistry.EXPOSED_COPPER_LADDER.get(), consumer);
        waxedLadder(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get(), BlockRegistry.WEATHERED_COPPER_LADDER.get(), consumer);
        waxedLadder(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get(), BlockRegistry.OXIDIZED_COPPER_LADDER.get(), consumer);
        ladderUpgrade(TagHelper.COPPER, ItemRegistry.COPPER_UPGRADE.get(), consumer);

        ladder(TagHelper.TIN, BlockRegistry.TIN_LADDER.get(), Blocks.LADDER, consumer);
        ladderUpgrade(TagHelper.TIN, ItemRegistry.WOOD_TIN_UPGRADE.get(), consumer);

        ladder(TagHelper.BRONZE, BlockRegistry.BRONZE_LADDER.get(), Blocks.LADDER, consumer);
        ladderUpgrade(TagHelper.BRONZE, ItemRegistry.WOOD_BRONZE_UPGRADE.get(), consumer);

        //Iron
        ironLevelLadderRecipes(BlockRegistry.IRON_LADDER.get(), TagHelper.IRON, consumer);
        ladderUpgrade(TagHelper.IRON, ItemRegistry.IRON_UPGRADE.get(), consumer);
        ironLevelUpgradeRecipes(ItemRegistry.WOOD_IRON_UPGRADE.get(), TagHelper.IRON, consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.WOOD_IRON_UPGRADE.get(), 1).define('I', TagHelper.IRON).define('L', Items.LADDER).pattern("III").pattern(" II").pattern("L I").group("wood_iron_upgrade").unlockedBy("has_ingot", has(TagHelper.IRON)).save(consumer, new ResourceLocation(IronLadders.MODID, "iron_upgrade_from_ladder"));

        ironLevelLadderRecipes(BlockRegistry.LEAD_LADDER.get(), TagHelper.LEAD, consumer);
        ironLevelUpgradeRecipes(ItemRegistry.WOOD_LEAD_UPGRADE.get(), TagHelper.LEAD, consumer);

        ironLevelLadderRecipes(BlockRegistry.STEEL_LADDER.get(), TagHelper.STEEL, consumer);
        ironLevelUpgradeRecipes(ItemRegistry.WOOD_STEEL_UPGRADE.get(), TagHelper.STEEL, consumer);

        //Gold
        ladderUpgrade(TagHelper.GOLD, ItemRegistry.GOLD_UPGRADE.get(), consumer);
        goldLevelLadderRecipes(BlockRegistry.GOLD_LADDER.get(), TagHelper.GOLD, consumer);
        goldLevelUpgradeRecipes(ItemRegistry.WOOD_GOLD_UPGRADE.get(), TagHelper.GOLD, consumer);

        goldLevelLadderRecipes(BlockRegistry.SILVER_LADDER.get(), TagHelper.SILVER, consumer);
        goldLevelUpgradeRecipes(ItemRegistry.WOOD_SILVER_UPGRADE.get(), TagHelper.SILVER, consumer);

        //Diamond
        diamondLevelLadderRecipes(BlockRegistry.DIAMOND_LADDER.get(), TagHelper.DIAMOND, consumer);
        ladderUpgrade(TagHelper.DIAMOND, ItemRegistry.DIAMOND_UPGRADE.get(), consumer);
        diamondLevelUpgradeRecipes(ItemRegistry.WOOD_DIAMOND_UPGRADE.get(), TagHelper.DIAMOND, consumer);


        //Obsidian
        ladder(TagHelper.OBSIDIAN, BlockRegistry.OBSIDIAN_LADDER.get(), BlockRegistry.DIAMOND_LADDER.get(), consumer);
        ladderUpgrade(TagHelper.OBSIDIAN, ItemRegistry.OBSIDIAN_UPGRADE.get(), consumer);
        ladderWoodUpgrade(TagHelper.OBSIDIAN, ItemRegistry.WOOD_OBSIDIAN_UPGRADE.get(), ItemRegistry.WOOD_DIAMOND_UPGRADE.get(), consumer);

        //Secret recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CRYING_OBSIDIAN_LADDER.get(), 9).define('I', Blocks.CRYING_OBSIDIAN).define('L', BlockRegistry.OBSIDIAN_LADDER.get()).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_ladder", has(TagHelper.FAKE_TAG)).save(consumer, new ResourceLocation(IronLadders.MODID, "secret_ladder_recipe"));

        //Netherite
        ladder(TagHelper.NETHERITE, BlockRegistry.NETHERITE_LADDER.get(), BlockRegistry.DIAMOND_LADDER.get(), consumer, 14);
        ladderWoodUpgrade(TagHelper.NETHERITE, ItemRegistry.WOOD_NETHERITE_UPGRADE.get(), ItemRegistry.WOOD_DIAMOND_UPGRADE.get(), consumer);
        ladderUpgrade(TagHelper.NETHERITE, ItemRegistry.NETHERITE_UPGRADE.get(), consumer);

        //Special
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.POWER_UPGRADE_ITEM.get(), 1).define('R', TagHelper.REDSTONE).define('L', Items.LADDER).define('P', Items.PISTON).define('D', Items.REPEATER).define('T', Items.REDSTONE_TORCH).pattern(" DT").pattern(" RP").pattern("L  ").unlockedBy("has_redstone", has(TagHelper.REDSTONE)).unlockedBy("has_repeater", has(Items.REPEATER)).unlockedBy("has_piston", has(Items.PISTON)).unlockedBy("has_redstone_torch", has(Items.REDSTONE_TORCH)).save(consumer, new ResourceLocation(IronLadders.MODID, "power_upgrade"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MORPH_UPGRADE_ITEM.get(), 1).define('S', TagHelper.SLIME).define('L', Items.LADDER).define('P', Items.PAPER).pattern(" SP").pattern(" SS").pattern("L  ").unlockedBy("has_slime", has(TagHelper.SLIME)).save(consumer, new ResourceLocation(IronLadders.MODID, "morph_upgrade"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.HIDE_UPGRADE_ITEM.get(), 1).define('G', TagHelper.GLASS).define('L', Items.LADDER).define('D', TagHelper.WHITE_DYE).pattern(" GD").pattern(" GG").pattern("L  ").unlockedBy("has_dye", has(TagHelper.WHITE_DYE)).save(consumer, new ResourceLocation(IronLadders.MODID, "hide_upgrade"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.LIGHT_UPGRADE_ITEM.get(), 1).define('D', TagHelper.GLOWSTONE_DUST).define('L', Items.LADDER).define('B', Items.GLOWSTONE).pattern(" DB").pattern(" DD").pattern("L  ").unlockedBy("has_glowstone", has(TagHelper.GLOWSTONE_DUST)).save(consumer, new ResourceLocation(IronLadders.MODID, "glow_upgrade"));
    }

    private static void waxedLadder(ItemLike result, ItemLike ladderBefore, Consumer<FinishedRecipe> consumer){
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, result).requires(ladderBefore).requires(Items.HONEYCOMB).group("ladders_waxing").unlockedBy("has_ladder", has(ladderBefore)).save(consumer,new ResourceLocation(IronLadders.MODID, getItemName(result)));;
    }

    private static void ladder(TagKey<Item> material, ItemLike result, ItemLike ladderBefore, Consumer<FinishedRecipe> consumer) {
        ladder(material, result, ladderBefore, consumer, 7);
    }

    private static void ladder(TagKey<Item> material, ItemLike result, ItemLike ladderBefore, Consumer<FinishedRecipe> consumer, int amount) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, amount).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").group(getItemName(result)).unlockedBy("has_material", has(material)).unlockedBy("has_ladder", has(ladderBefore)).save(consumer, new ResourceLocation(IronLadders.MODID, VanillaRecipeProvider.getItemName(result)));
    }

    private static void ladder(TagKey<Item> material, ItemLike result, TagKey<Item> ladderBefore, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 7).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").group(getItemName(result)).unlockedBy("has_material", has(material)).unlockedBy("has_ladder", has(ladderBefore)).save(consumer, new ResourceLocation(IronLadders.MODID, VanillaRecipeProvider.getItemName(result)));
    }

    private static void ladderAlternative(TagKey<Item> material, ItemLike result, ItemLike ladderBefore, Consumer<FinishedRecipe> consumer, String name){
        ladderAlternative(material, result, ladderBefore, consumer, 7, name);
    }

    private static void ladderAlternative(TagKey<Item> material, ItemLike result, ItemLike ladderBefore, Consumer<FinishedRecipe> consumer, int amount, String name){
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, amount).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_material", has(material)).group(getItemName(result)).unlockedBy("has_ladder", has(ladderBefore)).save(consumer, new ResourceLocation(IronLadders.MODID, VanillaRecipeProvider.getItemName(result) + name));
    }

    private static void ladderUpgrade(TagKey<Item> material, ItemLike result, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 1).define('I', material).define('R', Blocks.LADDER).pattern(" II").pattern(" II").pattern("R  ").group(getItemName(result)).unlockedBy("has_material", has(material)).save(consumer, new ResourceLocation(IronLadders.MODID, VanillaRecipeProvider.getItemName(result)));
    }

    private static void ladderWoodUpgrade(TagKey<Item> material, ItemLike result, ItemLike previous, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 1).define('I', material).define('R', previous).pattern(" II").pattern(" II").pattern("R  ").group(getItemName(result)).unlockedBy("has_material", has(previous)).unlockedBy("has_previous", has(material)).save(consumer, new ResourceLocation(IronLadders.MODID, VanillaRecipeProvider.getItemName(result) + "_from_" + VanillaRecipeProvider.getItemName(previous)));
    }

    private static void ironLevelUpgradeRecipes(ItemLike result, TagKey<Item> material , Consumer<FinishedRecipe> consumer){
        ladderWoodUpgrade(material, result, ItemRegistry.COPPER_UPGRADE.get(), consumer);
        ladderWoodUpgrade(material, result, ItemRegistry.WOOD_BRONZE_UPGRADE.get(), consumer);
        ladderWoodUpgrade(material, result, ItemRegistry.WOOD_TIN_UPGRADE.get(), consumer);
    }

    private static void ironLevelLadderRecipes(ItemLike result, TagKey<Item> material , Consumer<FinishedRecipe> consumer){
        ladder(material, result, TagHelper.COPPER_LADDER, consumer);
        ladderAlternative(material, result, BlockRegistry.TIN_LADDER.get(), consumer, "_from_tin_ladder");
        ladderAlternative(material, result, BlockRegistry.BRONZE_LADDER.get(), consumer, "_from_bronze_ladder");
    }

    private static void goldLevelUpgradeRecipes(ItemLike result, TagKey<Item> material , Consumer<FinishedRecipe> consumer){
        ladderWoodUpgrade(material, result, ItemRegistry.WOOD_IRON_UPGRADE.get(), consumer);
        ladderWoodUpgrade(material, result, ItemRegistry.WOOD_LEAD_UPGRADE.get(), consumer);
        ladderWoodUpgrade(material, result, ItemRegistry.WOOD_STEEL_UPGRADE.get(), consumer);
    }

    private static void goldLevelLadderRecipes(ItemLike result, TagKey<Item> material , Consumer<FinishedRecipe> consumer){
        ladder(material, result, BlockRegistry.IRON_LADDER.get(), consumer);
        ladderAlternative(material, result, BlockRegistry.LEAD_LADDER.get(), consumer, "_from_lead_ladder");
        ladderAlternative(material, result, BlockRegistry.STEEL_LADDER.get(), consumer, "_from_steel_ladder");
    }

    private static void diamondLevelUpgradeRecipes(ItemLike result, TagKey<Item> material , Consumer<FinishedRecipe> consumer){
        ladderWoodUpgrade(material, result, ItemRegistry.WOOD_GOLD_UPGRADE.get(), consumer);
        ladderWoodUpgrade(material, result, ItemRegistry.WOOD_SILVER_UPGRADE.get(), consumer);
    }

    private static void diamondLevelLadderRecipes(ItemLike result, TagKey<Item> material , Consumer<FinishedRecipe> consumer){
        ladder(material, result, BlockRegistry.GOLD_LADDER.get(), consumer);
        ladderAlternative(material, result, BlockRegistry.SILVER_LADDER.get(), consumer, "_from_silver_ladder");
    }

}
