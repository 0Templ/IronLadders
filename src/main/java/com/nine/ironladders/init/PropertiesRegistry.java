package com.nine.ironladders.init;

import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class PropertiesRegistry {

    public static void register() {
        makeMorphItem(ItemRegistry.MORPH_UPGRADE_ITEM.get());
    }

    private static void makeMorphItem(Item item) {
        ItemProperties.register(item, new ResourceLocation("morph_type"), (stack, level, entity, seed) -> {
            if (stack.getItem() instanceof MorphUpgradeItem){
                return ((MorphUpgradeItem) stack.getItem()).getMorphType(stack);
            }
            return 1F;
        });

    }

}
