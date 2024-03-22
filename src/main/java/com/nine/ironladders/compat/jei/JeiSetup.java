package com.nine.ironladders.compat.jei;


import com.nine.ironladders.IronLadders;
import com.nine.ironladders.init.ItemRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class JeiSetup implements IModPlugin {

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(ItemRegistry.HIDE_UPGRADE_ITEM), VanillaTypes.ITEM_STACK,
                Component.translatable("config.nei.hiding_upgrade"));
        registration.addIngredientInfo(new ItemStack(ItemRegistry.LIGHT_UPGRADE_ITEM), VanillaTypes.ITEM_STACK,
                Component.translatable("config.nei.light_upgrade"));
        registration.addIngredientInfo(new ItemStack(ItemRegistry.POWER_UPGRADE_ITEM), VanillaTypes.ITEM_STACK,
                Component.translatable("config.nei.power_upgrade"));
        registration.addIngredientInfo(new ItemStack(ItemRegistry.MORPH_UPGRADE_ITEM), VanillaTypes.ITEM_STACK,
                Component.translatable("config.nei.morph_upgrade"));
    }
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(IronLadders.MODID, IronLadders.MODID);
    }
}

