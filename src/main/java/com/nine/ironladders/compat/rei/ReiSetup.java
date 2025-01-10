package com.nine.ironladders.compat.rei;

import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.CreativeTabRegistry;
import com.nine.ironladders.init.ItemRegistry;
import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@REIPluginClient
public class ReiSetup implements REIClientPlugin {


    @Override
    public void registerBasicEntryFiltering(BasicFilteringRule<?> rule) {
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/tin"))) {
            rule.hide(EntryStacks.of(ItemRegistry.WOOD_TIN_UPGRADE.get()));
            rule.hide(EntryStacks.of(BlockRegistry.TIN_LADDER.get().asItem()));
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/bronze"))) {
            rule.hide(EntryStacks.of(ItemRegistry.WOOD_BRONZE_UPGRADE.get()));
            rule.hide(EntryStacks.of(BlockRegistry.BRONZE_LADDER.get().asItem()));
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/lead"))) {
            rule.hide(EntryStacks.of(ItemRegistry.WOOD_LEAD_UPGRADE.get()));
            rule.hide(EntryStacks.of(BlockRegistry.LEAD_LADDER.get().asItem()));
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/steel"))) {
            rule.hide(EntryStacks.of(ItemRegistry.WOOD_STEEL_UPGRADE.get()));
            rule.hide(EntryStacks.of(BlockRegistry.STEEL_LADDER.get().asItem()));
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/aluminum"))) {
            rule.hide(EntryStacks.of(ItemRegistry.WOOD_ALUMINUM_UPGRADE.get()));
            rule.hide(EntryStacks.of(BlockRegistry.ALUMINUM_LADDER.get().asItem()));
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/silver"))) {
            rule.hide(EntryStacks.of(ItemRegistry.WOOD_SILVER_UPGRADE.get()));
            rule.hide(EntryStacks.of(BlockRegistry.SILVER_LADDER.get().asItem()));
        }
        rule.hide(EntryStacks.of(BlockRegistry.CRYING_OBSIDIAN_LADDER.get()));
        rule.hide(EntryStacks.of(BlockRegistry.BEDROCK_LADDER.get()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registerDescriptions(registry);
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/tin"))) {
            registry.removeEntry(EntryStacks.of(ItemRegistry.WOOD_TIN_UPGRADE.get()));
            registry.removeEntry(EntryStacks.of(BlockRegistry.TIN_LADDER.get().asItem()));
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/bronze"))) {
            registry.removeEntry(EntryStacks.of(ItemRegistry.WOOD_BRONZE_UPGRADE.get()));
            registry.removeEntry(EntryStacks.of(BlockRegistry.BRONZE_LADDER.get().asItem()));
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/lead"))) {
            registry.removeEntry(EntryStacks.of(ItemRegistry.WOOD_LEAD_UPGRADE.get()));
            registry.removeEntry(EntryStacks.of(BlockRegistry.LEAD_LADDER.get().asItem()));
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/steel"))) {
            registry.removeEntry(EntryStacks.of(ItemRegistry.WOOD_STEEL_UPGRADE.get()));
            registry.removeEntry(EntryStacks.of(BlockRegistry.STEEL_LADDER.get().asItem()));
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/aluminum"))) {
            registry.removeEntry(EntryStacks.of(ItemRegistry.WOOD_ALUMINUM_UPGRADE.get()));
            registry.removeEntry(EntryStacks.of(BlockRegistry.ALUMINUM_LADDER.get().asItem()));
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/silver"))) {
            registry.removeEntry(EntryStacks.of(ItemRegistry.WOOD_SILVER_UPGRADE.get()));
            registry.removeEntry(EntryStacks.of(BlockRegistry.SILVER_LADDER.get().asItem()));
        }
        registry.removeEntry(EntryStacks.of(BlockRegistry.CRYING_OBSIDIAN_LADDER.get()));
        registry.removeEntry(EntryStacks.of(BlockRegistry.BEDROCK_LADDER.get()));
    }

    private void registerDescriptions(DisplayRegistry registry) {
        DefaultInformationDisplay upgrade_1 = DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.HIDE_UPGRADE_ITEM.get()),
                ItemRegistry.HIDE_UPGRADE_ITEM.get().asItem().getDescription());
        DefaultInformationDisplay upgrade_2 = DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.LIGHT_UPGRADE_ITEM.get()),
                ItemRegistry.LIGHT_UPGRADE_ITEM.get().asItem().getDescription());
        DefaultInformationDisplay upgrade_3 = DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.POWER_UPGRADE_ITEM.get()),
                ItemRegistry.POWER_UPGRADE_ITEM.get().asItem().getDescription());
        DefaultInformationDisplay upgrade_4 = DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.MORPH_UPGRADE_ITEM.get()),
                ItemRegistry.MORPH_UPGRADE_ITEM.get().asItem().getDescription());
        upgrade_1.lines(Component.translatable("ironladders.nei.hiding_upgrade.desc"));
        upgrade_2.lines(Component.translatable("ironladders.nei.light_upgrade.desc"));
        upgrade_3.lines(Component.translatable("ironladders.nei.power_upgrade.desc"));
        upgrade_4.lines(Component.translatable("ironladders.nei.morph_upgrade.desc"));
        registry.add(upgrade_1);
        registry.add(upgrade_2);
        registry.add(upgrade_3);
        registry.add(upgrade_4);

    }
}
