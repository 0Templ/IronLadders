package com.nine.ironladders.common.utils;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.IronLadders;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.ItemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.List;

public class TagHelper {

    public static final TagKey<Item> STEEL = ItemTags.create(new ResourceLocation("forge:ingots/steel"));
    public static final TagKey<Item> LEAD = ItemTags.create(new ResourceLocation("forge:ingots/lead"));
    public static final TagKey<Item> TIN = ItemTags.create(new ResourceLocation("forge:ingots/tin"));
    public static final TagKey<Item> BRONZE = ItemTags.create(new ResourceLocation("forge:ingots/bronze"));
    public static final TagKey<Item> SILVER = ItemTags.create(new ResourceLocation("forge:ingots/silver"));
    public static final TagKey<Item> ALUMINUM = ItemTags.create(new ResourceLocation("forge:ingots/aluminum"));

    public static final TagKey<Item> COPPER = ItemTags.create(new ResourceLocation("forge:ingots/copper"));
    public static final TagKey<Item> IRON = ItemTags.create(new ResourceLocation("forge:ingots/iron"));
    public static final TagKey<Item> GOLD = ItemTags.create(new ResourceLocation("forge:ingots/gold"));
    public static final TagKey<Item> DIAMOND = ItemTags.create(new ResourceLocation("forge:gems/diamond"));
    public static final TagKey<Item> NETHERITE = ItemTags.create(new ResourceLocation("forge:ingots/netherite"));
    public static final TagKey<Item> REDSTONE = ItemTags.create(new ResourceLocation("forge:dusts/redstone"));
    public static final TagKey<Item> SLIME = ItemTags.create(new ResourceLocation("forge:slimeballs"));
    public static final TagKey<Item> GLASS = ItemTags.create(new ResourceLocation("forge:glass"));
    public static final TagKey<Item> WHITE_DYE = ItemTags.create(new ResourceLocation("forge:dyes/white"));
    public static final TagKey<Item> GLOWSTONE_DUST = ItemTags.create(new ResourceLocation("forge:dusts/glowstone"));
    public static final TagKey<Item> OBSIDIAN = ItemTags.create(new ResourceLocation("forge:obsidian"));

    public static final TagKey<Item> FAKE_TAG = ItemTags.create(new ResourceLocation(IronLadders.MODID,"fake"));
    public static final TagKey<Item> COPPER_LADDER = ItemTags.create(new ResourceLocation(IronLadders.MODID,"copper_ladder"));

    public static final List<TagKey<Item>> availableTags = new ArrayList<>();

    public static List<TagKey<Item>> tagsToCheck = List.of(STEEL, LEAD, TIN, BRONZE, SILVER, ALUMINUM);

    public static void initializeAvailableTags(){
        for (Item item : BuiltInRegistries.ITEM) {
            for (TagKey<Item> tag : tagsToCheck) {
                if (item.builtInRegistryHolder().is(tag)) {
                    availableTags.add(tag);
                }
            }
        }
    }

    public static List<Item> getItemsToHide(){
        List<Item> items = new ArrayList<>();
        if (!TagHelper.hasMaterial(TagHelper.TIN)) {
            items.add(ItemRegistry.WOOD_TIN_UPGRADE.get());
            items.add(BlockRegistry.TIN_LADDER.get().asItem());
        }
        if (!TagHelper.hasMaterial(TagHelper.BRONZE)) {
            items.add(ItemRegistry.WOOD_BRONZE_UPGRADE.get());
            items.add(BlockRegistry.BRONZE_LADDER.get().asItem());
        }
        if (!TagHelper.hasMaterial(TagHelper.LEAD)) {
            items.add(ItemRegistry.WOOD_LEAD_UPGRADE.get());
            items.add(BlockRegistry.LEAD_LADDER.get().asItem());
        }
        if (!TagHelper.hasMaterial(TagHelper.STEEL)) {
            items.add(ItemRegistry.WOOD_STEEL_UPGRADE.get());
            items.add(BlockRegistry.STEEL_LADDER.get().asItem());
        }
        if (!TagHelper.hasMaterial(TagHelper.ALUMINUM)) {
            items.add(BlockRegistry.ALUMINUM_LADDER.get().asItem());
        }
        if (!TagHelper.hasMaterial(TagHelper.SILVER)) {
            items.add(ItemRegistry.WOOD_SILVER_UPGRADE.get());
            items.add(BlockRegistry.SILVER_LADDER.get().asItem());
        }
        items.add(ItemRegistry.WOOD_ALUMINUM_UPGRADE.get());
        items.add(BlockRegistry.CRYING_OBSIDIAN_LADDER.get().asItem());
        items.add(BlockRegistry.BEDROCK_LADDER.get().asItem());
        return items;
    }


    public static boolean hasMaterial(TagKey<Item> tag) {
        if (!ILConfig.hideUncraftableLadders.get() || FMLEnvironment.production) {
            return true;
        }
        if (availableTags.isEmpty()) {
            initializeAvailableTags();
        }
        return availableTags.contains(tag);
    }
}
