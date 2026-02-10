package com.nine.ironladders.network.packet.c2s;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.client.model.ModelType;
import com.nine.ironladders.common.item.StylerToolItem;
import com.nine.ironladders.init.ILItems;
import com.nine.ironladders.network.packet.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record ModelTypePacket(ModelType modelType) implements C2SPacket{
	
	public static final Type<ModelTypePacket> ID = new CustomPacketPayload.Type<>(
			ResourceLocation.fromNamespaceAndPath(ILCommon.MODID, "model_type_packet"));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, ModelTypePacket> CODEC = StreamCodec.ofMember(
			ModelTypePacket::encode, ModelTypePacket::decode);
	
	@Override
	public Type<ModelTypePacket> type() {
		return ID;
	}
	
	@Override
	public void encode(FriendlyByteBuf buf) {
		buf.writeUtf(modelType.key);
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
			StylerToolItem.writeType(stack, modelType);
		}
	}
}

