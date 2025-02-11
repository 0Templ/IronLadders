package com.nine.ironladders.compat.emi;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.TagHelper;
import com.nine.ironladders.init.ItemRegistry;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

@EmiEntrypoint
public class EmiSetup implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.addRecipe(new EmiInfoRecipe(List.of(EmiIngredient.of(Ingredient.of(ItemRegistry.HIDE_UPGRADE_ITEM.get()))),
                List.of(Component.translatable("ironladders.nei.hiding_upgrade.desc")), null));
        registry.addRecipe(new EmiInfoRecipe(List.of(EmiIngredient.of(Ingredient.of(ItemRegistry.LIGHT_UPGRADE_ITEM.get()))),
                List.of(Component.translatable("ironladders.nei.light_upgrade.desc")), null));
        registry.addRecipe(new EmiInfoRecipe(List.of(EmiIngredient.of(Ingredient.of(ItemRegistry.POWER_UPGRADE_ITEM.get()))),
                List.of(Component.translatable("ironladders.nei.power_upgrade.desc")), null));
        registry.addRecipe(new EmiInfoRecipe(List.of(EmiIngredient.of(Ingredient.of(ItemRegistry.MORPH_UPGRADE_ITEM.get()))),
                List.of(Component.translatable("ironladders.nei.morph_upgrade.desc")), null));
        var list = TagHelper.getItemsToHide();
        IronLadders.LOGGER.info("Hiding {} items from EMI panel {}", list.size(), list);
        for (Item item : list) {
            registry.removeEmiStacks(EmiStack.of(item));
        }
    }

    @Override
    public void initialize(EmiInitRegistry registry) {
    }
}
