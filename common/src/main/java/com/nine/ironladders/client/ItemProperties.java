package com.nine.ironladders.client;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.common.item.MorphToolItem;
import com.nine.ironladders.init.ILItems;
import com.nine.ironladders.mixin.accessor.client.ItemPropertiesAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ItemProperties {

	private ItemProperties() {
	}
	
	private static void registerProperties(){
		ItemPropertiesAccessor.register(ILItems.MORPH_TOOL.get(),
				new ResourceLocation(ILCommon.MODID, "morph_type"), new MorphModelPredicateProvider());
	}
	
	public static void init() {
		registerProperties();
	}
	
	static class MorphModelPredicateProvider implements ClampedItemPropertyFunction {
		
		@Override
		public float unclampedCall(ItemStack stack, ClientLevel clientWorld, LivingEntity livingEntity, int i) {
			return MorphToolItem.morphStateId(stack);
		}
		
		@Override
		public float call(ItemStack itemStack, ClientLevel clientWorld, LivingEntity livingEntity, int i) {
			return unclampedCall(itemStack, clientWorld, livingEntity, i);
		}
	}
	
}
