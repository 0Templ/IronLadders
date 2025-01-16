package com.nine.ironladders.common.utils;

import com.nine.ironladders.IronLadders;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class LadderTags {

    public static final TagKey<Item> UPGRADES_TO_IRON_UPGRADE = createItemTag("upgrades_to_iron_upgrade");
    public static final TagKey<Item> UPGRADES_TO_GOLD_UPGRADE = createItemTag("upgrades_to_gold_upgrade");
    public static final TagKey<Item> UPGRADES_TO_DIAMOND_UPGRADE = createItemTag("upgrades_to_diamond_upgrade");
    public static final TagKey<Item> UPGRADES_TO_NETHERITE_UPGRADE = createItemTag("upgrades_to_netherite_upgrade");
    public static final TagKey<Item> UPGRADES_TO_OBSIDIAN_UPGRADE = createItemTag("upgrades_to_obsidian_upgrade");
    public static final TagKey<Item> UNKNOWN = createItemTag("unknown");

    public static final TagKey<Item> UPGRADES_TO_IRON = createItemTag("upgrades_to_iron");
    public static final TagKey<Item> UPGRADES_TO_GOLD = createItemTag("upgrades_to_gold");
    public static final TagKey<Item> UPGRADES_TO_DIAMOND = createItemTag("upgrades_to_diamond");
    public static final TagKey<Item> UPGRADES_TO_OBSIDIAN = createItemTag("upgrades_to_obsidian");
    public static final TagKey<Item> UPGRADES_TO_NETHERITE = createItemTag("upgrades_to_netherite");

    private static TagKey<Item> createItemTag(String string) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, string));
    }

}
