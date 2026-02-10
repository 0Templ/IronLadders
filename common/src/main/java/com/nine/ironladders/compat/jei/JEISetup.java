package com.nine.ironladders.compat.jei;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.compat.nei.NeiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class JEISetup implements IModPlugin {

	private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, "jei");
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		for (var info : NeiHelper.NEI_ITEM_INFO_MAP.entrySet()){
			registration.addIngredientInfo(new ItemStack(info.getKey()), VanillaTypes.ITEM_STACK, info.getValue());
		}
		registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, NeiHelper.hiddenNEIStacks());
	}
	
	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}
}
