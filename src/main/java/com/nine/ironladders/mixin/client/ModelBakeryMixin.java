package com.nine.ironladders.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.function.Predicate;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Shadow
    @Final
    private Map<ResourceLocation, UnbakedModel> unbakedCache;

    @Shadow
    @Final
    private Map<ResourceLocation, UnbakedModel> topLevelModels;

    @Shadow
    public abstract UnbakedModel getModel(ResourceLocation location);

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V"))
    private void ModelBakery(ProfilerFiller instance, String s) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        String modelsPath = "models/item/morph";
        Predicate<ResourceLocation> filter = resourceLocation -> resourceLocation.getPath().endsWith(".json");
        Map<ResourceLocation, Resource> resources = resourceManager.listResources(modelsPath, filter);
        for (ResourceLocation resourceLocation : resources.keySet()) {
            String modelPath = resourceLocation.getPath().substring(modelsPath.length() + 1).replace(".json", "");
            loadTopLevel(new ModelResourceLocation(resourceLocation.getNamespace(), "morph/" + modelPath, "inventory"));
        }
        instance.push("missing_model");
    }

    @Unique
    private void loadTopLevel(ModelResourceLocation location) {
        UnbakedModel unbakedmodel = getModel(location);
        unbakedCache.put(location, unbakedmodel);
        topLevelModels.put(location, unbakedmodel);
    }
}
