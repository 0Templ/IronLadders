package com.nine.ironladders.network;

import com.nine.ironladders.common.utils.PlayerInputDataProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncPlayerInputData {

    private final Boolean value;

    public SyncPlayerInputData(Boolean value) {
        this.value = value;
    }

    public SyncPlayerInputData(FriendlyByteBuf buf) {
        value = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(value);
    }

    public void onMessage(Supplier<NetworkEvent.Context> ctx) {
        Player player = ctx.get().getSender();
        if (player != null) {
            if (player instanceof PlayerInputDataProvider) {
                ((PlayerInputDataProvider) player).setForwardImpulse(value);
            }
        }
    }

}
