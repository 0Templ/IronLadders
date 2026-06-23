package com.nine.ironladders.event;

import com.nine.ironladders.ILClient;
import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.ItemProperties;
import com.nine.ironladders.client.render.ForgeTierLadderBakedModel;
import com.nine.ironladders.init.ILBlocks;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public final class ForgeEvents {
	
	private ForgeEvents() {}
	
	@Mod.EventBusSubscriber(modid = ILCommon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static final class ModCommon {
		
		private ModCommon() {}
		
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

	}
	
	@Mod.EventBusSubscriber(modid = ILCommon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static final class ModClient {
		
		private ModClient() {}
		
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
						(original instanceof ForgeTierLadderBakedModel) ? original : new ForgeTierLadderBakedModel(original)
				);
			}
		}
	}
	
	@Mod.EventBusSubscriber(modid = ILCommon.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static final class ForgeCommon {
		
		private ForgeCommon() {}
		
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
	
	@Mod.EventBusSubscriber(modid = ILCommon.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
	public static final class ForgeClient {
		
		private ForgeClient() {}
		
		@SubscribeEvent
		public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
			if (!(event.getEntity() instanceof LocalPlayer player)) return;
			ILClient.onPlayerLogout(player);
		}
	}
}
