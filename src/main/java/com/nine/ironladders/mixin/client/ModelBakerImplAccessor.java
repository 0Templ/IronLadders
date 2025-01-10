package com.nine.ironladders.mixin.client;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiFunction;

@Mixin(ModelBakery.ModelBakerImpl.class)
public interface ModelBakerImplAccessor {

    @Invoker("<init>")
    static ModelBakery.ModelBakerImpl create(ModelBakery modelBakery, BiFunction<ResourceLocation, Material, TextureAtlasSprite> p_249651_, ResourceLocation p_251408_) {
        throw new AssertionError();
    }
}