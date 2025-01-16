package com.nine.ironladders.datagen;

import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ILBlockTagProvider extends BlockTagsProvider {


    public ILBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, lookupProvider, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                BlockRegistry.COPPER_LADDER.get(),
                BlockRegistry.EXPOSED_COPPER_LADDER.get(),
                BlockRegistry.WEATHERED_COPPER_LADDER.get(),
                BlockRegistry.OXIDIZED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get(),
                BlockRegistry.IRON_LADDER.get(),
                BlockRegistry.GOLD_LADDER.get(),
                BlockRegistry.DIAMOND_LADDER.get(),
                BlockRegistry.OBSIDIAN_LADDER.get(),
                BlockRegistry.CRYING_OBSIDIAN_LADDER.get(),
                BlockRegistry.NETHERITE_LADDER.get(),
                BlockRegistry.LEAD_LADDER.get(),
                BlockRegistry.TIN_LADDER.get(),
                BlockRegistry.STEEL_LADDER.get(),
                BlockRegistry.BRONZE_LADDER.get(),
                BlockRegistry.SILVER_LADDER.get(),
                BlockRegistry.ALUMINUM_LADDER.get()
        );

        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                BlockRegistry.COPPER_LADDER.get(),
                BlockRegistry.EXPOSED_COPPER_LADDER.get(),
                BlockRegistry.WEATHERED_COPPER_LADDER.get(),
                BlockRegistry.OXIDIZED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get(),
                BlockRegistry.IRON_LADDER.get(),
                BlockRegistry.TIN_LADDER.get(),
                BlockRegistry.LEAD_LADDER.get(),
                BlockRegistry.BRONZE_LADDER.get(),
                BlockRegistry.STEEL_LADDER.get(),
                BlockRegistry.ALUMINUM_LADDER.get()
        );
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                BlockRegistry.GOLD_LADDER.get(),
                BlockRegistry.SILVER_LADDER.get(),
                BlockRegistry.DIAMOND_LADDER.get()
        );
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                BlockRegistry.NETHERITE_LADDER.get(),
                BlockRegistry.OBSIDIAN_LADDER.get(),
                BlockRegistry.CRYING_OBSIDIAN_LADDER.get()
        );
        this.tag(BlockTags.CLIMBABLE).add(
                BlockRegistry.COPPER_LADDER.get(),
                BlockRegistry.EXPOSED_COPPER_LADDER.get(),
                BlockRegistry.WEATHERED_COPPER_LADDER.get(),
                BlockRegistry.OXIDIZED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get(),
                BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get(),
                BlockRegistry.IRON_LADDER.get(),
                BlockRegistry.GOLD_LADDER.get(),
                BlockRegistry.DIAMOND_LADDER.get(),
                BlockRegistry.OBSIDIAN_LADDER.get(),
                BlockRegistry.CRYING_OBSIDIAN_LADDER.get(),
                BlockRegistry.NETHERITE_LADDER.get(),
                BlockRegistry.BEDROCK_LADDER.get(),
                BlockRegistry.LEAD_LADDER.get(),
                BlockRegistry.TIN_LADDER.get(),
                BlockRegistry.STEEL_LADDER.get(),
                BlockRegistry.BRONZE_LADDER.get(),
                BlockRegistry.SILVER_LADDER.get(),
                BlockRegistry.ALUMINUM_LADDER.get()
        );
        this.tag(BlockTags.GUARDED_BY_PIGLINS).add(
                BlockRegistry.GOLD_LADDER.get()
        );
        this.tag(BlockTags.DRAGON_IMMUNE).add(
                BlockRegistry.OBSIDIAN_LADDER.get(),
                BlockRegistry.CRYING_OBSIDIAN_LADDER.get(),
                BlockRegistry.BEDROCK_LADDER.get()
        );
        this.tag(BlockTags.FEATURES_CANNOT_REPLACE).add(
                BlockRegistry.BEDROCK_LADDER.get()
        );
        this.tag(BlockTags.WITHER_IMMUNE).add(
                BlockRegistry.BEDROCK_LADDER.get()
        );

    }
}
