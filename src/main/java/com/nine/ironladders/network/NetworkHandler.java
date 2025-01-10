package com.nine.ironladders.network;

import com.nine.ironladders.IronLadders;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(

            new ResourceLocation(IronLadders.MODID, "packets"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int packetId = 0;
        CHANNEL.registerMessage(packetId++, MorphPacket.class, MorphPacket::encode, MorphPacket::new, MorphPacket::onMessage);
        CHANNEL.registerMessage(packetId++, SyncPlayerInputData.class, SyncPlayerInputData::encode, SyncPlayerInputData::new, SyncPlayerInputData::onMessage);
    }
}
