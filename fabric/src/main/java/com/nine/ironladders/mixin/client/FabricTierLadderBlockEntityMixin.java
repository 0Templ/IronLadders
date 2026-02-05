package com.nine.ironladders.mixin.client;

import com.nine.ironladders.client.LadderRenderData;
import com.nine.ironladders.common.block.entity.MetalLadderBlockEntity;
import net.fabricmc.fabric.api.blockview.v2.RenderDataBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MetalLadderBlockEntity.class)
public class FabricTierLadderBlockEntityMixin implements RenderDataBlockEntity {
	
	@Override
	public Object getRenderData() {
		var be = ((MetalLadderBlockEntity) (Object) this);
		return new LadderRenderData(be.morphState(), be.modelType(), be.isHideAttachments());
	}
	
}
