package com.nine.ironladders;

import com.nine.ironladders.client.MorphModelPredicateProvider;
import com.nine.ironladders.init.ItemRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class IronLaddersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        makeMorphItem(ItemRegistry.MORPH_UPGRADE_ITEM);
    }
    public void makeMorphItem(Item item) {
        ModelPredicateProviderRegistry.register(item, new Identifier("morph_type"), new MorphModelPredicateProvider());
    }

}
