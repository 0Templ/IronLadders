package com.nine.ironladders.datagen.provider.model.util;

import com.google.gson.JsonElement;
import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.model.ModelType;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.block.VariantLadderBlock;
import com.nine.ironladders.mixin.accessor.common.TextureSlotAccessor;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ILBlockModelGenerator {

	private static final TextureSlot LADDER_TEXTURE_SLOT = TextureSlotAccessor.il$create("ladder_texture", null);
	private static final TextureSlot SENSOR_SIGNAL_TEXTURE_SLOT = TextureSlotAccessor.il$create("sensor_signal_texture", null);

	private static final TextureSlot LIGHT_TEXTURE_SLOT = TextureSlotAccessor.il$create("light_texture", null);

	
	private static final ResourceLocation SENSOR_OFF = new ResourceLocation(ILCommon.MODID, "block/sensor/sensor_off");
	private static final ResourceLocation SENSOR_ON = new ResourceLocation(ILCommon.MODID, "block/sensor/sensor_on");
	
	private static final ResourceLocation LIGHT_TEXTURE = new ResourceLocation(ILCommon.MODID, "block/light/light");
	
	public final Consumer<BlockStateGenerator> originalStateOutput;
	
	public final BiConsumer<ResourceLocation, Supplier<JsonElement>> stateOutput;
	public final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;
	
	public ILBlockModelGenerator(Consumer<BlockStateGenerator> originalStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> stateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput) {
		this.originalStateOutput = originalStateOutput;
		this.stateOutput = stateOutput;
		this.modelOutput = modelOutput;
	}
	
	public void generate(MetalLadderBlock targetBlock, MetalLadderBlock templateBlock) {
		if (targetBlock.equals(templateBlock)){
			generateModels(
					targetBlock,
					templateBlock
			);
		}
		generateBlockStates(
				targetBlock,
				templateBlock
		);
	}
	
	private void generateModels(
			MetalLadderBlock targetBlock,
			MetalLadderBlock templateBlock
	){
		// Base models
		for (var type : List.of(ModelType.V1, ModelType.V2, ModelType.V3, ModelType.VANILLA)) {
			String templateSuffix = "_" + type.key;
			String modelSuffix = type.blockStateSuffix(false);
			generateBaseModel(templateBlock, templateSuffix, modelSuffix, "");
			generateLightModel(templateBlock, templateSuffix, modelSuffix, "");
			generateSensorModels(templateBlock, templateSuffix, modelSuffix, "");
		}
		
		// Variant base models
		for (var type : List.of(ModelType.V1, ModelType.V2, ModelType.V3)) {
			if (targetBlock instanceof VariantLadderBlock variantBlock){
				for (int i = 0; i < variantBlock.variants; i++){
					String templateSuffix = "_" + type.key;
					String modelSuffix = type.blockStateSuffix(false) + "_variant_" + i;
					String textureSuffix = "_variant_" + i;
					generateBaseModel(templateBlock, templateSuffix, modelSuffix, textureSuffix);
					generateLightModel(templateBlock, templateSuffix, modelSuffix, textureSuffix);
					generateSensorModels(templateBlock, templateSuffix, modelSuffix, textureSuffix);
				}
			}
		}
	}
	
	private void generateBaseModel(
			MetalLadderBlock templateBlock,
			String templateSuffix,
			String modelSuffix,
			String textureSuffix
	){
		String basePath = "block/" + templateBlock.getType().key + "/" + BuiltInRegistries.BLOCK.getKey(templateBlock).getPath();
		
		// Base ladder
		var baseTemplate = new ModelTemplate(
				Optional.of(new ResourceLocation(ILCommon.MODID, "block/template/template_ladder" + templateSuffix)),
				Optional.empty(),
				LADDER_TEXTURE_SLOT,
				TextureSlot.PARTICLE
		);
		
		ResourceLocation modelLocation = new ResourceLocation(
				ILCommon.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(templateBlock).getPath() + modelSuffix
		);
		
		TextureMapping textureMapping = new TextureMapping()
				.put(LADDER_TEXTURE_SLOT, new ResourceLocation(ILCommon.MODID, basePath + "_block" + textureSuffix))
				.put(TextureSlot.PARTICLE, new ResourceLocation(ILCommon.MODID, basePath));
		
		baseTemplate.create(
				modelLocation,
				textureMapping,
				modelOutput
		);
	}

	private void generateSensorModels(
			MetalLadderBlock templateBlock,
			String templateSuffix,
			String modelSuffix,
			String textureSuffix
	){
		String basePath = "block/" + templateBlock.getType().key + "/" + BuiltInRegistries.BLOCK.getKey(templateBlock).getPath();
		String baseModelPath = "block/" + BuiltInRegistries.BLOCK.getKey(templateBlock).getPath() + modelSuffix;
		
		var sensorTemplate = new ModelTemplate(
				Optional.of(new ResourceLocation(ILCommon.MODID, "block/template/template_ladder" + templateSuffix + "_sensor")),
				Optional.empty(),
				LADDER_TEXTURE_SLOT,
				SENSOR_SIGNAL_TEXTURE_SLOT
		);

		ResourceLocation ladderTexture = new ResourceLocation(ILCommon.MODID, basePath + "_block" + textureSuffix);

		var mappingSensorOn = new TextureMapping()
				.put(LADDER_TEXTURE_SLOT, ladderTexture)
				.put(SENSOR_SIGNAL_TEXTURE_SLOT, SENSOR_ON);

		var mappingSensorOff = new TextureMapping()
				.put(LADDER_TEXTURE_SLOT, ladderTexture)
				.put(SENSOR_SIGNAL_TEXTURE_SLOT, SENSOR_OFF);

		var modelLocation = new ResourceLocation(
				ILCommon.MODID, baseModelPath + "_sensor_on"
		);
		sensorTemplate.create(
				modelLocation,
				mappingSensorOn,
				modelOutput
		);

		modelLocation = new ResourceLocation(
				ILCommon.MODID, baseModelPath + "_sensor_off"
		);
		sensorTemplate.create(
				modelLocation,
				mappingSensorOff,
				modelOutput
		);
	}
	
	private void generateLightModel(
			MetalLadderBlock templateBlock,
			String templateSuffix,
			String modelSuffix,
			String textureSuffix
	){
		String basePath = "block/" + templateBlock.getType().key + "/" + BuiltInRegistries.BLOCK.getKey(templateBlock).getPath();
		String baseModelPath = "block/" + BuiltInRegistries.BLOCK.getKey(templateBlock).getPath() + modelSuffix;
		
		var lightTemplate = new ModelTemplate(
				Optional.of(new ResourceLocation(ILCommon.MODID, "block/template/template_ladder" + templateSuffix + "_light")),
				Optional.empty(),
				LADDER_TEXTURE_SLOT,
				LIGHT_TEXTURE_SLOT
		);
		
		ResourceLocation ladderTexture = new ResourceLocation(ILCommon.MODID, basePath + "_block" + textureSuffix);
		var lightMapping = new TextureMapping()
				.put(LADDER_TEXTURE_SLOT, ladderTexture)
				.put(LIGHT_TEXTURE_SLOT, LIGHT_TEXTURE);
		
		var modelLocation = new ResourceLocation(
				ILCommon.MODID, baseModelPath + "_light"
		);
		
		lightTemplate.create(
				modelLocation,
				lightMapping,
				modelOutput
		);
	}
	
	private void generateBlockStates(
			MetalLadderBlock targetBlock,
			MetalLadderBlock templateBlock
	){
		// Base blockstate
		if (!(targetBlock instanceof VariantLadderBlock)) {
			generateBlockStateMultipart(
					targetBlock,
					templateBlock,
					ModelType.V1.blockStateSuffix(false),
					true
			);
		}
		for (var type : List.of(ModelType.V1, ModelType.V2, ModelType.V3, ModelType.VANILLA)) {
			generateBlockStateMultipart(
					targetBlock,
					templateBlock,
					type.blockStateSuffix(false)
			);
		}
		for (var type : List.of(ModelType.V1, ModelType.V2, ModelType.V3)) {
			generateMultiVariantBlockStateMultipart(
					targetBlock,
					templateBlock,
					type.blockStateSuffix(true),
					type.blockStateSuffix(false),
					false
			);
		}
		generateFallbackState(targetBlock, templateBlock);
	}
	
	private void generateFallbackState(
			MetalLadderBlock targetBlock,
			MetalLadderBlock templateBlock
	){
		var type = ModelType.V1;
		generateMultiVariantBlockStateMultipart(
				targetBlock,
				templateBlock,
				type.blockStateSuffix(true),
				type.blockStateSuffix(false),
				true
		);
	}
	
	
	private void generateBlockStateMultipart(
			MetalLadderBlock targetBlock,
			MetalLadderBlock templateBlock,
			String blockStateSuffix
	){
		generateBlockStateMultipart(targetBlock, templateBlock, blockStateSuffix, false);
	}
	
	// Non-multivariant multipart
	private void generateBlockStateMultipart(
			MetalLadderBlock targetBlock,
			MetalLadderBlock templateBlock,
			String blockStateSuffix,
			boolean useOriginalOutput
	){
		String blockId = BuiltInRegistries.BLOCK.getKey(targetBlock).getPath() + blockStateSuffix;
		String modelBlockId = BuiltInRegistries.BLOCK.getKey(templateBlock).getPath() + blockStateSuffix;

		MultiPartGenerator generator = MultiPartGenerator.multiPart(targetBlock);
		
		// Base ladder (by facing)
		for (var facing : Direction.Plane.HORIZONTAL) {
			generator.with(
					Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, facing),
					variantFor(new ResourceLocation(ILCommon.MODID, "block/" + modelBlockId), facing)
			);
		}
		
		// Light module
		ResourceLocation lightModel = new ResourceLocation(ILCommon.MODID, "block/" + modelBlockId + "_light");
		for (var facing : Direction.Plane.HORIZONTAL) {
			generator.with(
					Condition.condition()
							.term(BlockStateProperties.HORIZONTAL_FACING, facing)
							.term(MetalLadderBlock.LIGHTED, true),
					variantFor(lightModel, facing)
			);
		}
		
		// Sensor module
		ResourceLocation sensorOnModel = sensorModelId(modelBlockId, true);
		ResourceLocation sensorOffModel = sensorModelId(modelBlockId, false);
		for (var facing : Direction.Plane.HORIZONTAL) {
			generator.with(
					Condition.condition()
							.term(BlockStateProperties.HORIZONTAL_FACING, facing)
							.term(MetalLadderBlock.HAS_SENSOR, true)
							.term(MetalLadderBlock.SENSOR_ACTIVE, true),
					variantFor(sensorOnModel, facing)
			);
			generator.with(
					Condition.condition()
							.term(BlockStateProperties.HORIZONTAL_FACING, facing)
							.term(MetalLadderBlock.HAS_SENSOR, true)
							.term(MetalLadderBlock.SENSOR_ACTIVE, false),
					variantFor(sensorOffModel, facing)
			);
		}
		
		acceptState(generator, useOriginalOutput, blockId);
	}
	
	private void generateMultiVariantBlockStateMultipart(
			MetalLadderBlock targetBlock,
			MetalLadderBlock templateBlock,
			String blockStateSuffix,
			String modelSuffix,
			boolean useOriginalOutput
	){
		if (!(targetBlock instanceof VariantLadderBlock variantLadderBlock)) return;
		String blockId = BuiltInRegistries.BLOCK.getKey(targetBlock).getPath() + blockStateSuffix;
		String modelBlockId = BuiltInRegistries.BLOCK.getKey(templateBlock).getPath() + modelSuffix;
		
		IntegerProperty variantProperty = variantLadderBlock.getVariantProperty();
		MultiPartGenerator generator = MultiPartGenerator.multiPart(targetBlock);

		// Base ladder
		for (int i = 0; i < variantLadderBlock.variants; i++) {
			String variantId = modelBlockId + "_variant_" + i;
			ResourceLocation baseModel = new ResourceLocation(ILCommon.MODID, "block/" + variantId);
			for (var facing : Direction.Plane.HORIZONTAL) {
				generator.with(
						Condition.condition()
								.term(BlockStateProperties.HORIZONTAL_FACING, facing)
								.term(variantProperty, i),
						variantFor(baseModel, facing)
				);
			}
		}
		
		// Light module
		for (int i = 0; i < variantLadderBlock.variants; i++) {
			ResourceLocation lightModel = new ResourceLocation(ILCommon.MODID, "block/" + modelBlockId + "_variant_" + i + "_light");
			for (var facing : Direction.Plane.HORIZONTAL) {
				generator.with(
						Condition.condition()
								.term(BlockStateProperties.HORIZONTAL_FACING, facing)
								.term(variantProperty, i)
								.term(MetalLadderBlock.LIGHTED, true),
						variantFor(lightModel, facing)
				);
			}
		}
		
		// Sensor module 
		for (int i = 0; i < variantLadderBlock.variants; i++) {
			ResourceLocation sensorOnModel = sensorModelId(modelBlockId + "_variant_" + i, true);
			ResourceLocation sensorOffModel = sensorModelId(modelBlockId + "_variant_" + i, false);
			for (var facing : Direction.Plane.HORIZONTAL) {
				generator.with(
						Condition.condition()
								.term(BlockStateProperties.HORIZONTAL_FACING, facing)
								.term(variantProperty, i)
								.term(MetalLadderBlock.HAS_SENSOR, true)
								.term(MetalLadderBlock.SENSOR_ACTIVE, true),
						variantFor(sensorOnModel, facing)
				);
				generator.with(
						Condition.condition()
								.term(BlockStateProperties.HORIZONTAL_FACING, facing)
								.term(variantProperty, i)
								.term(MetalLadderBlock.HAS_SENSOR, true)
								.term(MetalLadderBlock.SENSOR_ACTIVE, false),
						variantFor(sensorOffModel, facing)
				);
			}
		}
		
		acceptState(generator, useOriginalOutput, blockId);
	}
	
	private void acceptState(BlockStateGenerator generator, boolean useOriginalOutput, String blockId){
		ResourceLocation location = new ResourceLocation(ILCommon.MODID, blockId);
		
		if (useOriginalOutput){
			originalStateOutput.accept(generator);
		}
		else {
			stateOutput.accept(location, generator);
		}
	}

	private static Variant variantFor(ResourceLocation model, Direction facing){
		Variant variant = Variant.variant().with(VariantProperties.MODEL, model);
		return switch (facing){
			case EAST -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90);
			case SOUTH -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180);
			case WEST -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270);
			default -> variant;
		};
	}

	private static ResourceLocation sensorModelId(String baseModelId, boolean active){
		String suffix = active ? "_sensor_on" : "_sensor_off";
		return new ResourceLocation(ILCommon.MODID, "block/" + baseModelId + suffix);
	}
	
}
