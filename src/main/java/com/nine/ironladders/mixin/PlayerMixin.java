package com.nine.ironladders.mixin;

import com.nine.ironladders.common.utils.PlayerInputDataProvider;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public class PlayerMixin implements PlayerInputDataProvider {

    @Unique
    private boolean forwardImpulse;

    @Unique
    @Override
    public boolean forwardImpulse() {
        return this.forwardImpulse;
    }

    @Unique
    @Override
    public void setForwardImpulse(Boolean data) {
        this.forwardImpulse = data;
    }
}
