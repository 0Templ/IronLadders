package com.nine.ironladders.mixin.client;

import com.nine.ironladders.IronLadders;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(RecipeButton.class)
public abstract class RecipeButtonMixin {

    @Shadow
    private RecipeCollection collection;

    @Shadow
    protected abstract List<Recipe<?>> getOrderedRecipes();

    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderItem(Lnet/minecraft/world/item/ItemStack;IIII)V"))
    private void renderWidget$ironladders(GuiGraphics guiGraphics, ItemStack stack, int x, int y, int seed, int offset) {
        if ((stack != null) &&
                (ForgeRegistries.ITEMS.getKey(stack.getItem()) != null) &&
                ForgeRegistries.ITEMS.getKey(stack.getItem()).getNamespace().equals(IronLadders.MODID)) {
            return;
        }
        guiGraphics.renderItem(stack, x, y, seed, offset);
    }

    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderFakeItem(Lnet/minecraft/world/item/ItemStack;II)V"))
    private void renderWidget$ironladders(GuiGraphics guiGraphics, ItemStack stack, int x, int y) {
        if (stack != null &&
                ForgeRegistries.ITEMS.getKey(stack.getItem()) != null &&
                ForgeRegistries.ITEMS.getKey(stack.getItem()).getNamespace().equals(IronLadders.MODID)) {
            if (collection.hasSingleResultItem() && getOrderedRecipes().size() > 1){
                guiGraphics.renderFakeItem(stack, x + 1, y + 1);
            }
            else {
                guiGraphics.renderFakeItem(stack, x, y);
            }
            return;
        }
        guiGraphics.renderFakeItem(stack, x, y);
    }

}