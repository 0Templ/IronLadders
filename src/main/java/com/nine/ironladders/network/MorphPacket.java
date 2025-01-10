package com.nine.ironladders.network;

import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MorphPacket {

    private final String value;

    public MorphPacket(String value) {
        this.value = value;
    }

    public MorphPacket(FriendlyByteBuf buf) {
        value = buf.readUtf();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(value);
    }

    public void onMessage(Supplier<NetworkEvent.Context> ctx) {
        Player player = ctx.get().getSender();
        if (player != null) {
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof MorphUpgradeItem) {
                MorphUpgradeItem.writeMorphType(stack, value);
            }
        }
    }
}
