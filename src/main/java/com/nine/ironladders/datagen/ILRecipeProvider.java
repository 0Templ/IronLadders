package com.nine.ironladders.datagen;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.LadderTags;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ILRecipeProvider extends RecipeProvider {

    public ILRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {

        TagKey<Item> copper = ItemTags.create(ResourceLocation.parse("c:ingots/copper"));
        TagKey<Item> iron = ItemTags.create(ResourceLocation.parse("c:ingots/iron"));
        TagKey<Item> gold = ItemTags.create(ResourceLocation.parse("c:ingots/gold"));
        TagKey<Item> diamond = ItemTags.create(ResourceLocation.parse("c:gems/diamond"));
        TagKey<Item> netherite = ItemTags.create(ResourceLocation.parse("c:ingots/netherite"));
        TagKey<Item> redstone = ItemTags.create(ResourceLocation.parse("c:dusts/redstone"));
        TagKey<Item> slime = ItemTags.create(ResourceLocation.parse("c:slime_balls"));
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

        this.shaped(RecipeCategory.DECORATIONS, BlockRegistry.IRON_LADDER.get(), 7).define('I', iron).define('L', Items.LADDER).pattern("LIL").pattern("LIL").pattern("LIL").unlockedBy("has_ingot", has(iron)).save(this.output,  getRecipeId(IronLadders.MODID, "iron_ladder_from_regular_one"));

        ladder(copper, BlockRegistry.COPPER_LADDER.get(), Items.LADDER);
        ladder(iron, BlockRegistry.IRON_LADDER.get(), upgradesToIron);
        ladder(gold, BlockRegistry.GOLD_LADDER.get(), upgradesToGold);
        ladder(diamond, BlockRegistry.DIAMOND_LADDER.get(), upgradesToDiamond);
        ladder(Blocks.OBSIDIAN.asItem(), BlockRegistry.OBSIDIAN_LADDER.get(), upgradesToObsidian);
        ladder(netherite, BlockRegistry.NETHERITE_LADDER.get(), upgradesToNetherite);

        this.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.WAXED_COPPER_LADDER.get()).requires(BlockRegistry.COPPER_LADDER.get()).requires(Items.HONEYCOMB).group("ladders_waxing").unlockedBy("has_ladder", has(BlockRegistry.COPPER_LADDER.get())).save(this.output, getRecipeId(IronLadders.MODID, getItemName(BlockRegistry.WAXED_COPPER_LADDER.get()) + "_from_" + getItemName(BlockRegistry.COPPER_LADDER.get())));
        this.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get()).requires(BlockRegistry.EXPOSED_COPPER_LADDER.get()).requires(Items.HONEYCOMB).group("ladders_waxing").unlockedBy("has_ladder", has(BlockRegistry.EXPOSED_COPPER_LADDER.get())).save(this.output, getRecipeId(IronLadders.MODID, getItemName(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get()) + "_from_" + getItemName(BlockRegistry.EXPOSED_COPPER_LADDER.get())));
        this.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get()).requires(BlockRegistry.WEATHERED_COPPER_LADDER.get()).requires(Items.HONEYCOMB).group("ladders_waxing").unlockedBy("has_ladder", has(BlockRegistry.WEATHERED_COPPER_LADDER.get())).save(this.output, getRecipeId(IronLadders.MODID, getItemName(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get()) + "_from_" + getItemName(BlockRegistry.WEATHERED_COPPER_LADDER.get())));
        this.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get()).requires(BlockRegistry.OXIDIZED_COPPER_LADDER.get()).requires(Items.HONEYCOMB).group("ladders_waxing").unlockedBy("has_ladder", has(BlockRegistry.OXIDIZED_COPPER_LADDER.get())).save(this.output, getRecipeId(IronLadders.MODID, getItemName(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get()) + "_from_" + getItemName(BlockRegistry.OXIDIZED_COPPER_LADDER.get())));

        this.shaped(RecipeCategory.MISC, ItemRegistry.WOOD_IRON_UPGRADE.get(), 1).define('I', iron).define('L', Items.LADDER).pattern("III").pattern(" II").pattern("L I").unlockedBy("has_ingot", has(iron)).save(this.output, getRecipeId(IronLadders.MODID, "iron_upgrade_from_ladder"));
        this.shaped(RecipeCategory.MISC, ItemRegistry.POWER_UPGRADE_ITEM.get(), 1).define('R', redstone).define('L', Items.LADDER).define('P', Items.PISTON).define('D', Items.REPEATER).define('T', Items.REDSTONE_TORCH).pattern(" DT").pattern(" RP").pattern("L  ").unlockedBy("has_redstone", has(redstone)).unlockedBy("has_repeater", has(Items.REPEATER)).unlockedBy("has_piston", has(Items.PISTON)).unlockedBy("has_redstone_torch", has(Items.REDSTONE_TORCH)).save(this.output);
        this.shaped(RecipeCategory.MISC, ItemRegistry.MORPH_UPGRADE_ITEM.get(), 1).define('S', slime).define('L', Items.LADDER).define('P', Items.PAPER).pattern(" SP").pattern(" SS").pattern("L  ").unlockedBy("has_slime", has(slime)).save(this.output);
        this.shaped(RecipeCategory.MISC, ItemRegistry.HIDE_UPGRADE_ITEM.get(), 1).define('G', glass).define('L', Items.LADDER).define('D', whiteDye).pattern(" GD").pattern(" GG").pattern("L  ").unlockedBy("has_dye", has(whiteDye)).save(this.output);
        this.shaped(RecipeCategory.MISC, ItemRegistry.LIGHT_UPGRADE_ITEM.get(), 1).define('D', glowstoneDust).define('L', Items.LADDER).define('B', Items.GLOWSTONE).pattern(" DB").pattern(" DD").pattern("L  ").unlockedBy("has_glowstone", has(glowstoneDust)).save(this.output);

        ladderWoodUpgrade(iron, ItemRegistry.WOOD_IRON_UPGRADE.get(), upgradesToIronUpgrade);
        ladderWoodUpgrade(gold, ItemRegistry.WOOD_GOLD_UPGRADE.get(), upgradesToGoldUpgrade);
        ladderWoodUpgrade(diamond, ItemRegistry.WOOD_DIAMOND_UPGRADE.get(), upgradesToDiamondUpgrade);
        ladderWoodUpgrade(obsidian, ItemRegistry.WOOD_OBSIDIAN_UPGRADE.get(), upgradesToObsidianUpgrade);
        ladderWoodUpgrade(netherite, ItemRegistry.WOOD_NETHERITE_UPGRADE.get(), upgradesToNetheriteUpgrade);

        ladderUpgrade(copper, ItemRegistry.COPPER_UPGRADE.get());
        ladderUpgrade(iron, ItemRegistry.IRON_UPGRADE.get());
        ladderUpgrade(gold, ItemRegistry.GOLD_UPGRADE.get());
        ladderUpgrade(diamond, ItemRegistry.DIAMOND_UPGRADE.get());
        ladderUpgrade(obsidian, ItemRegistry.OBSIDIAN_UPGRADE.get());
        ladderUpgrade(netherite, ItemRegistry.NETHERITE_UPGRADE.get());

        // Secret recipe

        this.shaped(RecipeCategory.DECORATIONS, BlockRegistry.CRYING_OBSIDIAN_LADDER.get(), 9).define('I', Blocks.CRYING_OBSIDIAN).define('L', BlockRegistry.OBSIDIAN_LADDER.get()).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_ladder", has(LadderTags.UNKNOWN)).save(this.output, getRecipeId(IronLadders.MODID, "secret_ladder_recipe"));

        //Additional ladders recipes (from non-vanilla materials)

        ladderUpgrade(lead, ItemRegistry.WOOD_LEAD_UPGRADE.get());
        ladderWoodUpgrade(steel, ItemRegistry.WOOD_STEEL_UPGRADE.get(), upgradesToIronUpgrade);
        ladderWoodUpgrade(bronze, ItemRegistry.WOOD_BRONZE_UPGRADE.get(), upgradesToIronUpgrade);
        ladderUpgrade(tin, ItemRegistry.WOOD_TIN_UPGRADE.get());
        ladderWoodUpgrade(silver, ItemRegistry.WOOD_SILVER_UPGRADE.get(), upgradesToGoldUpgrade);
        ladderUpgrade(aluminum, ItemRegistry.WOOD_ALUMINUM_UPGRADE.get());

        ladder(steel, BlockRegistry.STEEL_LADDER.get(), upgradesToIron);
        ladder(lead, BlockRegistry.LEAD_LADDER.get(), Items.LADDER);
        ladder(tin, BlockRegistry.TIN_LADDER.get(), Items.LADDER);
        ladder(silver, BlockRegistry.SILVER_LADDER.get(), upgradesToGold);
        ladder(bronze, BlockRegistry.BRONZE_LADDER.get(), upgradesToIron);
        ladder(aluminum, BlockRegistry.ALUMINUM_LADDER.get(), Items.LADDER);

    }
    private void ladder(TagKey<Item> material, ItemLike result, ItemLike ladderBefore){
        this.shaped(RecipeCategory.DECORATIONS, result, 7).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_material", has(material)).unlockedBy("has_ladder", has(ladderBefore)).save(this.output);
    }
    private void ladder(TagKey<Item> material, ItemLike result, TagKey<Item> ladderBefore){
        this.shaped(RecipeCategory.DECORATIONS, result, 7).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_material", has(material)).unlockedBy("has_ladder", has(ladderBefore)).save(this.output);
    }
    private void ladder(Item material, ItemLike result, TagKey<Item> ladderBefore){
        this.shaped(RecipeCategory.DECORATIONS, result, 7).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_material", has(material)).unlockedBy("has_ladder", has(ladderBefore)).save(this.output);
    }
    private void ladder(Item material, ItemLike result, Item ladderBefore){
        this.shaped(RecipeCategory.DECORATIONS, result, 7).define('I', material).define('L', ladderBefore).pattern("LIL").pattern("LLL").pattern("LIL").unlockedBy("has_material", has(material)).unlockedBy("has_ladder", has(ladderBefore)).save(this.output);
    }
    private void ladderUpgrade(TagKey<Item> material, ItemLike result){
        this.shaped(RecipeCategory.MISC, result, 1).define('I', material).define('R', Blocks.LADDER).pattern(" II").pattern(" II").pattern("R  ").unlockedBy("has_material", has(material)).save(this.output);
    }
    private void ladderWoodUpgrade(TagKey<Item> material, ItemLike result, TagKey<Item> upgradeBefore){
        this.shaped(RecipeCategory.MISC, result, 1).define('I', material).define('L', upgradeBefore).pattern(" II").pattern(" II").pattern("L  ").unlockedBy("has_material", has(material)).save(this.output,  getRecipeId(IronLadders.MODID, "wood_" + VanillaRecipeProvider.getItemName(result)));
    }
    
    private String getRecipeId(String modid, String item) {
        return modid + ":" + item;
    }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ILRecipeProvider(provider, recipeOutput) {
            };
        }

        @Override
        public String getName() {
            return "Iron Ladders Recipes";
        }
    }
}
