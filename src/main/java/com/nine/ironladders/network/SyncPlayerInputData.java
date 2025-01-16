package com.nine.ironladders.network;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.utils.PlayerInputDataProvider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncPlayerInputData(Boolean value) implements CustomPacketPayload {

    public static final Type<SyncPlayerInputData> ID =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IronLadders.MODID,"sync_input_data_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncPlayerInputData> PACKET_CODEC =
            StreamCodec.ofMember(
                    SyncPlayerInputData::write, SyncPlayerInputData::read);

    public static SyncPlayerInputData read(RegistryFriendlyByteBuf buf) {
        Boolean value = buf.readBoolean();
        return new SyncPlayerInputData(value);
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(value);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static void onMessage(SyncPlayerInputData packet, IPayloadContext ctx) {
        Player player = ctx.player();
        MinecraftServer server = player.getServer();
        if (server == null){
            return;
        }
        server.execute(() -> {
            if (player instanceof PlayerInputDataProvider) {
                ((PlayerInputDataProvider) player).setForwardImpulse(packet.value());
            }
        });
    }
}
