package com.nine.ironladders.compat.rei;

import com.nine.ironladders.init.ItemRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import mezz.jei.api.constants.VanillaTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ReiSetup implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registerDescriptions(registry);
    }
    private void registerDescriptions(DisplayRegistry registry) {


        DefaultInformationDisplay upgrade_1 = DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.HIDE_UPGRADE_ITEM),
                ItemRegistry.HIDE_UPGRADE_ITEM.asItem().getDescription());
        DefaultInformationDisplay upgrade_2 = DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.LIGHT_UPGRADE_ITEM),
                ItemRegistry.LIGHT_UPGRADE_ITEM.asItem().getDescription());
        DefaultInformationDisplay upgrade_3 = DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.POWER_UPGRADE_ITEM),
                ItemRegistry.POWER_UPGRADE_ITEM.asItem().getDescription());
        DefaultInformationDisplay upgrade_4 = DefaultInformationDisplay.createFromEntry(EntryStacks.of(ItemRegistry.MORPH_UPGRADE_ITEM),
                ItemRegistry.MORPH_UPGRADE_ITEM.asItem().getDescription());
        upgrade_1.lines(Component.translatable("config.nei.hiding_upgrade"));
        upgrade_2.lines(Component.translatable("config.nei.light_upgrade"));
        upgrade_3.lines(Component.translatable("config.nei.power_upgrade"));
        upgrade_4.lines(Component.translatable("config.nei.morph_upgrade"));
        registry.add(upgrade_1);
        registry.add(upgrade_2);
        registry.add(upgrade_3);
        registry.add(upgrade_4);
    }

}
