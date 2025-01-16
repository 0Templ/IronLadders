package com.nine.ironladders.init;


import com.nine.ironladders.ILConfig;
import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.item.CustomUpgradeItem;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import com.nine.ironladders.common.item.UpgradeItem;
import com.nine.ironladders.common.utils.UpgradeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IronLadders.MODID);

    public static final DeferredItem<Item> WOOD_IRON_UPGRADE = register("wood_iron_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_IRON),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> WOOD_GOLD_UPGRADE = register("wood_gold_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_GOLD),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> WOOD_DIAMOND_UPGRADE = register("wood_diamond_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_DIAMOND),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> WOOD_OBSIDIAN_UPGRADE = register("wood_obsidian_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_OBSIDIAN),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> WOOD_NETHERITE_UPGRADE = register("wood_netherite_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_NETHERITE),
            new Item.Properties().durability(28));
    public static final DeferredItem<Item> COPPER_UPGRADE = register("copper_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_COPPER),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> IRON_UPGRADE = register("iron_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.COPPER_TO_IRON),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> GOLD_UPGRADE = register("gold_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.IRON_TO_GOLD),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> DIAMOND_UPGRADE = register("diamond_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.GOLD_TO_DIAMOND),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> OBSIDIAN_UPGRADE = register("obsidian_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DIAMOND_TO_OBSIDIAN),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> NETHERITE_UPGRADE = register("netherite_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DIAMOND_TO_NETHERITE),
            new Item.Properties().durability(28));
    public static final DeferredItem<Item> POWER_UPGRADE_ITEM = register("power_upgrade",
            properties -> new CustomUpgradeItem(properties, UpgradeType.ANY_TO_POWERED),
            new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> LIGHT_UPGRADE_ITEM = register("light_upgrade",
            properties -> new CustomUpgradeItem(properties, UpgradeType.ANY_TO_GLOWING),
            new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> HIDE_UPGRADE_ITEM = register("hiding_upgrade",
            properties -> new CustomUpgradeItem(properties, UpgradeType.ANY_TO_HIDDEN),
            new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> MORPH_UPGRADE_ITEM = register("morph_upgrade",
            MorphUpgradeItem::new,
            new Item.Properties().stacksTo(1));

    //Non-vanilla
    public static final DeferredItem<Item> WOOD_BRONZE_UPGRADE = register("wood_bronze_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_BRONZE),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> WOOD_LEAD_UPGRADE = register("wood_lead_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_LEAD),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> WOOD_STEEL_UPGRADE = register("wood_steel_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_STEEL),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> WOOD_TIN_UPGRADE = register("wood_tin_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_TIN),
            new Item.Properties().durability(14));
    public static final DeferredItem<Item> WOOD_ALUMINUM_UPGRADE = register("wood_aluminum_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_ALUMINUM),
            new Item.Properties().durability(24));
    public static final DeferredItem<Item> WOOD_SILVER_UPGRADE = register("wood_silver_upgrade",
            properties -> new UpgradeItem(properties, UpgradeType.DEFAULT_TO_SILVER),
            new Item.Properties().durability(14));


    public static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
        return ITEMS.register(name, () -> factory.apply(properties.setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, name)))));
    }

    public static boolean checkDisabled(Item item) {
        if (item.equals(POWER_UPGRADE_ITEM.get())) {
            return !ILConfig.enablePoweredLaddersUpgrade.get();
        } else if (item.equals(LIGHT_UPGRADE_ITEM.get())) {
            return !ILConfig.enableGlowingLaddersUpgrade.get();
        } else if (item.equals(MORPH_UPGRADE_ITEM.get())) {
            return !ILConfig.enableMorphLaddersUpgrade.get();
        } else if (item.equals(HIDE_UPGRADE_ITEM.get())) {
            return !ILConfig.hideUncraftableLadders.get();
        }
        return false;
    }
}