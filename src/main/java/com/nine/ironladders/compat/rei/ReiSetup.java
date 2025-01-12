package com.nine.ironladders.compat.rei;

import com.nine.ironladders.IronLadders;
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
import net.minecraft.world.item.Item;

@REIPluginClient
public class ReiSetup implements REIClientPlugin {


    @Override
    public void registerBasicEntryFiltering(BasicFilteringRule<?> rule) {
        for (Item item : CreativeTabRegistry.getItemsToHide()){
            IronLadders.LOGGER.debug("Hiding {} recipes from REI", CreativeTabRegistry.getItemsToHide().size());
            rule.hide(EntryStacks.of(item));
        }
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registerDescriptions(registry);
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        for (Item item : CreativeTabRegistry.getItemsToHide()){
            IronLadders.LOGGER.debug("Hiding {} items from REI", CreativeTabRegistry.getItemsToHide().size());
            registry.removeEntry(EntryStacks.of(item));
        }
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
