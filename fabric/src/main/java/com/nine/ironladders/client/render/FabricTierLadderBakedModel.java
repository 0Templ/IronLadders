package com.nine.ironladders.client.render;

import com.nine.ironladders.client.ClientCache;
import com.nine.ironladders.client.LadderRenderData;
import com.nine.ironladders.client.model.ModelType;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.item.CasingToolItem;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class FabricTierLadderBakedModel extends ForwardingBakedModel {
	
	public FabricTierLadderBakedModel(BakedModel model) {
		this.wrapped = model;
	}
	
	/*// Todo: make the particles match the morph state
	public TextureAtlasSprite getParticleIcon() {
		return wrapped.getParticleIcon();
	}*/
	
	@Override
	public void emitBlockQuads(
			BlockAndTintGetter view,
			BlockState state,
			BlockPos pos,
			Supplier<RandomSource> randomSupplier,
			RenderContext context
	) {
		BlockState renderState = state;
		
		Object renderData = view.getBlockEntityRenderData(pos);
		
		ModelType customType = null;
		
		if (renderData instanceof LadderRenderData data) {
			var morph = data.state();
			customType = data.type();
			if (morph != null){
				if (morph.getBlock() instanceof MetalLadderBlock metalLadderBlock){
					renderState = metalLadderBlock.withPropertiesOf(state);
				}
				else {
					renderState = data.state();
				}
			}
			if (data.hideAttachments()){
				renderState = applyHiddenAttachmentState(renderState);
			}
		}
		BakedModel delegate;
		if (customType != null
				&& ClientCache.CACHE.containsKey(customType)
				&& ClientCache.CACHE.get(customType).get(renderState) != null
		){
			delegate = ClientCache.CACHE.get(customType).get(renderState);
		}
		else {
			delegate = (renderState == state)
					? this.wrapped
					: Minecraft.getInstance().getBlockRenderer().getBlockModel(renderState);
		}
		
		if (delegate == this) {
			delegate = this.wrapped;
		}
		delegate.emitBlockQuads(view, renderState, pos, randomSupplier, context);
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
	
	@Override
	public boolean isVanillaAdapter() {
		return false;
	}
	
}
