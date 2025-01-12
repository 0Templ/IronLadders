package com.nine.ironladders.datagen;

import com.nine.ironladders.common.utils.LadderTags;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ILItemTagProvider extends ItemTagsProvider {

    public ILItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags) {
        super(pOutput, pLookupProvider, pBlockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.PIGLIN_LOVED).add(
                ItemRegistry.GOLD_UPGRADE.get(),
                ItemRegistry.WOOD_GOLD_UPGRADE.get(),
                BlockRegistry.GOLD_LADDER.get().asItem()
        );

        this.tag(LadderTags.UPGRADES_TO_IRON_UPGRADE).add(
                ItemRegistry.COPPER_UPGRADE.get(),
                ItemRegistry.WOOD_ALUMINUM_UPGRADE.get(),
                ItemRegistry.WOOD_TIN_UPGRADE.get(),
                ItemRegistry.WOOD_LEAD_UPGRADE.get()
        );

        this.tag(LadderTags.UPGRADES_TO_GOLD_UPGRADE).add(
                ItemRegistry.WOOD_IRON_UPGRADE.get(),
                ItemRegistry.WOOD_STEEL_UPGRADE.get(),
                ItemRegistry.WOOD_BRONZE_UPGRADE.get()
        );

        this.tag(LadderTags.UPGRADES_TO_DIAMOND_UPGRADE).add(
                ItemRegistry.WOOD_GOLD_UPGRADE.get(),
                ItemRegistry.WOOD_SILVER_UPGRADE.get()
        );

        this.tag(LadderTags.UPGRADES_TO_NETHERITE_UPGRADE).add(
                ItemRegistry.WOOD_DIAMOND_UPGRADE.get()
        );

        this.tag(LadderTags.UPGRADES_TO_OBSIDIAN_UPGRADE).add(
                ItemRegistry.WOOD_DIAMOND_UPGRADE.get()
        );

        this.tag(LadderTags.UPGRADES_TO_IRON).add(
                BlockRegistry.COPPER_LADDER.get().asItem(),
                BlockRegistry.EXPOSED_COPPER_LADDER.get().asItem(),
                BlockRegistry.OXIDIZED_COPPER_LADDER.get().asItem(),
                BlockRegistry.WEATHERED_COPPER_LADDER.get().asItem(),
                BlockRegistry.ALUMINUM_LADDER.get().asItem(),
                BlockRegistry.TIN_LADDER.get().asItem(),
                BlockRegistry.LEAD_LADDER.get().asItem()
        );

        this.tag(LadderTags.UPGRADES_TO_GOLD).add(
                BlockRegistry.IRON_LADDER.get().asItem(),
                BlockRegistry.STEEL_LADDER.get().asItem(),
                BlockRegistry.BRONZE_LADDER.get().asItem()
        );

        this.tag(LadderTags.UPGRADES_TO_DIAMOND).add(
                BlockRegistry.GOLD_LADDER.get().asItem(),
                BlockRegistry.SILVER_LADDER.get().asItem()
        );

        this.tag(LadderTags.UPGRADES_TO_NETHERITE).add(
                BlockRegistry.DIAMOND_LADDER.get().asItem()
        );

        this.tag(LadderTags.UPGRADES_TO_OBSIDIAN).add(
                BlockRegistry.DIAMOND_LADDER.get().asItem()
        );

    }


}
