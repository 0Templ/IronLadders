package com.nine.ironladders.mixin.client;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
    public void getModel(ItemStack stack, @Nullable Level level, @Nullable LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
        if (stack.getItem() instanceof MorphUpgradeItem) {
            if (!MorphUpgradeItem.getMorphType(stack).isEmpty()) {
                BakedModel bakedmodel = itemModelShaper.getModelManager().getModel(getCustomMorphModel(MorphUpgradeItem.getMorphType(stack).split(":")[1]));
                BakedModel bakedModelRet = bakedmodel.getOverrides().resolve(bakedmodel, stack, (ClientLevel) level, entity, seed);
                if (bakedModelRet.getParticleIcon().contents().name().toString().equals("minecraft:missingno")) {
                    cir.setReturnValue(itemModelShaper.getModelManager().getModel(getCustomMorphModel("unknown_ladder")));
                    return;
                }
                cir.setReturnValue(bakedModelRet);
            }
        }
    }

    @Unique
    private static ModelResourceLocation getCustomMorphModel(String name) {
        return new ModelResourceLocation(IronLadders.MODID, "morph/morph_" + name, "inventory");
    }
}
