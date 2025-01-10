package com.nine.ironladders.compat.jei;


import com.nine.ironladders.IronLadders;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.CreativeTabRegistry;
import com.nine.ironladders.init.ItemRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;


@JeiPlugin
public class JeiSetup implements IModPlugin {

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IRecipeManager recipeManager = jeiRuntime.getRecipeManager();

        RecipeType<CraftingRecipe> craftingRecipeType = RecipeTypes.CRAFTING;
        List<Item> stacks = new ArrayList<>();

        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/tin"))) {
            stacks.add(ItemRegistry.WOOD_TIN_UPGRADE.get());
            stacks.add(BlockRegistry.TIN_LADDER.get().asItem());
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/bronze"))) {
            stacks.add(ItemRegistry.WOOD_BRONZE_UPGRADE.get());
            stacks.add(BlockRegistry.BRONZE_LADDER.get().asItem());
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/lead"))) {
            stacks.add(ItemRegistry.WOOD_LEAD_UPGRADE.get());
            stacks.add(BlockRegistry.LEAD_LADDER.get().asItem());
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/steel"))) {
            stacks.add(ItemRegistry.WOOD_STEEL_UPGRADE.get());
            stacks.add(BlockRegistry.STEEL_LADDER.get().asItem());
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/aluminum"))) {
            stacks.add(ItemRegistry.WOOD_ALUMINUM_UPGRADE.get());
            stacks.add(BlockRegistry.ALUMINUM_LADDER.get().asItem());
        }
        if (!CreativeTabRegistry.hasMaterial(new ResourceLocation("forge:ingots/silver"))) {
            stacks.add(ItemRegistry.WOOD_SILVER_UPGRADE.get());
            stacks.add(BlockRegistry.SILVER_LADDER.get().asItem());
        }
        stacks.add(BlockRegistry.CRYING_OBSIDIAN_LADDER.get().asItem());
        stacks.add(BlockRegistry.BEDROCK_LADDER.get().asItem());
        if (Minecraft.getInstance().getConnection() != null) {
            RecipeManager vanillaRecipeManager = Minecraft.getInstance().getConnection().getRecipeManager();
            var recipesToHide = vanillaRecipeManager.getRecipes().stream()
                    .filter(recipe -> recipe instanceof CraftingRecipe)
                    .map(recipe -> (CraftingRecipe) recipe)
                    .filter(recipe -> stacks.contains(recipe.getResultItem(Minecraft.getInstance().getConnection().registryAccess()).getItem()))
                    .toList();
            IronLadders.LOGGER.info("Hiding {} recipes from JEI", recipesToHide.size());
            recipeManager.hideRecipes(craftingRecipeType, recipesToHide);
        }

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
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
        return new ResourceLocation(IronLadders.MODID, IronLadders.MODID);
    }
}

