package com.nine.ironladders.mixin.client;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nine.ironladders.client.render.MetalLadderBakedModel;
import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import static com.mojang.text2speech.Narrator.LOGGER;

@Mixin(ModelManager.class)
public class ModelManagerMixin {


    @Inject(method = "loadModels", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
            ordinal = 1
    ))
    private void ironladders$loadModels(ProfilerFiller profilerFiller, Map<ResourceLocation, AtlasSet.StitchResult> resultMap, ModelBakery modelBakery, CallbackInfoReturnable<ModelManager.ReloadState> cir) {
        modelBakery.bakedTopLevelModels.entrySet().stream()
                .filter(entry -> "ironladders".equals(entry.getKey().id().getNamespace()))
                .forEach(entry -> modelBakery.getBakedTopLevelModels().put(entry.getKey(), new MetalLadderBakedModel(entry.getValue())));
    }


    @Inject(method = "loadModels", at = @At(value = "HEAD"))
    private void ironladders$loadModels_mf(ProfilerFiller profilerFiller, Map<ResourceLocation, AtlasSet.StitchResult> resultMap, ModelBakery modelBakery, CallbackInfoReturnable<ModelManager.ReloadState> cir) {
        if (!ModList.get().isLoaded("modernfix")) {
            return;
        }
        Multimap<ModelResourceLocation, Material> multimap = HashMultimap.create();
        bakeModels(modelBakery, (location, material) -> {
            AtlasSet.StitchResult atlasset$stitchresult = resultMap.get(material.atlasLocation());
            TextureAtlasSprite textureatlassprite = atlasset$stitchresult.getSprite(material.texture());
            if (textureatlassprite != null) {
                return textureatlassprite;
            } else {
                multimap.put(location, material);
                return atlasset$stitchresult.missing();
            }
        });
        modelBakery.getBakedTopLevelModels().entrySet().stream()
                .filter(entry -> "ironladders".equals(entry.getKey().id().getNamespace()))
                .forEach(entry -> modelBakery.getBakedTopLevelModels().put(entry.getKey(), new MetalLadderBakedModel(entry.getValue())));
    }

    @Unique
    public void bakeModels(ModelBakery modelBakery,ModelBakery.TextureGetter textureGetter) {
        modelBakery.topLevelModels.forEach((p_351687_, p_351688_) -> {
            BakedModel bakedmodel = null;
            try {
                ModelBakery.ModelBakerImpl modelBakerImpl = ModelBakerImplAccessor.create(modelBakery, textureGetter, p_351687_);
                bakedmodel = modelBakerImpl.bakeUncached(p_351688_, BlockModelRotation.X0_Y0);
            } catch (Exception exception) {
                LOGGER.warn("Unable to bake model: '{}': {}", p_351687_, exception);
            }
            if (bakedmodel != null) {
                modelBakery.bakedTopLevelModels.put(p_351687_, bakedmodel);
            }
        });
    }
}
