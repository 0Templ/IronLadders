package com.nine.ironladders.common.utils;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.ItemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class TagHelper {

    public static final TagKey<Item> steel = ItemTags.create(ResourceLocation.parse("c:ingots/steel"));
    public static final TagKey<Item> lead = ItemTags.create(ResourceLocation.parse("c:ingots/lead"));
    public static final TagKey<Item> tin = ItemTags.create(ResourceLocation.parse("c:ingots/tin"));
    public static final TagKey<Item> bronze = ItemTags.create(ResourceLocation.parse("c:ingots/bronze"));
    public static final TagKey<Item> silver = ItemTags.create(ResourceLocation.parse("c:ingots/silver"));
    public static final TagKey<Item> aluminum = ItemTags.create(ResourceLocation.parse("c:ingots/aluminum"));

    public static final List<TagKey<Item>> availableTags = new ArrayList<>();
    public static List<TagKey<Item>> tagsToCheck = List.of(steel, lead, tin, bronze, silver, aluminum);

    private static void initializeAvailableTags(){
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
        if (!TagHelper.hasMaterial(TagHelper.tin)) {
            items.add(ItemRegistry.WOOD_TIN_UPGRADE.get());
            items.add(BlockRegistry.TIN_LADDER.get().asItem());
        }
        if (!TagHelper.hasMaterial(TagHelper.bronze)) {
            items.add(ItemRegistry.WOOD_BRONZE_UPGRADE.get());
            items.add(BlockRegistry.BRONZE_LADDER.get().asItem());
        }
        if (!TagHelper.hasMaterial(TagHelper.lead)) {
            items.add(ItemRegistry.WOOD_LEAD_UPGRADE.get());
            items.add(BlockRegistry.LEAD_LADDER.get().asItem());
        }
        if (!TagHelper.hasMaterial(TagHelper.steel)) {
            items.add(ItemRegistry.WOOD_STEEL_UPGRADE.get());
            items.add(BlockRegistry.STEEL_LADDER.get().asItem());
        }
        if (!TagHelper.hasMaterial(TagHelper.aluminum)) {
            items.add(ItemRegistry.WOOD_ALUMINUM_UPGRADE.get());
            items.add(BlockRegistry.ALUMINUM_LADDER.get().asItem());
        }
        if (!TagHelper.hasMaterial(TagHelper.silver)) {
            items.add(ItemRegistry.WOOD_SILVER_UPGRADE.get());
            items.add(BlockRegistry.SILVER_LADDER.get().asItem());
        }
        items.add(BlockRegistry.CRYING_OBSIDIAN_LADDER.get().asItem());
        items.add(BlockRegistry.BEDROCK_LADDER.get().asItem());
        return items;
    }


    public static boolean hasMaterial(TagKey<Item> tag) {
        if (!ILConfig.hideUncraftableLadders.get()) {
            return true;
        }
        if (availableTags.isEmpty()) {
            initializeAvailableTags();
        }
        return availableTags.contains(tag);
    }
}
