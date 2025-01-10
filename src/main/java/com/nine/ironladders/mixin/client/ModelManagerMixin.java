package com.nine.ironladders.mixin.client;

import com.nine.ironladders.client.render.MetalLadderBakedModel;
import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
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
                .filter(entry -> "ironladders".equals(entry.getKey().getNamespace()))
                .forEach(entry -> modelBakery.bakedTopLevelModels.put(entry.getKey(), new MetalLadderBakedModel(entry.getValue())));
    }

    @Inject(method = "loadModels", at = @At(value = "HEAD"))
    private void ironladders$loadModels_mf(ProfilerFiller profilerFiller, Map<ResourceLocation, AtlasSet.StitchResult> resultMap, ModelBakery modelBakery, CallbackInfoReturnable<ModelManager.ReloadState> cir) {
        if (!ModList.get().isLoaded("modernfix")) {
            return;
        }
        for (Block block : BlockRegistry.getLadders()) {
            block.getStateDefinition().getPossibleStates().forEach((state) -> {
                loadTopLevel(modelBakery, BlockModelShaper.stateToModelLocation(state));
            });
        }
        bakeModels(modelBakery, (location, material) -> {
            AtlasSet.StitchResult atlasset$stitchresult = resultMap.get(material.atlasLocation());
            TextureAtlasSprite textureatlassprite = atlasset$stitchresult.getSprite(material.texture());
            return Objects.requireNonNullElseGet(textureatlassprite, atlasset$stitchresult::missing);
        });
        modelBakery.bakedTopLevelModels.entrySet().stream()
                .filter(entry -> "ironladders".equals(entry.getKey().getNamespace()))
                .forEach(entry -> modelBakery.bakedTopLevelModels.put(entry.getKey(), new MetalLadderBakedModel(entry.getValue())));
    }


    @Unique
    public void bakeModels(ModelBakery modelBakery, BiFunction<ResourceLocation, Material, TextureAtlasSprite> map) {
        Set<ResourceLocation> keys = new HashSet<>(modelBakery.topLevelModels.keySet());
        keys.forEach((location) -> {
            BakedModel bakedmodel = null;
            try {
                ModelBakery.ModelBakerImpl modelBakerImpl = ModelBakerImplAccessor.create(modelBakery, map, location);
                bakedmodel = modelBakerImpl.bake(location, BlockModelRotation.X0_Y0);
            } catch (Exception exception) {
                LOGGER.warn("Unable to bake model: '{}': {}", location, exception);
            }
            if (bakedmodel != null) {
                modelBakery.bakedTopLevelModels.put(location, bakedmodel);
            }
        });
    }

    @Unique
    private void loadTopLevel(ModelBakery modelBakery, ModelResourceLocation location) {
        UnbakedModel unbakedmodel = modelBakery.getModel(location);
        modelBakery.unbakedCache.put(location, unbakedmodel);
        modelBakery.topLevelModels.put(location, unbakedmodel);
    }

}
