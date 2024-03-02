package com.nine.ironladders.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import com.nine.ironladders.IronLadders;

public class CreativeTabGroup {
    public static final ItemGroup TAB = Registry.register(Registries.ITEM_GROUP,
            new Identifier(IronLadders.MODID, "ironladders"),
            FabricItemGroup.builder().displayName(Text.translatable("tab.ironladders"))
                    .icon(() -> new ItemStack(BlockRegistry.IRON_LADDER)).entries((displayContext, entries) -> {

                        entries.add(ItemRegistry.COPPER_UPGRADE);
                        entries.add(ItemRegistry.WOOD_IRON_UPGRADE);
                        entries.add(ItemRegistry.WOOD_GOLD_UPGRADE);
                        entries.add(ItemRegistry.WOOD_DIAMOND_UPGRADE);
                        entries.add(ItemRegistry.WOOD_NEHTERITE_UPGRADE);
                        entries.add(ItemRegistry.IRON_UPGRADE);
                        entries.add(ItemRegistry.GOLD_UPGRADE);
                        entries.add(ItemRegistry.DIAMOND_UPGRADE);
                        entries.add(ItemRegistry.NETHERITE_UPGRADE);
                        entries.add(ItemRegistry.POWER_UPGRADE_ITEM);
                        entries.add(ItemRegistry.LIGHT_UPGRADE_ITEM);
                        entries.add(ItemRegistry.HIDE_UPGRADE_ITEM);
                        entries.add(ItemRegistry.MORPH_UPGRADE_ITEM);

                        entries.add(BlockRegistry.COPPER_LADDER);
                        entries.add(BlockRegistry.EXPOSED_COPPER_LADDER);
                        entries.add(BlockRegistry.WEATHERED_COPPER_LADDER);
                        entries.add(BlockRegistry.OXIDIZED_COPPER_LADDER);
                        entries.add(BlockRegistry.WAXED_COPPER_LADDER);
                        entries.add(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER);
                        entries.add(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER);
                        entries.add(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER);

                        entries.add(BlockRegistry.IRON_LADDER);
                        entries.add(BlockRegistry.GOLD_LADDER);
                        entries.add(BlockRegistry.DIAMOND_LADDER);
                        entries.add(BlockRegistry.NETHERITE_LADDER);

                    }).build());
    public static void register() {
    }
}

