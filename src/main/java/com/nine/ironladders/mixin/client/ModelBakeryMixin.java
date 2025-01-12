package com.nine.ironladders.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.function.Predicate;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Shadow protected abstract void loadItemModelAndDependencies(ResourceLocation modelLocation);

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V"))
    private void ModelBakery(ProfilerFiller instance, String s) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        String modelsPath = "models/item/morph";
        Predicate<ResourceLocation> filter = resourceLocation -> resourceLocation.getPath().endsWith(".json");
        Map<ResourceLocation, Resource> resources = resourceManager.listResources(modelsPath, filter);
        for (ResourceLocation resourceLocation : resources.keySet()) {
            String modelPath = resourceLocation.getPath().substring(modelsPath.length() + 1).replace(".json", "");
            loadItemModelAndDependencies(ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), "morph/" + modelPath));
        }
        instance.push("missing_model");
    }
}
