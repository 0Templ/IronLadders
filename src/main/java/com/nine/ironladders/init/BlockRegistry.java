package com.nine.ironladders.init;


import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.CopperLadderBlock;
import com.nine.ironladders.common.block.CryingObsidianLadder;
import com.nine.ironladders.common.utils.LadderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class BlockRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(IronLadders.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IronLadders.MODID);

    public static final DeferredBlock<Block> WAXED_OXIDIZED_COPPER_LADDER = register("waxed_oxidized_copper_ladder",
            properties -> new CopperLadderBlock(properties, LadderType.COPPER, WeatheringCopper.WeatherState.OXIDIZED),
            BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.WARPED_NYLIUM)
    );
    public static final DeferredBlock<Block> WAXED_WEATHERED_COPPER_LADDER = register("waxed_weathered_copper_ladder",
            properties -> new CopperLadderBlock(properties, LadderType.COPPER, WeatheringCopper.WeatherState.WEATHERED),
            BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.WARPED_STEM)
    );
    public static final DeferredBlock<Block> WAXED_EXPOSED_COPPER_LADDER = register("waxed_exposed_copper_ladder",
            properties -> new CopperLadderBlock(properties, LadderType.COPPER, WeatheringCopper.WeatherState.EXPOSED),
            BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
    );
    public static final DeferredBlock<Block> WAXED_COPPER_LADDER = register("waxed_copper_ladder",
            properties -> new CopperLadderBlock(properties, LadderType.COPPER, WeatheringCopper.WeatherState.UNAFFECTED),
            BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
    );
    public static final DeferredBlock<Block> OXIDIZED_COPPER_LADDER = register("oxidized_copper_ladder",
            properties -> new CopperLadderBlock(properties, LadderType.COPPER, WeatheringCopper.WeatherState.OXIDIZED),
            BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.WARPED_NYLIUM)
    );
    public static final DeferredBlock<Block> WEATHERED_COPPER_LADDER = register("weathered_copper_ladder",
            properties -> new CopperLadderBlock(properties, LadderType.COPPER, WeatheringCopper.WeatherState.WEATHERED),
            BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.WARPED_STEM)
    );
    public static final DeferredBlock<Block> EXPOSED_COPPER_LADDER = register("exposed_copper_ladder",
            properties -> new CopperLadderBlock(properties, LadderType.COPPER, WeatheringCopper.WeatherState.EXPOSED),
            BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
    );

    public static final DeferredBlock<Block> COPPER_LADDER = register("copper_ladder",
            properties -> new CopperLadderBlock(properties, LadderType.COPPER, WeatheringCopper.WeatherState.UNAFFECTED),
            BlockBehaviour.Properties.of().strength(0.8F, 6.0F).sound(SoundType.COPPER).pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_ORANGE)
    );
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final DeferredBlock<Block> IRON_LADDER = register("iron_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.IRON),
            BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.METAL)
    );
    public static final DeferredBlock<Block> GOLD_LADDER = register("gold_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.GOLD),
            BlockBehaviour.Properties.of().strength(1.2F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.GOLD)
    );
    public static final DeferredBlock<Block> DIAMOND_LADDER = register("diamond_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.DIAMOND),
            BlockBehaviour.Properties.of().strength(2.5F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.DIAMOND)
    );
    public static final DeferredBlock<Block> OBSIDIAN_LADDER = register("obsidian_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.OBSIDIAN),
            BlockBehaviour.Properties.of().strength(12.0F, 1200.0F).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK).mapColor(MapColor.COLOR_BLACK)
    );
    public static final DeferredBlock<Block> CRYING_OBSIDIAN_LADDER = register("crying_obsidian_ladder",
            properties -> new CryingObsidianLadder(properties, LadderType.CRYING_OBSIDIAN),
            BlockBehaviour.Properties.of().strength(12.0F, 1200.0F).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK).mapColor(MapColor.COLOR_BLACK)
    );
    public static final DeferredBlock<Block> NETHERITE_LADDER = register("netherite_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.NETHERITE),
            BlockBehaviour.Properties.of().strength(5F, 1200.0F).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK).sound(SoundType.NETHERITE_BLOCK).mapColor(MapColor.COLOR_BLACK),
            new Item.Properties().fireResistant()
    );
    public static final DeferredBlock<Block> BEDROCK_LADDER = register("bedrock_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.BEDROCK),
            BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).pushReaction(PushReaction.BLOCK).mapColor(MapColor.STONE).noLootTable()
    );
    public static final DeferredBlock<Block> STEEL_LADDER = register("steel_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.STEEL),
            BlockBehaviour.Properties.of().strength(2F, 9.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.DEEPSLATE)
    );
    public static final DeferredBlock<Block> TIN_LADDER = register("tin_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.TIN),
            BlockBehaviour.Properties.of().strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_WHITE)
    );
    public static final DeferredBlock<Block> LEAD_LADDER = register("lead_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.LEAD),
            BlockBehaviour.Properties.of().strength(5.0F, 9.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_LIGHT_GRAY)
    );
    public static final DeferredBlock<Block> BRONZE_LADDER = register("bronze_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.BRONZE),
            BlockBehaviour.Properties.of().strength(5.0F, 9.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_ORANGE)
    );
    public static final DeferredBlock<Block> SILVER_LADDER = register("silver_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.SILVER),
            BlockBehaviour.Properties.of().strength(3.0F, 4.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_WHITE)
    );
    public static final DeferredBlock<Block> ALUMINUM_LADDER = register("aluminum_ladder",
            properties -> new BaseMetalLadder(properties, LadderType.ALUMINUM),
            BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.METAL).pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_WHITE)
    );

    public static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties properties) {
        return register(name, factory, properties, new Item.Properties());
    }

    public static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties blockProps, Item.Properties itemProps) {
        DeferredBlock<T> ret = BLOCKS.register(name, () -> factory.apply(blockProps.setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(IronLadders.MODID, name)))));
        ITEMS.registerSimpleBlockItem(name, ret, itemProps);
        return ret;
    }
}