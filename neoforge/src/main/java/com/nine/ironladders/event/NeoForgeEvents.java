package com.nine.ironladders.event;

import com.nine.ironladders.ILClient;
import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.ItemProperties;
import com.nine.ironladders.client.render.NeoForgeMetalLadderBakedModel;
import com.nine.ironladders.init.ILBlocks;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class NeoForgeEvents {

	private NeoForgeEvents() {
	}
	
	@EventBusSubscriber(modid = ILCommon.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
	public static final class ClientGameEvents {
		@SubscribeEvent
		public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
			if (!(event.getEntity() instanceof LocalPlayer player)) return;
			ILClient.onPlayerLogout(player);
		}
	}
	
	@EventBusSubscriber(modid = ILCommon.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static final class ClientModEvents {
		
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			event.enqueueWork(ItemProperties::init);
		}
		
		
		@SubscribeEvent
		public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
			for (var block : ILBlocks.registeredLadders()) {
				wrapBlock(event, block);
			}
		}
		
		private static void wrapBlock(ModelEvent.ModifyBakingResult event, Block block) {
			var models = event.getModels();
			for (var state : block.getStateDefinition().getPossibleStates()) {
				var location = BlockModelShaper.stateToModelLocation(state);
				models.computeIfPresent(location, (id, original) ->
						(original instanceof NeoForgeMetalLadderBakedModel) ? original : new NeoForgeMetalLadderBakedModel(original)
				);
			}
		}

	}
	
	@EventBusSubscriber(modid = ILCommon.MODID, bus = EventBusSubscriber.Bus.GAME)
	public static final class CommonGameEvents {
		
		@SubscribeEvent
		public static void onTagsUpdated(TagsUpdatedEvent event) {
			ILCommon.tagsLoadEvent();
		}
		
		@SubscribeEvent
		public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
			if (!(event.getEntity() instanceof ServerPlayer player)) return;
			ILCommon.onPlayerLogin(player);
		}
	
	}
	
	@EventBusSubscriber(modid = ILCommon.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static final class CommonModEvents {
		
		
		@SubscribeEvent
		public static void onCommonSetup(FMLCommonSetupEvent event) {
			event.enqueueWork(() -> {
				try {
					CopperInjector.injectCopperLadders();
					ILCommon.LOGGER.info("Copper ladders are successfully injected into vanilla maps.");
				} catch (Throwable throwable) {
					ILCommon.LOGGER.error("Failed to inject copper ladders into vanilla maps. Copper weathering/waxing integration is disabled.", throwable);
				}
			});
		}
		
		@SubscribeEvent
		public static void onInterModEnqueue(InterModEnqueueEvent event) {
			/*if (ModList.get().isLoaded("theoneprobe")) {
				InterModComms.sendTo("theoneprobe", "getTheOneProbe", TheOneProbeSetup::new);
			}*/
		}
	}

}
