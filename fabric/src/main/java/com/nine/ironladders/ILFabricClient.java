package com.nine.ironladders;

import com.nine.ironladders.client.ItemProperties;
import com.nine.ironladders.client.render.FabricTierLadderBakedModel;
import com.nine.ironladders.event.FabricClientEvents;
import com.nine.ironladders.init.ILBlocks;
import com.nine.ironladders.network.ILFabricClientNetwork;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.renderer.RenderType;

public class ILFabricClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		ILFabricClientNetwork.initClient();
		
		registerRenderLayers();
		injectCustomModels();
		
		ItemProperties.init();
		
		FabricClientEvents.registerClient();
	}
	
	void injectCustomModels(){
		ModelLoadingPlugin.register(pluginContext -> {
			pluginContext.modifyModelAfterBake().register((model, ctx) -> {
				if (model == null) return null;
				
				var id = ctx.resourceId();
				if (id != null
						&& id.getNamespace().equals(ILCommon.MODID)
				) {
					return new FabricTierLadderBakedModel(model);
				}
				
				return model;
			});
		});
	}
	
	private void registerRenderLayers() {
		for (var ladder : ILBlocks.registeredLadders()) {
			BlockRenderLayerMap.INSTANCE.putBlock(ladder, RenderType.cutout());
		}
	}
}
