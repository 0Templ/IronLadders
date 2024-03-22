package com.nine.ironladders.init;

import com.nine.ironladders.IronLadders;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTabGroup {
    public static void register() {
        var tab = FabricItemGroup.builder()
                .title(Component.translatable("itemgroup.ironladders"))
                .icon(() -> new ItemStack(BlockRegistry.IRON_LADDER))
                .displayItems((parameters, output) -> {
                    output.accept(ItemRegistry.COPPER_UPGRADE);
                    output.accept(ItemRegistry.WOOD_IRON_UPGRADE);
                    output.accept(ItemRegistry.WOOD_GOLD_UPGRADE);
                    output.accept(ItemRegistry.WOOD_DIAMOND_UPGRADE);
                    output.accept(ItemRegistry.WOOD_NEHTERITE_UPGRADE);
                    output.accept(ItemRegistry.IRON_UPGRADE);
                    output.accept(ItemRegistry.GOLD_UPGRADE);
                    output.accept(ItemRegistry.DIAMOND_UPGRADE);
                    output.accept(ItemRegistry.NETHERITE_UPGRADE);
                    output.accept(ItemRegistry.POWER_UPGRADE_ITEM);
                    output.accept(ItemRegistry.LIGHT_UPGRADE_ITEM);
                    output.accept(ItemRegistry.HIDE_UPGRADE_ITEM);
                    output.accept(ItemRegistry.MORPH_UPGRADE_ITEM);

                    output.accept(BlockRegistry.COPPER_LADDER);
                    output.accept(BlockRegistry.EXPOSED_COPPER_LADDER);
                    output.accept(BlockRegistry.WEATHERED_COPPER_LADDER);
                    output.accept(BlockRegistry.OXIDIZED_COPPER_LADDER);
                    output.accept(BlockRegistry.WAXED_COPPER_LADDER);
                    output.accept(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER);
                    output.accept(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER);
                    output.accept(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER);

                    output.accept(BlockRegistry.IRON_LADDER);
                    output.accept(BlockRegistry.GOLD_LADDER);
                    output.accept(BlockRegistry.DIAMOND_LADDER);
                    output.accept(BlockRegistry.NETHERITE_LADDER);
                }).build();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,new ResourceLocation(IronLadders.MODID), tab);
    }

}

