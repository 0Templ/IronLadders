package com.nine.ironladders.mixin.client;

import com.nine.ironladders.client.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

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
    private void ironladders$ModelBakery(ProfilerFiller instance, String s) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if (!ModList.get().isLoaded("fabric_model_loading_api_v1")) {
            ClientHelper.initializeMorphModels(resourceManager);
            for (var entry : ClientHelper.morphModels.entrySet()) {
                loadTopLevel(new ModelResourceLocation(new ResourceLocation(entry.getValue()), "inventory"));
            }
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
