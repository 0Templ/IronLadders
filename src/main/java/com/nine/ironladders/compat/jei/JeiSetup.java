package com.nine.ironladders.compat.jei;


import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.TagHelper;
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
        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, TagHelper.getItemsToHide().stream().map(item -> item.asItem().getDefaultInstance()).toList());
        var list = TagHelper.getItemsToHide();
        IronLadders.LOGGER.info("Hiding {} items from JEI panel ({})", list.size(), list);
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

