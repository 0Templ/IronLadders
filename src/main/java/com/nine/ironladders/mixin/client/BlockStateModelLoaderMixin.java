package com.nine.ironladders.mixin.client;

import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

@Mixin(BlockStateModelLoader.class)
public abstract class BlockStateModelLoaderMixin {


    @Shadow @Final private ProfilerFiller profiler;

    @Shadow @Final private static Map<ResourceLocation, StateDefinition<Block, BlockState>> STATIC_DEFINITIONS;

    @Shadow public abstract void loadBlockStateDefinitions(ResourceLocation blockStateId, StateDefinition<Block, BlockState> stateDefenition);

    @Inject(method = "loadAllBlockStates", at = @At("HEAD"), cancellable = true)
    public void loadAllBlockStates(CallbackInfo ci) {
        if (!ModList.get().isLoaded("modernfix")) {
            return;
        }
        profiler.push("static_definitions");
        STATIC_DEFINITIONS.forEach(this::loadBlockStateDefinitions);
        this.profiler.popPush("blocks");
        for (Block block : BlockRegistry.getLadders()) {
            block.getStateDefinition().getPossibleStates().forEach((state) -> {
                this.loadBlockStateDefinitions(block.builtInRegistryHolder().key().location(), block.getStateDefinition());
            });
        }
        this.profiler.pop();
    }
}
