package com.nine.ironladders.mixin;

import com.nine.ironladders.common.block.WeatheringLadder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LightningBolt.class)
public class LightningBoltMixin {

    @Shadow
    private static void randomWalkCleaningCopper(Level level, BlockPos blockPos, BlockPos.MutableBlockPos mutableBlockPos, int d) {
    }

    @Inject(method = "clearCopperOnLightningStrike", at = @At("TAIL"))
    private static void ironladders$clearCopperOnLightningStrike(Level level, BlockPos blockPos, CallbackInfo ci) {
        BlockState blockstate = level.getBlockState(blockPos);
        if (blockstate.getBlock() instanceof WeatheringLadder) {
            level.setBlockAndUpdate(blockPos, WeatheringLadder.getFirst(level.getBlockState(blockPos)));
            BlockPos.MutableBlockPos blockpos$mutableblockpos = blockPos.mutable();
            int i = level.random.nextInt(3) + 3;
            for (int j = 0; j < i; ++j) {
                int k = level.random.nextInt(8) + 1;
                randomWalkCleaningCopper(level, blockPos, blockpos$mutableblockpos, k);
            }

        }
    }

    @Inject(method = "randomStepCleaningCopper", at = @At("TAIL"), cancellable = true)
    private static void ironladders$randomStepCleaningCopper(Level level, BlockPos pos, CallbackInfoReturnable ci) {
        for (BlockPos blockpos : BlockPos.randomInCube(level.random, 10, pos, 1)) {
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.getBlock() instanceof WeatheringLadder) {
                WeatheringLadder.getPrevious(blockstate).ifPresent((state) -> {
                    level.setBlockAndUpdate(blockpos, state);
                });
                level.levelEvent(3002, blockpos, -1);
                ci.setReturnValue(Optional.of(blockpos));
            }
        }
        ci.setReturnValue(Optional.empty());
    }

}
