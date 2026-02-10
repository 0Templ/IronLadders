package com.nine.ironladders.client.render;

import com.nine.ironladders.client.ClientCache;
import com.nine.ironladders.client.LadderRenderData;
import com.nine.ironladders.client.model.ModelType;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.item.CasingToolItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;

import java.util.List;

public class NeoForgeMetalLadderBakedModel extends BakedModelWrapper<BakedModel> {

	public static final ModelProperty<LadderRenderData> MODEL_DATA = new ModelProperty<>();

	public NeoForgeMetalLadderBakedModel(BakedModel bakedModel) {
		super(bakedModel);
	}

	@Override
	public TextureAtlasSprite getParticleIcon(ModelData data) {
		ModelData safeData = data == null ? ModelData.EMPTY : data;
		LadderRenderData renderData = safeData.get(MODEL_DATA);
		if (renderData == null || renderData.state() == null) {
			return super.getParticleIcon(safeData);
		}

		BlockState renderState = renderData.state();
		if (renderData.hideAttachments()) {
			renderState = applyHiddenAttachmentState(renderState);
		}

		BakedModel delegate = resolveDelegate(renderState, renderData.type());
		if (delegate == this) {
			delegate = this.originalModel;
		} else if (delegate instanceof NeoForgeMetalLadderBakedModel wrapper) {
			delegate = wrapper.originalModel;
		}

		ModelData childData = (renderState.getBlock() instanceof MetalLadderBlock) ? ModelData.EMPTY : safeData;
		return delegate.getParticleIcon(childData);
	}

	@Override
	public List<BakedQuad> getQuads(
			BlockState state,
			Direction side,
			RandomSource rand,
			ModelData data,
			RenderType renderType) {
		if (state == null) {
			return super.getQuads(state, side, rand, data, renderType);
		}

		ModelData safeData = data == null ? ModelData.EMPTY : data;
		BlockState renderState = state;

		LadderRenderData renderData = safeData.get(MODEL_DATA);
		ModelType customType = null;
		if (renderData != null) {
			BlockState morph = renderData.state();
			customType = renderData.type();
			if (morph != null) {
				if (morph.getBlock() instanceof MetalLadderBlock metalLadderBlock) {
					renderState = metalLadderBlock.withPropertiesOf(state);
				} else {
					renderState = morph;
				}
			}
			if (renderData.hideAttachments()) {
				renderState = applyHiddenAttachmentState(renderState);
			}
		}

		BakedModel delegate = resolveDelegate(renderState, customType);
		if (delegate == this) {
			delegate = this.originalModel;
		}
		ModelData childData = (renderState.getBlock() instanceof MetalLadderBlock) ? ModelData.EMPTY : safeData;
		return delegate.getQuads(renderState, side, rand, childData, renderType);
	}

	@Override
	public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
		if (state == null) {
			return super.getRenderTypes(state, rand, data);
		}
		ModelData safeData = data == null ? ModelData.EMPTY : data;
		BlockState renderState = state;
		ModelType customType = null;

		LadderRenderData renderData = safeData.get(MODEL_DATA);
		if (renderData != null) {
			BlockState morph = renderData.state();
			customType = renderData.type();
			if (morph != null) {
				if (morph.getBlock() instanceof MetalLadderBlock metalLadderBlock) {
					renderState = metalLadderBlock.withPropertiesOf(state);
				} else {
					renderState = morph;
				}
			}
			if (renderData.hideAttachments()) {
				renderState = applyHiddenAttachmentState(renderState);
			}
		}

		BakedModel delegate = resolveDelegate(renderState, customType);
		if (delegate == this) {
			delegate = this.originalModel;
		} else if (delegate instanceof NeoForgeMetalLadderBakedModel wrapper) {
			delegate = wrapper.originalModel;
		}

		ModelData childData = (renderState.getBlock() instanceof MetalLadderBlock) ? ModelData.EMPTY : safeData;
		return delegate.getRenderTypes(renderState, rand, childData);
	}

	private BakedModel resolveDelegate(BlockState renderState, ModelType customType) {
		if (customType != null) {
			var cached = ClientCache.CACHE.get(customType);
			if (cached != null) {
				BakedModel cachedModel = cached.get(renderState);
				if (cachedModel != null) {
					return cachedModel;
				}
			}
		}

		return Minecraft.getInstance().getBlockRenderer().getBlockModel(renderState);
	}

	private static BlockState applyHiddenAttachmentState(BlockState state) {
		BlockState result = state;
		for (var element : CasingToolItem.PROPERTIES_TO_HIDE.entrySet()) {
			if (result.hasProperty(element.getKey())) {
				result = result.setValue(element.getKey(), element.getValue());
			}
		}
		return result;
	}
}
