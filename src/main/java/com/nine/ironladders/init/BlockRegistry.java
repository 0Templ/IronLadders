package com.nine.ironladders.init;


import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.CopperLadderBlock;
import com.nine.ironladders.common.block.CryingObsidianLadder;
import com.nine.ironladders.common.utils.LadderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class BlockRegistry {


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IronLadders.MODID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IronLadders.MODID);

    public static final RegistryObject<Block> WAXED_OXIDIZED_COPPER_LADDER = register("waxed_oxidized_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.OXIDIZED, BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.WARPED_NYLIUM)));
    public static final RegistryObject<Block> WAXED_WEATHERED_COPPER_LADDER = register("waxed_weathered_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.WEATHERED, BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.WARPED_STEM)));
    public static final RegistryObject<Block> WAXED_EXPOSED_COPPER_LADDER = register("waxed_exposed_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.EXPOSED, BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryObject<Block> WAXED_COPPER_LADDER = register("waxed_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.UNAFFECTED, BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_ORANGE)));

    public static final RegistryObject<Block> OXIDIZED_COPPER_LADDER = register("oxidized_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.OXIDIZED, BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.WARPED_NYLIUM)));
    public static final RegistryObject<Block> WEATHERED_COPPER_LADDER = register("weathered_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.WEATHERED, BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.WARPED_STEM)));
    public static final RegistryObject<Block> EXPOSED_COPPER_LADDER = register("exposed_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.EXPOSED, BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryObject<Block> COPPER_LADDER = register("copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.UNAFFECTED, BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_ORANGE)));

    public static final RegistryObject<Block> IRON_LADDER = register("iron_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.METAL), LadderType.IRON));
    public static final RegistryObject<Block> GOLD_LADDER = register("gold_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(1.2F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.GOLD), LadderType.GOLD));
    public static final RegistryObject<Block> DIAMOND_LADDER = register("diamond_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(2.5F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.DIAMOND), LadderType.DIAMOND));
    public static final RegistryObject<Block> OBSIDIAN_LADDER = register("obsidian_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(12.0F, 1200.0F).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK).mapColor(MapColor.COLOR_BLACK), LadderType.OBSIDIAN));
    public static final RegistryObject<Block> CRYING_OBSIDIAN_LADDER = register("crying_obsidian_ladder",
            () -> new CryingObsidianLadder(BlockBehaviour.Properties.of().strength(12.0F, 1200.0F).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK).mapColor(MapColor.COLOR_BLACK), LadderType.CRYING_OBSIDIAN));
    public static final RegistryObject<Block> NETHERITE_LADDER = register("netherite_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(5F, 1200.0F).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK).sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_BLACK), LadderType.NETHERITE));
    public static final RegistryObject<Block> BEDROCK_LADDER = register("bedrock_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).pushReaction(PushReaction.BLOCK).sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.STONE).noLootTable(), LadderType.NETHERITE));


    public static final RegistryObject<Block> STEEL_LADDER = register("steel_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(2F, 9.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.DEEPSLATE), LadderType.STEEL));
    public static final RegistryObject<Block> TIN_LADDER = register("tin_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_WHITE), LadderType.TIN));
    public static final RegistryObject<Block> LEAD_LADDER = register("lead_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(5.0F, 9.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_LIGHT_GRAY), LadderType.LEAD));
    public static final RegistryObject<Block> BRONZE_LADDER = register("bronze_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(5.0F, 9.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_ORANGE), LadderType.BRONZE));
    public static final RegistryObject<Block> SILVER_LADDER = register("silver_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(3.0F, 4.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_WHITE), LadderType.SILVER));
    public static final RegistryObject<Block> ALUMINUM_LADDER = register("aluminum_ladder",
            () -> new BaseMetalLadder(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_WHITE), LadderType.ALUMINUM));

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<Block> block) {
        RegistryObject<? extends Block> ret = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return (RegistryObject<T>) ret;
    }

    public static List<Block> getLadders() {
        if (!ALUMINUM_LADDER.isPresent()) {
            return List.of();
        }
        return List.of(
                ALUMINUM_LADDER.get(),
                BRONZE_LADDER.get(),
                COPPER_LADDER.get(),
                DIAMOND_LADDER.get(),
                OBSIDIAN_LADDER.get(),
                CRYING_OBSIDIAN_LADDER.get(),
                EXPOSED_COPPER_LADDER.get(),
                GOLD_LADDER.get(),
                IRON_LADDER.get(),
                LEAD_LADDER.get(),
                NETHERITE_LADDER.get(),
                OXIDIZED_COPPER_LADDER.get(),
                SILVER_LADDER.get(),
                STEEL_LADDER.get(),
                TIN_LADDER.get(),
                WAXED_COPPER_LADDER.get(),
                WAXED_EXPOSED_COPPER_LADDER.get(),
                WAXED_OXIDIZED_COPPER_LADDER.get(),
                WAXED_WEATHERED_COPPER_LADDER.get(),
                WEATHERED_COPPER_LADDER.get()
        );
    }
}