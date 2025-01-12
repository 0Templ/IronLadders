package com.nine.ironladders.init;


import com.nine.ironladders.ILConfig;
import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.item.CustomUpgradeItem;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import com.nine.ironladders.common.item.UpgradeItem;
import com.nine.ironladders.common.utils.UpgradeType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IronLadders.MODID);

    public static final DeferredItem<Item> WOOD_IRON_UPGRADE = ITEMS.register("wood_iron_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_IRON));
    public static final DeferredItem<Item> WOOD_GOLD_UPGRADE = ITEMS.register("wood_gold_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_GOLD));
    public static final DeferredItem<Item> WOOD_DIAMOND_UPGRADE = ITEMS.register("wood_diamond_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_DIAMOND));
    public static final DeferredItem<Item> WOOD_OBSIDIAN_UPGRADE = ITEMS.register("wood_obsidian_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_OBSIDIAN));
    public static final DeferredItem<Item> WOOD_NETHERITE_UPGRADE = ITEMS.register("wood_netherite_upgrade", () -> new UpgradeItem(new Item.Properties().durability(28), UpgradeType.DEFAULT_TO_NETHERITE));
    public static final DeferredItem<Item> COPPER_UPGRADE = ITEMS.register("copper_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_COPPER));
    public static final DeferredItem<Item> IRON_UPGRADE = ITEMS.register("iron_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.COPPER_TO_IRON));
    public static final DeferredItem<Item> GOLD_UPGRADE = ITEMS.register("gold_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.IRON_TO_GOLD));
    public static final DeferredItem<Item> DIAMOND_UPGRADE = ITEMS.register("diamond_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.GOLD_TO_DIAMOND));
    public static final DeferredItem<Item> OBSIDIAN_UPGRADE = ITEMS.register("obsidian_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DIAMOND_TO_OBSIDIAN));
    public static final DeferredItem<Item> NETHERITE_UPGRADE = ITEMS.register("netherite_upgrade", () -> new UpgradeItem(new Item.Properties().durability(28), UpgradeType.DIAMOND_TO_NETHERITE));
    public static final DeferredItem<Item> POWER_UPGRADE_ITEM = ITEMS.register("power_upgrade", () -> new CustomUpgradeItem(new Item.Properties().stacksTo(1), UpgradeType.ANY_TO_POWERED));
    public static final DeferredItem<Item> LIGHT_UPGRADE_ITEM = ITEMS.register("light_upgrade", () -> new CustomUpgradeItem(new Item.Properties().stacksTo(1), UpgradeType.ANY_TO_GLOWING));
    public static final DeferredItem<Item> HIDE_UPGRADE_ITEM = ITEMS.register("hiding_upgrade", () -> new CustomUpgradeItem(new Item.Properties().stacksTo(1), UpgradeType.ANY_TO_HIDDEN));
    public static final DeferredItem<Item> MORPH_UPGRADE_ITEM = ITEMS.register("morph_upgrade", () -> new MorphUpgradeItem(new Item.Properties().stacksTo(1)));

    //Non-vanilla
    public static final DeferredItem<Item> WOOD_BRONZE_UPGRADE = ITEMS.register("wood_bronze_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_BRONZE));
    public static final DeferredItem<Item> WOOD_LEAD_UPGRADE = ITEMS.register("wood_lead_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_LEAD));
    public static final DeferredItem<Item> WOOD_STEEL_UPGRADE = ITEMS.register("wood_steel_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_STEEL));
    public static final DeferredItem<Item> WOOD_TIN_UPGRADE = ITEMS.register("wood_tin_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_TIN));
    public static final DeferredItem<Item> WOOD_ALUMINUM_UPGRADE = ITEMS.register("wood_aluminum_upgrade", () -> new UpgradeItem(new Item.Properties().durability(24), UpgradeType.DEFAULT_TO_ALUMINUM));
    public static final DeferredItem<Item> WOOD_SILVER_UPGRADE = ITEMS.register("wood_silver_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_SILVER));

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