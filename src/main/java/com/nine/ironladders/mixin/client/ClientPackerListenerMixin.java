package com.nine.ironladders.mixin.client;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.TagHelper;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Mixin(value = ClientPacketListener.class)
public abstract class ClientPackerListenerMixin {

    @ModifyArg(method = "handleUpdateRecipes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/RecipeManager;replaceRecipes(Ljava/lang/Iterable;)V"))
    private Iterable<Recipe<?>> ironladders$handleUpdateRecipes(Iterable<Recipe<?>> recipes) {
        if (TagHelper.availableTags.isEmpty()){
            TagHelper.initializeAvailableTags();
        }
        List<String> allTags = new ArrayList<>(TagHelper.tagsToCheck.stream()
                .map(s -> s.location().toString().split("/")[1])
                .filter(t -> !TagHelper.availableTags.stream().map(s -> s.location().toString().split("/")[1]).toList().contains(t))
                .toList());
        allTags.add("secret");
        var ret = StreamSupport.stream(recipes.spliterator(), false)
                .filter(recipe -> {
                    ResourceLocation id = recipe.getId();
                    if (id.getNamespace().equals(IronLadders.MODID)) {
                        return allTags.stream().noneMatch(hidden -> id.getPath().contains(hidden));
                    }
                    return true;
                })
                .toList();
        return ret;
    }
}
