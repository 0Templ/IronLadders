package com.nine.ironladders.mixin.client;

import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Shadow @Final private UnbakedModel missingModel;

    @Shadow protected abstract void registerModelAndLoadDependencies(ModelResourceLocation modelLocation, UnbakedModel model);

    @Shadow protected abstract void loadItemModelAndDependencies(ResourceLocation modelLocation);

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void ironladders$ModelBakery(BlockColors blockColors, ProfilerFiller profilerFiller, Map modelResources, Map blockStateResources, CallbackInfo ci) {
////        if (!ModList.get().isLoaded("modernfix")){
////            return;
////        }
//        BlockStateModelLoader blockstatemodelloader = new BlockStateModelLoader(
//                blockStateResources, profilerFiller, missingModel, blockColors, this::registerModelAndLoadDependencies
//        );
//
//        //blockstatemodelloader.loadBlockStateDefinitions(BlockRegistry.DIAMOND_LADDER.get().builtInRegistryHolder().key().location(), BlockRegistry.DIAMOND_LADDER.get().getStateDefinition());
//
//        for (Block block : BlockRegistry.getLadders()) {
//            block.getStateDefinition().getPossibleStates().forEach((state) -> {
//                //blockstatemodelloader.loadBlockStateDefinitions(block.builtInRegistryHolder().key().location(), block.getStateDefinition());
//            });
//        }
    }

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
