package com.nine.ironladders.mixin.feature.model.client;

import com.google.gson.JsonObject;
import com.mojang.math.Transformation;
import com.nine.ironladders.ILClient;
import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.ClientCache;
import com.nine.ironladders.client.model.ModelType;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.block.VariantLadderBlock;
import com.nine.ironladders.config.ILConfig;
import com.nine.ironladders.init.ILBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.block.model.multipart.MultiPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.Reader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(ModelManager.class)
public abstract class ModelManagerMixin {
	
	@Unique
	private static final List<ModelType> CUSTOM_TYPES = List.of(
			ModelType.V1, ModelType.V2, ModelType.V3, ModelType.VANILLA
	);
	
	
	// Replacing base blockstates
	@Inject(
			method = "loadBlockStates",
			at = @At("RETURN"),
			cancellable = true
	)
	private static void il$loadBlockStates(
			ResourceManager resourceManager,
			Executor executor,
			CallbackInfoReturnable<CompletableFuture<Map<ResourceLocation,
					List<ModelBakery.LoadedJson>>>> cir
	) {
		CompletableFuture<Map<ResourceLocation, List<ModelBakery.LoadedJson>>> modified =
				cir.getReturnValue().thenApply(map -> {
					Map<ResourceLocation, List<ModelBakery.LoadedJson>> mutableMap = new HashMap<>(map);
					for (ResourceLocation location : map.keySet()) {
						if (location.getNamespace().equals(ILCommon.MODID)) {
							ModelType modelType = ILConfig.DEFAULT_MODEL_TYPE.get();
							boolean useMulti = il$shouldUseMultivariant(location, modelType);
							ResourceLocation altLocation = new ResourceLocation(
									location.getNamespace(),
									location.getPath()
											.replace("blockstates", ILClient.ALT_BLOCK_STATES_DIR)
											.replace(".json", "_" + modelType.blockStateKey(useMulti) + ".json")
							);
							Optional<Resource> altResource = resourceManager.getResource(altLocation);
							if (altResource.isPresent()) {
								try (Reader reader = altResource.get().openAsReader()) {
									JsonObject json = GsonHelper.parse(reader);
									List<ModelBakery.LoadedJson> altList = List.of(
											new ModelBakery.LoadedJson(altResource.get().sourcePackId(), json)
									);
									mutableMap.put(location, altList);
								} catch (Exception ignored) {
								}
							}
						}
					}
					return Map.copyOf(mutableMap);
				});
		cir.setReturnValue(modified);
	}
	
	@Inject(method = "loadModels", at = @At("TAIL"))
	private void il$loadModels(ProfilerFiller profiler, Map<ResourceLocation, AtlasSet.StitchResult> atlasMap, ModelBakery bakery, CallbackInfoReturnable<?> cir) {
		ClientCache.CACHE.clear();
		
		ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
		
		Function<Material, TextureAtlasSprite> spriteGetter = mat -> {
			AtlasSet.StitchResult result = atlasMap.get(mat.atlasLocation());
			TextureAtlasSprite sprite = result.getSprite(mat.texture());
			return sprite != null ? sprite : result.missing();
		};
		
		// Simplified ModelBakerImpl
		ModelBaker baker = new ModelBaker() {
			
			private final Map<ResourceLocation, Map<Transformation, BakedModel[]>> cache = new HashMap<>();
		
			public UnbakedModel getModel(ResourceLocation location) {
				return bakery.getModel(location);
			}
	
			public BakedModel bake(ResourceLocation location, ModelState state) {
				return cache.computeIfAbsent(location, k -> new HashMap<>())
						.computeIfAbsent(state.getRotation(), k -> new BakedModel[2])
						[state.isUvLocked() ? 1 : 0] =
						bakery.getModel(location).bake(this, spriteGetter, state, location);
			}
			
			// Forge-only hooks
			// TODO: move to a platform helper..
			public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
				return spriteGetter;
			}
			
			public BakedModel bake(ResourceLocation location, ModelState state, Function<Material, TextureAtlasSprite> atlasSpriteFunction){
				return bake(location, state);
			}
		};
		
		BakedModel missingModel = bakery.getBakedTopLevelModels().getOrDefault(
				ModelBakery.MISSING_MODEL_LOCATION,
				bakery.getModel(ModelBakery.MISSING_MODEL_LOCATION).bake(baker, spriteGetter, BlockModelRotation.X0_Y0, ModelBakery.MISSING_MODEL_LOCATION)
		);
		
