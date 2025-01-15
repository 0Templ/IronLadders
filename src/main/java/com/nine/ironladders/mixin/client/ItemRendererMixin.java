package com.nine.ironladders.mixin.client;

import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Final
    @Shadow
    private ItemModelShaper itemModelShaper;

    @Inject(method = "getModel", at = @At(value = "HEAD"), cancellable = true)
    public void ironladders$getModel(ItemStack stack, @Nullable Level level, @Nullable LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
        if (!ModList.get().isLoaded("fabric_model_loading_api_v1")) {
            if (stack.getItem() instanceof MorphUpgradeItem) {
                String morphType = MorphUpgradeItem.getMorphType(stack);
                if (!morphType.isEmpty()) {
                    try {
                        morphType = morphType.split(":")[1];
                        var availableModels = ClientHelper.morphModels;
                        if (!availableModels.isEmpty() && availableModels.containsKey(morphType)) {
                            BakedModel bakedmodel = itemModelShaper.getModelManager().getModel(getCustomMorphModel(availableModels.get(morphType)));
                            BakedModel bakedModelRet = bakedmodel.getOverrides().resolve(bakedmodel, stack, (ClientLevel) level, entity, seed);
                            if (!bakedModelRet.getParticleIcon().contents().name().toString().equals("minecraft:missingno")) {
                                cir.setReturnValue(bakedModelRet);
                            }
                        } else {
                            cir.setReturnValue(unknownMorphLadder());
                        }
                    } catch (Exception e) {
                        cir.setReturnValue(unknownMorphLadder());
                    }
                }
            }
        }
    }

    @Unique
    private BakedModel unknownMorphLadder(){
        return itemModelShaper.getModelManager().getModel(getCustomMorphModel("ironladders:morph_ladders/morph_unknown_ladder"));
    }

    @Unique
    private static ModelResourceLocation getCustomMorphModel(String name) {
        return new ModelResourceLocation(new ResourceLocation(name), "inventory");
    }
}
