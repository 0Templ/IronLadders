package com.nine.ironladders.datagen;

import com.nine.ironladders.common.utils.TagHelper;
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

        this.tag(TagHelper.COPPER_LADDER).add(
                BlockRegistry.COPPER_LADDER.get().asItem(),
                BlockRegistry.EXPOSED_COPPER_LADDER.get().asItem(),
                BlockRegistry.WEATHERED_COPPER_LADDER.get().asItem(),
                BlockRegistry.OXIDIZED_COPPER_LADDER.get().asItem(),
                BlockRegistry.WAXED_COPPER_LADDER.get().asItem(),
                BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get().asItem(),
                BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get().asItem(),
                BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get().asItem()
        );

    }


}