		for (ModelType type : CUSTOM_TYPES) {
			Map<BlockState, BakedModel> typeCache = new IdentityHashMap<>();
			Map<BlockState, BakedModel> bakedCache = new IdentityHashMap<>();
			
			for (Block block : ILBlocks.AVAILABLE_LADDERS.all()) {
				if (block == null) continue;
				var json = il$loadJson(resourceManager, block, type);
				if (json == null) continue;
				
				try {
					StateDefinition<Block, BlockState> stateDef = block.getStateDefinition();
					BlockModelDefinition.Context context = new BlockModelDefinition.Context();
					context.setDefinition(stateDef);
					BlockModelDefinition definition = BlockModelDefinition.fromJsonElement(
							context, json.data());
					
					Map<BlockState, UnbakedModel> unbakedMap = il$mapStates(definition, stateDef);
					
					for (BlockState state : stateDef.getPossibleStates()) {
						BlockState renderState = il$normalizeState(state);
						BakedModel baked = bakedCache.get(renderState);
						if (baked == null) {
							UnbakedModel unbaked = unbakedMap.getOrDefault(renderState, bakery.getModel(ModelBakery.MISSING_MODEL_LOCATION));
							unbaked.resolveParents(bakery::getModel);
							try {
								baked = unbaked.bake(baker, spriteGetter, BlockModelRotation.X0_Y0, BlockModelShaper.stateToModelLocation(renderState));
							} catch (Exception e) {
								baked = missingModel;
							}
							bakedCache.put(renderState, baked != null ? baked : missingModel);
						}
						typeCache.put(state, baked != null ? baked : missingModel);
					}
				}
				catch (Exception e) {
					ILCommon.LOGGER.warn("Couldn't process custom blockstate for: {}", block, e);
				}
			}
			if (!typeCache.isEmpty()) {
				ClientCache.CACHE.put(type, typeCache);
			}
		}
	}
	
	@Unique
	private ModelBakery.LoadedJson il$loadJson(ResourceManager manager, Block block, ModelType type) {
		ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
		boolean useMulti = type.isMultivariant() && block instanceof VariantLadderBlock;
		ResourceLocation location = new ResourceLocation(ILCommon.MODID,
				ILClient.ALT_BLOCK_STATES_DIR + "/" + id.getPath() + "_" + type.blockStateKey(useMulti) + ".json");
		return manager.getResource(location).flatMap(res -> {
			try (Reader reader = res.openAsReader()) {
				return Optional.of(new ModelBakery.LoadedJson(res.sourcePackId(), GsonHelper.parse(reader)));
			} catch (Exception e) {
				return Optional.empty();
			}
		}).orElse(null);
	}

	@Unique
	private static boolean il$shouldUseMultivariant(ResourceLocation location, ModelType type) {
		if (!type.isMultivariant()) {
			return false;
		}
		String path = location.getPath();
		if (!path.startsWith("blockstates/")) {
			return false;
		}
		String blockPath = path.replace("blockstates/", "").replace(".json", "");
		ResourceLocation blockId = new ResourceLocation(location.getNamespace(), blockPath);
		if (!BuiltInRegistries.BLOCK.containsKey(blockId)) {
			return false;
		}
		Block block = BuiltInRegistries.BLOCK.get(blockId);
		return block instanceof VariantLadderBlock;
	}
	
	@Unique
	private static BlockState il$normalizeState(BlockState state) {
		BlockState result = state;
		result = result.setValue(BlockStateProperties.WATERLOGGED, false)
				.setValue(MetalLadderBlock.LIGHT_LEVEL, 0);
		if (!result.getValue(MetalLadderBlock.HAS_SENSOR)){
			result = result.setValue(MetalLadderBlock.SENSOR_ACTIVE, false);
		}
		return result;
	}
	
	@Unique
	private Map<BlockState, UnbakedModel> il$mapStates(
			BlockModelDefinition definition,
			StateDefinition<Block, BlockState> stateDef
	) {
		Map<BlockState, UnbakedModel> map = new IdentityHashMap<>();
		List<BlockState> states = stateDef.getPossibleStates();
		if (definition.isMultiPart()) {
			MultiPart multipart = definition.getMultiPart();
			states.forEach(s -> map.put(s, multipart));
		}
		for (var entry : definition.getVariants().entrySet()) {
			Predicate<BlockState> predicate = il$parsePredicate(stateDef, entry.getKey());
			MultiVariant variant = entry.getValue();
			for (BlockState state : states) {
				if (predicate.test(state)) {
					map.put(state, variant);
				}
			}
		}
		return map;
	}
	
	@Unique
	private Predicate<BlockState> il$parsePredicate(StateDefinition<Block, BlockState> def, String variantString) {
		Map<Property<?>, Comparable<?>> requirements = new HashMap<>();
		for (String part : variantString.split(",")) {
			String[] kv = part.split("=");
			if (kv.length == 2) {
				Property<?> prop = def.getProperty(kv[0]);
				if (prop != null)
					prop.getValue(kv[1]).ifPresent(val -> requirements.put(prop, val));
			}
		}
		
		return state -> {
			for (var entry : requirements.entrySet()) {
				if (!state.getValue(entry.getKey()).equals(entry.getValue())) {
					return false;
				}
			}
			return true;
		};
	}

}
