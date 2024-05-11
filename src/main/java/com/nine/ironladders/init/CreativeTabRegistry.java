package com.nine.ironladders.init;

import com.nine.ironladders.IronLadders;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
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
                output.accept(ItemRegistry.WOOD_NEHTERITE_UPGRADE.get());
                output.accept(ItemRegistry.IRON_UPGRADE.get());
                output.accept(ItemRegistry.GOLD_UPGRADE.get());
                output.accept(ItemRegistry.DIAMOND_UPGRADE.get());
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
                output.accept(BlockRegistry.NETHERITE_LADDER.get());

            }).build());
}