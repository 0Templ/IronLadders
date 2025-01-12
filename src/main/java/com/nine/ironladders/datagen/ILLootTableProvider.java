package com.nine.ironladders.datagen;

import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class ILLootTableProvider extends BlockLootSubProvider {

    public ILLootTableProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        this.dropSelf(BlockRegistry.COPPER_LADDER.get());
        this.dropSelf(BlockRegistry.WAXED_COPPER_LADDER.get());
        this.dropSelf(BlockRegistry.EXPOSED_COPPER_LADDER.get());
        this.dropSelf(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get());
        this.dropSelf(BlockRegistry.WEATHERED_COPPER_LADDER.get());
        this.dropSelf(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get());
        this.dropSelf(BlockRegistry.OXIDIZED_COPPER_LADDER.get());
        this.dropSelf(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get());
        this.dropSelf(BlockRegistry.IRON_LADDER.get());
        this.dropSelf(BlockRegistry.GOLD_LADDER.get());
        this.dropSelf(BlockRegistry.DIAMOND_LADDER.get());
        this.dropSelf(BlockRegistry.OBSIDIAN_LADDER.get());
        this.dropSelf(BlockRegistry.CRYING_OBSIDIAN_LADDER.get());
        this.dropSelf(BlockRegistry.NETHERITE_LADDER.get());
        this.dropSelf(BlockRegistry.ALUMINUM_LADDER.get());
        this.dropSelf(BlockRegistry.LEAD_LADDER.get());
        this.dropSelf(BlockRegistry.TIN_LADDER.get());
        this.dropSelf(BlockRegistry.STEEL_LADDER.get());
        this.dropSelf(BlockRegistry.BRONZE_LADDER.get());
        this.dropSelf(BlockRegistry.SILVER_LADDER.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map(DeferredHolder::value).collect(Collectors.toList());
    }
}
