package com.nine.ironladders.mixin.feature.recipe.common;

import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
	
/*	@Inject(method = "apply", at = @At("HEAD"))
	private void il$apply(Map<ResourceLocation, JsonElement> object,
						  ResourceManager resourceManager,
						  ProfilerFiller profiler,
						  CallbackInfo ci) {
		
		List<ResourceLocation> toRemove = new ArrayList<>();
		for (var holder : ItemRegistry.ITEMS){
			var item = holder.get();
			if (item instanceof OldUpgradeToolItem){
				toRemove.add(il$getId(item));
			}
		}
		
		if (ILConfig.REMOVE_UNCRAFTABLE_LADDERS_RECIPES.get()){
			for (var uncraftable : RecipeHelper.UNCRAFTABLE_ITEMS){
				toRemove.add(il$getId(uncraftable));
			}
		}
		
		for (ResourceLocation id : toRemove) {
			object.remove(id);
		}
	}
	
	@Unique
	private static ResourceLocation il$getId(Item item){
		return BuiltInRegistries.ITEM.getKey(item);
	}*/

}
