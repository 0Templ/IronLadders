package com.nine.ironladders.compat.jei;


import com.nine.ironladders.IronLadders;
import com.nine.ironladders.init.CreativeTabRegistry;
import com.nine.ironladders.init.ItemRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;


@JeiPlugin
public class JeiSetup implements IModPlugin {

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IRecipeManager recipeManager = jeiRuntime.getRecipeManager();
        if (Minecraft.getInstance().getConnection() != null) {
            RecipeManager vanillaRecipeManager = Minecraft.getInstance().getConnection().getRecipeManager();
            var recipesToHide = vanillaRecipeManager.getRecipes().stream()
                    .filter(holder -> holder.value() instanceof CraftingRecipe)
                    .map(holder -> (RecipeHolder<CraftingRecipe>) holder)
                    .filter(recipe -> CreativeTabRegistry.getItemsToHide().contains(recipe.value().getResultItem(Minecraft.getInstance().getConnection().registryAccess()).getItem()))
                    .toList();
            recipeManager.hideRecipes(RecipeTypes.CRAFTING, recipesToHide);
        }

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, CreativeTabRegistry.getItemsToHide().stream().map(item -> item.asItem().getDefaultInstance()).toList());

        registration.addIngredientInfo(new ItemStack(ItemRegistry.HIDE_UPGRADE_ITEM.get()), VanillaTypes.ITEM_STACK,
                Component.translatable("ironladders.nei.hiding_upgrade.desc"));
        registration.addIngredientInfo(new ItemStack(ItemRegistry.LIGHT_UPGRADE_ITEM.get()), VanillaTypes.ITEM_STACK,
                Component.translatable("cironladders.nei.light_upgrade.desc"));
        registration.addIngredientInfo(new ItemStack(ItemRegistry.POWER_UPGRADE_ITEM.get()), VanillaTypes.ITEM_STACK,
                Component.translatable("ironladders.nei.power_upgrade.desc"));
        registration.addIngredientInfo(new ItemStack(ItemRegistry.MORPH_UPGRADE_ITEM.get()), VanillaTypes.ITEM_STACK,
                Component.translatable("ironladders.nei.morph_upgrade.desc"));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, IronLadders.MODID);
    }
}

