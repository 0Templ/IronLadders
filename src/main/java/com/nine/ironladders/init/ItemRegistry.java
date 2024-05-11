package com.nine.ironladders.init;


import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.component.CustomComponents;
import com.nine.ironladders.common.item.CustomUpgradeItem;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import com.nine.ironladders.common.item.UpgradeItem;
import com.nine.ironladders.common.utils.UpgradeType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IronLadders.MODID);
    public static final RegistryObject<Item> WOOD_IRON_UPGRADE = ITEMS.register("wood_iron_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_IRON));
    public static final RegistryObject<Item> WOOD_GOLD_UPGRADE = ITEMS.register("wood_gold_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_GOLD));
    public static final RegistryObject<Item> WOOD_DIAMOND_UPGRADE = ITEMS.register("wood_diamond_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_DIAMOND));
    public static final RegistryObject<Item> WOOD_NEHTERITE_UPGRADE = ITEMS.register("wood_netherite_upgrade", () -> new UpgradeItem(new Item.Properties().durability(28), UpgradeType.DEFAULT_TO_NETHERITE));
    public static final RegistryObject<Item> COPPER_UPGRADE = ITEMS.register("copper_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.DEFAULT_TO_COPPER));
    public static final RegistryObject<Item> IRON_UPGRADE = ITEMS.register("iron_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.COPPER_TO_IRON));
    public static final RegistryObject<Item> GOLD_UPGRADE = ITEMS.register("gold_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.IRON_TO_GOLD));
    public static final RegistryObject<Item> DIAMOND_UPGRADE = ITEMS.register("diamond_upgrade", () -> new UpgradeItem(new Item.Properties().durability(14), UpgradeType.GOLD_TO_DIAMOND));
    public static final RegistryObject<Item> NETHERITE_UPGRADE = ITEMS.register("netherite_upgrade", () -> new UpgradeItem(new Item.Properties().durability(28), UpgradeType.DIAMOND_TO_NETHERITE));
    public static final RegistryObject<Item> POWER_UPGRADE_ITEM = ITEMS.register("power_upgrade", () -> new CustomUpgradeItem(new Item.Properties().stacksTo(1),UpgradeType.ANY_TO_POWERED));
    public static final RegistryObject<Item> LIGHT_UPGRADE_ITEM = ITEMS.register("light_upgrade", () -> new CustomUpgradeItem(new Item.Properties().stacksTo(1),UpgradeType.ANY_TO_GLOWING));
    public static final RegistryObject<Item> HIDE_UPGRADE_ITEM = ITEMS.register("hiding_upgrade", () -> new CustomUpgradeItem(new Item.Properties().stacksTo(1),UpgradeType.ANY_TO_HIDDEN));
    public static final RegistryObject<Item> MORPH_UPGRADE_ITEM = ITEMS.register("morph_upgrade", () -> new MorphUpgradeItem(new Item.Properties().stacksTo(1).component(CustomComponents.MORPH_TYPE.get(), 0),UpgradeType.ANY_TO_MORPHED));
}