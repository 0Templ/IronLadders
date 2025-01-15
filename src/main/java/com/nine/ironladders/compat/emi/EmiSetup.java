package com.nine.ironladders.compat.emi;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.TagHelper;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.ItemRegistry;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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

        if (!TagHelper.hasMaterial(TagHelper.tin)) {
            registry.removeEmiStacks(EmiStack.of(ItemRegistry.WOOD_TIN_UPGRADE.get()));
            registry.removeEmiStacks(EmiStack.of(BlockRegistry.TIN_LADDER.get().asItem()));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "tin_ladder"));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "wood_tin_ladder"));
        }
        if (!TagHelper.hasMaterial(TagHelper.bronze)) {
            registry.removeEmiStacks(EmiStack.of(ItemRegistry.WOOD_BRONZE_UPGRADE.get()));
            registry.removeEmiStacks(EmiStack.of(BlockRegistry.BRONZE_LADDER.get().asItem()));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "bronze_ladder"));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "wood_bronze_ladder"));
        }
        if (!TagHelper.hasMaterial(TagHelper.lead)) {
            registry.removeEmiStacks(EmiStack.of(ItemRegistry.WOOD_LEAD_UPGRADE.get()));
            registry.removeEmiStacks(EmiStack.of(BlockRegistry.LEAD_LADDER.get().asItem()));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "lead_ladder"));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "wood_lead_ladder"));
        }
        if (!TagHelper.hasMaterial(TagHelper.steel)) {
            registry.removeEmiStacks(EmiStack.of(ItemRegistry.WOOD_STEEL_UPGRADE.get()));
            registry.removeEmiStacks(EmiStack.of(BlockRegistry.STEEL_LADDER.get().asItem()));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "steel_ladder"));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "wood_steel_ladder"));
        }
        if (!TagHelper.hasMaterial(TagHelper.aluminum)) {
            registry.removeEmiStacks(EmiStack.of(ItemRegistry.WOOD_ALUMINUM_UPGRADE.get()));
            registry.removeEmiStacks(EmiStack.of(BlockRegistry.ALUMINUM_LADDER.get().asItem()));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "aluminum_ladder"));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "wood_aluminum_ladder"));
        }
        if (!TagHelper.hasMaterial(TagHelper.silver)) {
            registry.removeEmiStacks(EmiStack.of(ItemRegistry.WOOD_SILVER_UPGRADE.get()));
            registry.removeEmiStacks(EmiStack.of(BlockRegistry.SILVER_LADDER.get().asItem()));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "silver_ladder"));
            registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "wood_silver_ladder"));
        }
        registry.removeEmiStacks(EmiStack.of(BlockRegistry.CRYING_OBSIDIAN_LADDER.get()));
        registry.removeEmiStacks(EmiStack.of(BlockRegistry.BEDROCK_LADDER.get()));
        registry.removeRecipes(new ResourceLocation(IronLadders.MODID, "secret_ladder_recipe"));
    }

    @Override
    public void initialize(EmiInitRegistry registry) {
    }
}
