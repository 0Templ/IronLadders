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
    private static void randomWalkCleaningCopper(Level p_147146_, BlockPos p_147147_, BlockPos.MutableBlockPos p_147148_, int p_147149_) {}
    @Inject(method = "clearCopperOnLightningStrike", at = @At("TAIL"))
    private static void ironladders$clearCopperOnLightningStrike(Level p_147151_, BlockPos blockPos, CallbackInfo ci) {
        BlockState blockstate = p_147151_.getBlockState(blockPos);

        if (blockstate.getBlock() instanceof WeatheringLadder) {
            p_147151_.setBlockAndUpdate(blockPos, WeatheringLadder.getFirst(p_147151_.getBlockState(blockPos)));
            BlockPos.MutableBlockPos blockpos$mutableblockpos = blockPos.mutable();
            int i = p_147151_.random.nextInt(3) + 3;
            for (int j = 0; j < i; ++j) {
                int k = p_147151_.random.nextInt(8) + 1;
                randomWalkCleaningCopper(p_147151_, blockPos, blockpos$mutableblockpos, k);
            }
        }
    }
    @Inject(method = "randomStepCleaningCopper", at = @At("TAIL"),cancellable = true)
    private static void ironladders$randomStepCleaningCopper(Level p_147154_, BlockPos p_147155_, CallbackInfoReturnable ci) {
        for(BlockPos blockpos : BlockPos.randomInCube(p_147154_.random, 10, p_147155_, 1)) {
            BlockState blockstate = p_147154_.getBlockState(blockpos);
            if (blockstate.getBlock() instanceof WeatheringLadder) {
                WeatheringLadder.getPrevious(blockstate).ifPresent((p_147144_) -> {
                    p_147154_.setBlockAndUpdate(blockpos, p_147144_);
                });
                p_147154_.levelEvent(3002, blockpos, -1);
                ci.setReturnValue(Optional.of(blockpos));
            }
        }
        ci.setReturnValue(Optional.empty());
    }

}
