package com.nine.ironladders.mixin.client;

import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(BlockStateModelLoader.class)
public abstract class BlockStateModelLoaderMixin {

    @Shadow public abstract void loadBlockStateDefinitions(ResourceLocation blockStateId, StateDefinition<Block, BlockState> stateDefenition);

    @Inject(method = "loadAllBlockStates", at = @At("HEAD"), cancellable = true)
    public void loadAllBlockStates(CallbackInfo ci) {
        if (!ModList.get().isLoaded("modernfix")) {
            return;
        }
        for (Block block : BlockRegistry.getLadders()) {
            block.getStateDefinition().getPossibleStates().forEach((state) -> {
                this.loadBlockStateDefinitions(block.builtInRegistryHolder().key().location(), block.getStateDefinition());
            });
        }
    }
}
