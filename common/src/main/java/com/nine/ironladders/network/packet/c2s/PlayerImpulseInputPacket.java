package com.nine.ironladders.network.packet.c2s;

import com.nine.ironladders.ILCommon;
import com.nine.ironladders.common.util.PlayerImpulseInputProvider;
import com.nine.ironladders.network.packet.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record PlayerImpulseInputPacket(boolean forward) implements C2SPacket{
	
	public static final ResourceLocation ID = new ResourceLocation(ILCommon.MODID, "impulse_packet");
	
	@Override
	public ResourceLocation id() {
		return ID;
	}
	
	@Override
	public void encode(FriendlyByteBuf buf) {
		buf.writeBoolean(forward);
	}
	
	public static PlayerImpulseInputPacket decode(FriendlyByteBuf buf) {
		return new PlayerImpulseInputPacket(buf.readBoolean());
	}
	
	@Override
	public void handle(PacketContext context) {
		var player = context.serverPlayer();
		if (player == null) {
			return;
		}
		((PlayerImpulseInputProvider) player).il$setForwardImpulse(forward);
	}
}
