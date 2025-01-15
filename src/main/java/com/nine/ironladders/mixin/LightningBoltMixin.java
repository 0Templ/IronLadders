package com.nine.ironladders.mixin;

import com.nine.ironladders.common.block.WeatheringLadder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(LightningBolt.class)
public class LightningBoltMixin {

    @Redirect(method = "clearCopperOnLightningStrike", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/WeatheringCopper;getFirst(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private static BlockState ironladders$clearCopperOnLightningStrike(BlockState state) {
        if (state.getBlock() instanceof WeatheringLadder){
            return WeatheringLadder.getFirst(state);
        }
        return WeatheringCopper.getFirst(state);
    }

    @Redirect(method = "randomStepCleaningCopper", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/WeatheringCopper;getPrevious(Lnet/minecraft/world/level/block/state/BlockState;)Ljava/util/Optional;"))
    private static Optional<BlockState> ironladders$randomStepCleaningCopper(BlockState state, Level level, BlockPos blockpos ) {
        if (state.getBlock() instanceof WeatheringLadder){
            return WeatheringLadder.getPrevious(state);
        }
        return WeatheringCopper.getPrevious(state);
    }
}
