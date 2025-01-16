package com.nine.ironladders.network;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MorphPacket(String value) implements CustomPacketPayload {

    public static final Type<MorphPacket> ID =
            new Type<>(ResourceLocation.fromNamespaceAndPath(IronLadders.MODID,"morph_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MorphPacket> PACKET_CODEC =
            StreamCodec.ofMember(
                    MorphPacket::write, MorphPacket::read);

    public static MorphPacket read(RegistryFriendlyByteBuf buf) {
        String value = buf.readUtf();
        return new MorphPacket(value);
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeUtf(value);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static void onMessage(MorphPacket packet, IPayloadContext ctx) {
        Player player = ctx.player();
        MinecraftServer server = player.getServer();
        if (server == null){
            return;
        }
        server.execute(() -> {
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof MorphUpgradeItem) {
                MorphUpgradeItem.writeMorphType(stack, packet.value());
            }
        });
    }
}
