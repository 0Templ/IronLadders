package com.nine.ironladders.compat.rei;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.TagHelper;
import com.nine.ironladders.init.ItemRegistry;
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
    public void registerDisplays(DisplayRegistry registry) {
        registerDescriptions(registry);
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        var list = TagHelper.getItemsToHide();
        IronLadders.LOGGER.info("Hiding {} items from REI panel {}", list.size(), list);
        for (Item item : TagHelper.getItemsToHide()){
            registry.removeEntry(EntryStacks.of(item));
        }
    }

    private void registerDescriptions(DisplayRegistry registry) {
        registry.add(DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.HIDE_UPGRADE_ITEM.get()),
                ItemRegistry.HIDE_UPGRADE_ITEM.get().asItem().getDescription())
                .lines(Component.translatable("ironladders.nei.hiding_upgrade.desc")));
        registry.add(DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.LIGHT_UPGRADE_ITEM.get()),
                ItemRegistry.LIGHT_UPGRADE_ITEM.get().asItem().getDescription())
                .lines(Component.translatable("ironladders.nei.light_upgrade.desc")));
        registry.add(DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.POWER_UPGRADE_ITEM.get()),
                ItemRegistry.POWER_UPGRADE_ITEM.get().asItem().getDescription())
                .lines(Component.translatable("ironladders.nei.power_upgrade.desc")));
        registry.add(DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.MORPH_UPGRADE_ITEM.get()),
                ItemRegistry.MORPH_UPGRADE_ITEM.get().asItem().getDescription())
                .lines(Component.translatable("ironladders.nei.morph_upgrade.desc")));
    }
}
