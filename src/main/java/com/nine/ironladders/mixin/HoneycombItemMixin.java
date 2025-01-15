package com.nine.ironladders.mixin;

import com.nine.ironladders.common.block.WeatheringLadder;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {

    @Inject(method = "getWaxed", at = @At("HEAD"), cancellable = true)
    private static void ironladders$getWaxed(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
        if (state.getBlock() instanceof WeatheringLadder){
            cir.setReturnValue(Optional.ofNullable(WeatheringLadder.WAXABLES.get().get(state.getBlock())).map((block -> block.withPropertiesOf(state))));
        }
    }
}
