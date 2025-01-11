package com.nine.ironladders.init;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.IronLadders;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabRegistry {


    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IronLadders.MODID);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("blocks", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemgroup.ironladders"))
            .icon(() -> new ItemStack(BlockRegistry.IRON_LADDER.get()))
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.COPPER_UPGRADE.get());
                output.accept(ItemRegistry.WOOD_IRON_UPGRADE.get());
                output.accept(ItemRegistry.WOOD_GOLD_UPGRADE.get());
                output.accept(ItemRegistry.WOOD_DIAMOND_UPGRADE.get());
                output.accept(ItemRegistry.WOOD_OBSIDIAN_UPGRADE.get());
                output.accept(ItemRegistry.WOOD_NETHERITE_UPGRADE.get());
                output.accept(ItemRegistry.IRON_UPGRADE.get());
                output.accept(ItemRegistry.GOLD_UPGRADE.get());
                output.accept(ItemRegistry.DIAMOND_UPGRADE.get());
                output.accept(ItemRegistry.OBSIDIAN_UPGRADE.get());
                output.accept(ItemRegistry.NETHERITE_UPGRADE.get());
                output.accept(ItemRegistry.POWER_UPGRADE_ITEM.get());
                output.accept(ItemRegistry.LIGHT_UPGRADE_ITEM.get());
                output.accept(ItemRegistry.HIDE_UPGRADE_ITEM.get());
                output.accept(ItemRegistry.MORPH_UPGRADE_ITEM.get());

                output.accept(BlockRegistry.COPPER_LADDER.get());
                output.accept(BlockRegistry.EXPOSED_COPPER_LADDER.get());
                output.accept(BlockRegistry.WEATHERED_COPPER_LADDER.get());
                output.accept(BlockRegistry.OXIDIZED_COPPER_LADDER.get());
                output.accept(BlockRegistry.WAXED_COPPER_LADDER.get());
                output.accept(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get());
                output.accept(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get());
                output.accept(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get());
                output.accept(BlockRegistry.IRON_LADDER.get());
                output.accept(BlockRegistry.GOLD_LADDER.get());
                output.accept(BlockRegistry.DIAMOND_LADDER.get());
                output.accept(BlockRegistry.OBSIDIAN_LADDER.get());
                if (!FMLEnvironment.production) {
                    output.accept(BlockRegistry.CRYING_OBSIDIAN_LADDER.get());
                    output.accept(BlockRegistry.BEDROCK_LADDER.get());
                }
                output.accept(BlockRegistry.NETHERITE_LADDER.get());

                //Non vanilla

                if (hasMaterial(new ResourceLocation("forge:ingots/tin"))) {
                    output.accept(ItemRegistry.WOOD_TIN_UPGRADE.get());
                    output.accept(BlockRegistry.TIN_LADDER.get());
                }
                if (hasMaterial(new ResourceLocation("forge:ingots/bronze"))) {
                    output.accept(ItemRegistry.WOOD_BRONZE_UPGRADE.get());
                    output.accept(BlockRegistry.BRONZE_LADDER.get());
                }
                if (hasMaterial(new ResourceLocation("forge:ingots/lead"))) {
                    output.accept(ItemRegistry.WOOD_LEAD_UPGRADE.get());
                    output.accept(BlockRegistry.LEAD_LADDER.get());
                }
                if (hasMaterial(new ResourceLocation("forge:ingots/steel"))) {
                    output.accept(ItemRegistry.WOOD_STEEL_UPGRADE.get());
                    output.accept(BlockRegistry.STEEL_LADDER.get());
                }
                if (hasMaterial(new ResourceLocation("forge:ingots/aluminum"))) {
                    output.accept(ItemRegistry.WOOD_ALUMINUM_UPGRADE.get());
                    output.accept(BlockRegistry.ALUMINUM_LADDER.get());
                }
                if (hasMaterial(new ResourceLocation("forge:ingots/silver"))) {
                    output.accept(ItemRegistry.WOOD_SILVER_UPGRADE.get());
                    output.accept(BlockRegistry.SILVER_LADDER.get());
                }
            }).build());

    public static boolean hasMaterial(ResourceLocation tagLocation) {
        if (!ILConfig.hideUncraftableLadders.get()) {
            return true;
        }
        TagKey<Item> tag = ItemTags.create(tagLocation);
        for (Item item : ForgeRegistries.ITEMS) {
            if (item.builtInRegistryHolder().is(tag)) {
                return true;
            }
        }
        return false;
    }
}