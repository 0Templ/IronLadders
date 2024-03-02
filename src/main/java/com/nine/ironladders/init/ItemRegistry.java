package com.nine.ironladders.init;

import com.nine.ironladders.common.item.CustomUpgradeItem;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import com.nine.ironladders.common.item.UpgradeItem;
import com.nine.ironladders.common.item.UpgradeType;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.nine.ironladders.IronLadders;

public class ItemRegistry {
    public static final Item WOOD_IRON_UPGRADE = registerItem("wood_iron_upgrade", new UpgradeItem(new FabricItemSettings().maxDamage(14), UpgradeType.DEFAULT_TO_IRON));
    public static final Item WOOD_GOLD_UPGRADE = registerItem("wood_gold_upgrade", new UpgradeItem(new FabricItemSettings().maxDamage(14), UpgradeType.DEFAULT_TO_GOLD));
    public static final Item WOOD_DIAMOND_UPGRADE = registerItem("wood_diamond_upgrade", new UpgradeItem(new FabricItemSettings().maxDamage(14), UpgradeType.DEFAULT_TO_DIAMOND));
    public static final Item WOOD_NEHTERITE_UPGRADE = registerItem("wood_netherite_upgrade", new UpgradeItem(new FabricItemSettings().maxDamage(28), UpgradeType.DEFAULT_TO_NETHERITE));
    public static final Item COPPER_UPGRADE = registerItem("copper_upgrade", new UpgradeItem(new FabricItemSettings().maxDamage(14), UpgradeType.DEFAULT_TO_COPPER));
    public static final Item IRON_UPGRADE = registerItem("iron_upgrade", new UpgradeItem(new FabricItemSettings().maxDamage(14), UpgradeType.COPPER_TO_IRON));
    public static final Item GOLD_UPGRADE = registerItem("gold_upgrade", new UpgradeItem(new FabricItemSettings().maxDamage(14), UpgradeType.IRON_TO_GOLD));
    public static final Item DIAMOND_UPGRADE = registerItem("diamond_upgrade", new UpgradeItem(new FabricItemSettings().maxDamage(14), UpgradeType.GOLD_TO_DIAMOND));
    public static final Item NETHERITE_UPGRADE = registerItem("netherite_upgrade", new UpgradeItem(new FabricItemSettings().maxDamage(28), UpgradeType.DIAMOND_TO_NETHERITE));
    public static final Item POWER_UPGRADE_ITEM = registerItem("power_upgrade", new CustomUpgradeItem(new FabricItemSettings().maxCount(1), UpgradeType.ANY_TO_POWERED));
    public static final Item LIGHT_UPGRADE_ITEM = registerItem("light_upgrade", new CustomUpgradeItem(new FabricItemSettings().maxCount(1), UpgradeType.ANY_TO_GLOWING));
    public static final Item HIDE_UPGRADE_ITEM = registerItem("hiding_upgrade", new CustomUpgradeItem(new FabricItemSettings().maxCount(1), UpgradeType.ANY_TO_HIDDEN));
    public static final Item MORPH_UPGRADE_ITEM = registerItem("morph_upgrade", new MorphUpgradeItem(new FabricItemSettings().maxCount(1), UpgradeType.ANY_TO_MORPHED));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(IronLadders.MODID, name), item);
    }
    public static void register() {
    }
}
