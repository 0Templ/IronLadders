package com.nine.ironladders.datagen.provider.model.util;

import com.google.gson.JsonElement;
import com.nine.ironladders.ILCommon;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.util.MorphType;
import com.nine.ironladders.init.ILItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ILItemModels {
	
	public static void generateModels(ItemModelGenerators generators){
		var output = generators.output;

		
		// Ladder item models
		for (var data : ILBlockModels.SOURCE.entrySet()){
			if (data.getValue() instanceof MetalLadderBlock ladder){
				ResourceLocation texture = BuiltInRegistries.BLOCK.getKey(ladder)
						.withPrefix("block/" + ladder.getType().key + "/");
						
				ModelTemplates.FLAT_ITEM.create(
						ModelLocationUtils.getModelLocation(data.getKey().asItem()),
						TextureMapping.layer0(texture),
						output);
			}

		}
		
		generateMorphItemModels(output);
		
		generators.generateFlatItem(ILItems.SENSOR_TOOL.get(), ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ILItems.LIGHT_TOOL.get(), ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ILItems.STYLER_TOOL.get(), ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ILItems.CASING_TOOL.get(), ModelTemplates.FLAT_ITEM);
	}

	private static void generateMorphItemModels(BiConsumer<ResourceLocation, Supplier<JsonElement>> output){
		ResourceLocation baseTexture = new ResourceLocation(ILCommon.MODID, "item/ladder_morph_tool");
		for (MorphType type : MorphType.values()) {
			if (type == MorphType.NONE) continue;
			if (type.key == null || type.key.isBlank()) continue;
			ResourceLocation modelId = new ResourceLocation(ILCommon.MODID, "item/morph/morph_" + type.key);
			ResourceLocation overlayTexture = new ResourceLocation(ILCommon.MODID, "item/morph/morph_" + type.key);

			TextureMapping mapping = new TextureMapping();
			mapping.put(TextureSlot.LAYER0, baseTexture);
			mapping.put(TextureSlot.LAYER1, overlayTexture);
			ModelTemplates.TWO_LAYERED_ITEM.create(modelId, mapping, output);
		}
	}
}
