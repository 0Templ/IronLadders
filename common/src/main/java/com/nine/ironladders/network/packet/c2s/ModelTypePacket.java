package com.nine.ironladders.network.packet.c2s;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.model.ModelType;
import com.nine.ironladders.common.item.StylerToolItem;
import com.nine.ironladders.init.ILItems;
import com.nine.ironladders.network.packet.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record ModelTypePacket(ModelType type) implements C2SPacket{
	
	public static final ResourceLocation ID = new ResourceLocation(ILCommon.MODID, "model_type_packet");
	
	@Override
	public ResourceLocation id() {
		return ID;
	}
	
	@Override
	public void encode(FriendlyByteBuf buf) {
		buf.writeUtf(type.key);
	}
	
	public static ModelTypePacket decode(FriendlyByteBuf buf) {
		return new ModelTypePacket(ModelType.byKey(buf.readUtf()));
	}
	
	@Override
	public void handle(PacketContext context) {
		var player = context.serverPlayer();
		if (player == null || player.getCooldowns().isOnCooldown(ILItems.STYLER_TOOL.get())) {
			return;
		}
		ItemStack stack = player.getMainHandItem();
		if (stack.getItem() instanceof StylerToolItem){
			StylerToolItem.writeType(stack, type);
		}
	}
}
