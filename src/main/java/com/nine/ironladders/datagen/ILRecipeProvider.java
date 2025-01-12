package com.nine.ironladders.datagen;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.LadderTags;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ILRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ILRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(pOutput, provider);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        TagKey<Item> copper = ItemTags.create(ResourceLocation.parse("c:ingots/copper"));
        TagKey<Item> iron = ItemTags.create(ResourceLocation.parse("c:ingots/iron"));
        TagKey<Item> gold = ItemTags.create(ResourceLocation.parse("c:ingots/gold"));
        TagKey<Item> diamond = ItemTags.create(ResourceLocation.parse("c:gems/diamond"));
        TagKey<Item> netherite = ItemTags.create(ResourceLocation.parse("c:ingots/netherite"));
        TagKey<Item> redstone = ItemTags.create(ResourceLocation.parse("c:dusts/redstone"));
        TagKey<Item> slime = ItemTags.create(ResourceLocation.parse("c:slimeballs"));
        TagKey<Item> glass = ItemTags.create(ResourceLocation.parse("c:glass_blocks/cheap"));
        TagKey<Item> whiteDye = ItemTags.create(ResourceLocation.parse("c:dyes/white"));
        TagKey<Item> glowstoneDust = ItemTags.create(ResourceLocation.parse("c:dusts/glowstone"));
        TagKey<Item> obsidian = ItemTags.create(ResourceLocation.parse("c:obsidians"));

        TagKey<Item> steel = ItemTags.create(ResourceLocation.parse("c:ingots/steel"));
        TagKey<Item> lead = ItemTags.create(ResourceLocation.parse("c:ingots/lead"));
        TagKey<Item> tin = ItemTags.create(ResourceLocation.parse("c:ingots/tin"));
        TagKey<Item> bronze = ItemTags.create(ResourceLocation.parse("c:ingots/bronze"));
        TagKey<Item> silver = ItemTags.create(ResourceLocation.parse("c:ingots/silver"));
        TagKey<Item> aluminum = ItemTags.create(ResourceLocation.parse("c:ingots/aluminum"));

        TagKey<Item> upgradesToIronUpgrade = LadderTags.UPGRADES_TO_IRON_UPGRADE;
        TagKey<Item> upgradesToGoldUpgrade = LadderTags.UPGRADES_TO_GOLD_UPGRADE;
        TagKey<Item> upgradesToDiamondUpgrade = LadderTags.UPGRADES_TO_DIAMOND_UPGRADE;
        TagKey<Item> upgradesToObsidianUpgrade = LadderTags.UPGRADES_TO_OBSIDIAN_UPGRADE;
        TagKey<Item> upgradesToNetheriteUpgrade = LadderTags.UPGRADES_TO_NETHERITE_UPGRADE;

        TagKey<Item> upgradesToIron = LadderTags.UPGRADES_TO_IRON;
        TagKey<Item> upgradesToGold = LadderTags.UPGRADES_TO_GOLD;
        TagKey<Item> upgradesToDiamond = LadderTags.UPGRADES_TO_DIAMOND;
        TagKey<Item> upgradesToObsidian = LadderTags.UPGRADES_TO_OBSIDIAN;
        TagKey<Item> upgradesToNetherite = LadderTags.UPGRADES_TO_NETHERITE;

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.IRON_LADDER.get(), 7).define('I', iron).define('L', Items.LADDER).pattern("LIL").pattern("LIL").pattern("LIL").unlockedBy("has_ingot", has(iron)).save(consumer, ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, "iron_ladder_from_regular_one"));

        ladder(copper, BlockRegistry.COPPER_LADDER.get(), Items.LADDER, consumer);
        ladder(iron, BlockRegistry.IRON_LADDER.get(), upgradesToIron, consumer);
        ladder(gold, BlockRegistry.GOLD_LADDER.get(), upgradesToGold, consumer);
        ladder(diamond, BlockRegistry.DIAMOND_LADDER.get(), upgradesToDiamond, consumer);
        ladder(Blocks.OBSIDIAN.asItem(), BlockRegistry.OBSIDIAN_LADDER.get(), upgradesToObsidian, consumer);
        ladder(netherite, BlockRegistry.NETHERITE_LADDER.get(), upgradesToNetherite, consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.WAXED_COPPER_LADDER.get()).requires(BlockRegistry.COPPER_LADDER.get()).requires(Items.HONEYCOMB).group("ladders_waxing").unlockedBy("has_ladder", has(BlockRegistry.COPPER_LADDER.get())).save(consumer,ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, getItemName(BlockRegistry.WAXED_COPPER_LADDER.get()) + "_from_" + getItemName(BlockRegistry.COPPER_LADDER.get())));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get()).requires(BlockRegistry.EXPOSED_COPPER_LADDER.get()).requires(Items.HONEYCOMB).group("ladders_waxing").unlockedBy("has_ladder", has(BlockRegistry.EXPOSED_COPPER_LADDER.get())).save(consumer,ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, getItemName(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get()) + "_from_" + getItemName(BlockRegistry.EXPOSED_COPPER_LADDER.get())));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get()).requires(BlockRegistry.WEATHERED_COPPER_LADDER.get()).requires(Items.HONEYCOMB).group("ladders_waxing").unlockedBy("has_ladder", has(BlockRegistry.WEATHERED_COPPER_LADDER.get())).save(consumer,ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, getItemName(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get()) + "_from_" + getItemName(BlockRegistry.WEATHERED_COPPER_LADDER.get())));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get()).requires(BlockRegistry.OXIDIZED_COPPER_LADDER.get()).requires(Items.HONEYCOMB).group("ladders_waxing").unlockedBy("has_ladder", has(BlockRegistry.OXIDIZED_COPPER_LADDER.get())).save(consumer,ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, getItemName(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get()) + "_from_" + getItemName(BlockRegistry.OXIDIZED_COPPER_LADDER.get())));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.WOOD_IRON_UPGRADE.get(), 1).define('I', iron).define('L', Items.LADDER).pattern("III").pattern(" II").pattern("L I").unlockedBy("has_ingot", has(iron)).save(consumer,ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, "iron_upgrade_from_ladder"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.POWER_UPGRADE_ITEM.get(), 1).define('R', redstone).define('L', Items.LADDER).define('P', Items.PISTON).define('D', Items.REPEATER).define('T', Items.REDSTONE_TORCH).pattern(" DT").pattern(" RP").pattern("L  ").unlockedBy("has_redstone", has(redstone)).unlockedBy("has_repeater", has(Items.REPEATER)).unlockedBy("has_piston", has(Items.PISTON)).unlockedBy("has_redstone_torch", has(Items.REDSTONE_TORCH)).save(consumer,ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, "power_upgrade"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MORPH_UPGRADE_ITEM.get(), 1).define('S', slime).define('L', Items.LADDER).define('P', Items.PAPER).pattern(" SP").pattern(" SS").pattern("L  ").unlockedBy("has_slime", has(slime)).save(consumer,ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, "morph_upgrade"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.HIDE_UPGRADE_ITEM.get(), 1).define('G', glass).define('L', Items.LADDER).define('D', whiteDye).pattern(" GD").pattern(" GG").pattern("L  ").unlockedBy("has_dye", has(whiteDye)).save(consumer,ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, "hide_upgrade"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.LIGHT_UPGRADE_ITEM.get(), 1).define('D', glowstoneDust).define('L', Items.LADDER).define('B', Items.GLOWSTONE).pattern(" DB").pattern(" DD").pattern("L  ").unlockedBy("has_glowstone", has(glowstoneDust)).save(consumer,ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, "glow_upgrade"));

        ladderWoodUpgrade(iron, ItemRegistry.WOOD_IRON_UPGRADE.get(), upgradesToIronUpgrade, consumer);
        ladderWoodUpgrade(gold, ItemRegistry.WOOD_GOLD_UPGRADE.get(), upgradesToGoldUpgrade, consumer);
        ladderWoodUpgrade(diamond, ItemRegistry.WOOD_DIAMOND_UPGRADE.get(), upgradesToDiamondUpgrade, consumer);
        ladderWoodUpgrade(obsidian, ItemRegistry.WOOD_OBSIDIAN_UPGRADE.get(), upgradesToObsidianUpgrade, consumer);
        ladderWoodUpgrade(netherite, ItemRegistry.WOOD_NETHERITE_UPGRADE.get(), upgradesToNetheriteUpgrade, consumer);

        ladderUpgrade(copper, ItemRegistry.COPPER_UPGRADE.get(), consumer);
        ladderUpgrade(iron, ItemRegistry.IRON_UPGRADE.get(), consumer);
        ladderUpgrade(gold, ItemRegistry.GOLD_UPGRADE.get(), consumer);
        ladderUpgrade(diamond, ItemRegistry.DIAMOND_UPGRADE.get(), consumer);
        ladderUpgrade(obsidian, ItemRegistry.OBSIDIAN_UPGRADE.get(), consumer);
        ladderUpgrade(netherite, ItemRegistry.NETHERITE_UPGRADE.get(), consumer);

        // Secret recipe

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CRYING_OBSIDIAN_LADDER.get(), 9).define('I', Blocks.CRYING_OBSIDIAN).define('L', BlockRegistry.OBSIDIAN_LADDER.get()).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_ladder", has(LadderTags.UNKNOWN)).save(consumer,ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, "secret_ladder_recipe"));

        //Additional ladders recipes (from non-vanilla materials)

        ladderUpgrade(lead, ItemRegistry.WOOD_LEAD_UPGRADE.get(), consumer);
        ladderWoodUpgrade(steel, ItemRegistry.WOOD_STEEL_UPGRADE.get(), upgradesToIronUpgrade, consumer);
        ladderWoodUpgrade(bronze, ItemRegistry.WOOD_BRONZE_UPGRADE.get(), upgradesToIronUpgrade, consumer);
        ladderUpgrade(tin, ItemRegistry.WOOD_TIN_UPGRADE.get(), consumer);
        ladderWoodUpgrade(silver, ItemRegistry.WOOD_SILVER_UPGRADE.get(), upgradesToGoldUpgrade, consumer);
        ladderUpgrade(aluminum, ItemRegistry.WOOD_ALUMINUM_UPGRADE.get(), consumer);

        ladder(steel, BlockRegistry.STEEL_LADDER.get(), upgradesToIron, consumer);
        ladder(lead, BlockRegistry.LEAD_LADDER.get(), Items.LADDER, consumer);
        ladder(tin, BlockRegistry.TIN_LADDER.get(), Items.LADDER, consumer);
        ladder(silver, BlockRegistry.SILVER_LADDER.get(), upgradesToGold, consumer);
        ladder(bronze, BlockRegistry.BRONZE_LADDER.get(), upgradesToIron, consumer);
        ladder(aluminum, BlockRegistry.ALUMINUM_LADDER.get(), Items.LADDER, consumer);

    }
    private static void ladder(TagKey<Item> material, ItemLike result, ItemLike ladderBefore, RecipeOutput consumer){
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 7).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_material", has(material)).unlockedBy("has_ladder", has(ladderBefore)).save(consumer, ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, VanillaRecipeProvider.getItemName(result)));
    }
    private static void ladder(TagKey<Item> material, ItemLike result, TagKey<Item> ladderBefore, RecipeOutput consumer){
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 7).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_material", has(material)).unlockedBy("has_ladder", has(ladderBefore)).save(consumer, ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, VanillaRecipeProvider.getItemName(result)));
    }
    private static void ladder(Item material, ItemLike result, TagKey<Item> ladderBefore, RecipeOutput consumer){
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 7).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_material", has(material)).unlockedBy("has_ladder", has(ladderBefore)).save(consumer, ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, VanillaRecipeProvider.getItemName(result)));
    }
    private static void ladder(Item material, ItemLike result, Item ladderBefore, RecipeOutput consumer){
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 7).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_material", has(material)).unlockedBy("has_ladder", has(ladderBefore)).save(consumer, ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, VanillaRecipeProvider.getItemName(result)));
    }
    private static void ladderUpgrade(TagKey<Item> material, ItemLike result, RecipeOutput consumer){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 1).define('I', material).define('R', Blocks.LADDER).pattern(" II").pattern(" II").pattern("R  ").unlockedBy("has_material", has(material)).save(consumer, ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, VanillaRecipeProvider.getItemName(result)));
    }
    private static void ladderWoodUpgrade(TagKey<Item> material, ItemLike result, TagKey<Item> upgradeBefore, RecipeOutput consumer){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 1).define('I', material).define('L', upgradeBefore).pattern(" II").pattern(" II").pattern("L  ").unlockedBy("has_material", has(material)).save(consumer, ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, "wood_" + VanillaRecipeProvider.getItemName(result)));
    }
}
