package com.nine.ironladders.datagen;

import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;

import java.util.concurrent.CompletableFuture;

public class ILDataMapsProvider extends DataMapProvider {

    protected ILDataMapsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        final var oxidizables = builder(NeoForgeDataMaps.OXIDIZABLES);
        final var waxables = builder(NeoForgeDataMaps.WAXABLES);

        oxidizables.add(BlockRegistry.COPPER_LADDER.get().builtInRegistryHolder(), new Oxidizable(BlockRegistry.EXPOSED_COPPER_LADDER.get()), false);
        oxidizables.add(BlockRegistry.EXPOSED_COPPER_LADDER.get().builtInRegistryHolder(), new Oxidizable(BlockRegistry.WEATHERED_COPPER_LADDER.get()), false);
        oxidizables.add(BlockRegistry.WEATHERED_COPPER_LADDER.get().builtInRegistryHolder(), new Oxidizable(BlockRegistry.OXIDIZED_COPPER_LADDER.get()), false);

        waxables.add(BlockRegistry.COPPER_LADDER.get().builtInRegistryHolder(), new Waxable(BlockRegistry.WAXED_COPPER_LADDER.get()), false);
        waxables.add(BlockRegistry.EXPOSED_COPPER_LADDER.get().builtInRegistryHolder(), new Waxable(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get()), false);
        waxables.add(BlockRegistry.WEATHERED_COPPER_LADDER.get().builtInRegistryHolder(), new Waxable(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get()), false);
        waxables.add(BlockRegistry.OXIDIZED_COPPER_LADDER.get().builtInRegistryHolder(), new Waxable(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get()), false);
    }


}
