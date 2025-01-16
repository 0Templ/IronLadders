package com.nine.ironladders.mixin.client;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(ItemModelResolver.class)
public abstract class ItemModelResolverMixin {

    @Shadow
    @Final
    private Function<ResourceLocation, ItemModel> modelGetter;

    @Shadow
    private static void fixupSkullProfile(ItemStack stack) {
    }

    @Unique
    private ResourceManager resourceManager;

    @Unique
    private Map<String, String> morphModels;

    @Unique
    private void initializeResourceManager() {
        resourceManager = Minecraft.getInstance().getResourceManager();
    }

    @Inject(method = "appendItemLayers", at = @At("HEAD"), cancellable = true)
    public void appendItemLayers(ItemStackRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, Level level, LivingEntity entity, int seed, CallbackInfo ci) {
        if (stack.getItem() instanceof MorphUpgradeItem morphUpgradeItem && !MorphUpgradeItem.getMorphType(stack).isEmpty()) {
            if (resourceManager == null || resourceManager.listPacks().toList().isEmpty()) {
                this.initializeResourceManager();
            }
            if (morphModels == null || morphModels.isEmpty()) {
                morphModels = new HashMap<>();
                String modelsPath = "items/morph_ladders";
                Predicate<ResourceLocation> filter = resourceLocation -> resourceLocation.getPath().endsWith(".json");
                for (var v : resourceManager.listResources(modelsPath, filter).keySet()) {
                    String location = v.toString();
                    try {
                        String d = location.split("\\.")[0].split("/")[2].split("_", 2)[1];
                        String f = location.split(":")[0] + ":morph_ladders/" + location.split("\\.")[0].split("/")[2];
                        morphModels.put(d, f);
                    } catch (Exception ignored) {
                        IronLadders.LOGGER.warn("Failed to load morph model: " + location);
                    }
                }
            }
            ItemModelResolver resolver = (ItemModelResolver) (Object) this;
            ResourceLocation resourcelocation;
            String morphType = MorphUpgradeItem.getMorphType(stack).split(":")[1];
            if (morphModels.containsKey(morphType)) {
                resourcelocation = ResourceLocation.parse(morphModels.get(morphType));
                if (resourcelocation != null) {
                    modelGetter
                            .apply(resourcelocation)
                            .update(renderState, stack, resolver, displayContext, level instanceof ClientLevel clientlevel ? clientlevel : null, entity, seed);
                }
                ci.cancel();
            }

        }
    }
}
