package com.nine.ironladders;

import com.nine.ironladders.client.MorphModelPredicateProvider;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.init.BlockRegistry;
import com.nine.ironladders.init.ItemRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.mixin.blockrenderlayer.RenderLayersMixin;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.ColorResolver;

public class IronLaddersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        makeMorphItem(ItemRegistry.MORPH_UPGRADE_ITEM);
        registerBlockColors();
        registerBlockLayers();
    }
    public void makeMorphItem(Item item) {
        ModelPredicateProviderRegistry.register(item, new Identifier("morph_type"), new MorphModelPredicateProvider());
    }

    private static final BlockColorProvider BLOCK_COLOR_PROVIDER = (state, world, pos, tintIndex) -> {
        if (state.getBlock() instanceof BaseMetalLadder ladder && ladder.isVines(state)) {
            return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor();
        }
        return 0;
    };
    public static void registerBlockLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COPPER_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.EXPOSED_COPPER_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WEATHERED_COPPER_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.OXIDIZED_COPPER_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WAXED_COPPER_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.IRON_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.GOLD_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.DIAMOND_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.NETHERITE_LADDER, RenderLayer.getCutout());

    }
    public static void registerBlockColors() {
        ColorProviderRegistry.BLOCK.register(BLOCK_COLOR_PROVIDER,
                BlockRegistry.COPPER_LADDER,
                BlockRegistry.EXPOSED_COPPER_LADDER,
                BlockRegistry.WEATHERED_COPPER_LADDER,
                BlockRegistry.OXIDIZED_COPPER_LADDER,
                BlockRegistry.WAXED_COPPER_LADDER,
                BlockRegistry.WAXED_EXPOSED_COPPER_LADDER,
                BlockRegistry.WAXED_WEATHERED_COPPER_LADDER,
                BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER,
                BlockRegistry.IRON_LADDER,
                BlockRegistry.GOLD_LADDER,
                BlockRegistry.DIAMOND_LADDER,
                BlockRegistry.NETHERITE_LADDER);
    }


}
