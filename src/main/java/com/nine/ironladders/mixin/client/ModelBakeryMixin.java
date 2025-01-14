package com.nine.ironladders.mixin.client;

import com.nine.ironladders.client.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {


    @Shadow public abstract UnbakedModel getModel(ResourceLocation modelLocation);

    @Shadow protected abstract void registerModelAndLoadDependencies(ModelResourceLocation modelLocation, UnbakedModel model);

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V"))
    private void ModelBakery(ProfilerFiller instance, String s) {
        if (!ModList.get().isLoaded("fabric_model_loading_api_v1")) {
            ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
            ClientHelper.initializeMorphModels(resourceManager);
            for (var entry : ClientHelper.morphModels.entrySet()) {
                loadItemModelAndDependencies_fabric(ResourceLocation.parse(entry.getValue()));
            }
        }
        instance.push("missing_model");
    }

    @Unique
    private void loadItemModelAndDependencies_fabric(ResourceLocation modelLocation) {
        ModelResourceLocation modelresourcelocation = ModelResourceLocation.inventory(modelLocation);
        ResourceLocation resourcelocation = modelLocation.withPrefix("item/");
        UnbakedModel unbakedmodel = getModel(resourcelocation);
        registerModelAndLoadDependencies(modelresourcelocation, unbakedmodel);
    }
}
