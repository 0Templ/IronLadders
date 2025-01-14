package com.nine.ironladders.mixin.client;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ModelBakery.ModelBakerImpl.class)
public interface ModelBakerImplAccessor {

    @Invoker("<init>")
    static ModelBakery.ModelBakerImpl create(ModelBakery modelBakery, ModelBakery.TextureGetter textureGetter, ModelResourceLocation modelLocation) {
        throw new AssertionError();
    }
}
