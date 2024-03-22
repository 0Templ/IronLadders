package com.nine.ironladders.init;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.ToIntFunction;

import static com.nine.ironladders.common.utils.LadderProperties.LIGHTED;

public class BlockRegistry {
      public static final Block WAXED_OXIDIZED_COPPER_LADDER = registerBlock("waxed_oxidized_copper_ladder",
            new CopperLadderBlock(WeatheringCopper.WeatherState.OXIDIZED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.WARPED_NYLIUM).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final Block WAXED_WEATHERED_COPPER_LADDER = registerBlock("waxed_weathered_copper_ladder",
            new CopperLadderBlock(WeatheringCopper.WeatherState.WEATHERED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.WARPED_STEM).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final Block WAXED_EXPOSED_COPPER_LADDER = registerBlock("waxed_exposed_copper_ladder",
            new CopperLadderBlock(WeatheringCopper.WeatherState.EXPOSED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final Block WAXED_COPPER_LADDER = registerBlock("waxed_copper_ladder",
            new CopperLadderBlock(WeatheringCopper.WeatherState.UNAFFECTED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.COLOR_ORANGE).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));

    public static final Block OXIDIZED_COPPER_LADDER = registerBlock("oxidized_copper_ladder",
            new CopperLadderBlock(WeatheringCopper.WeatherState.OXIDIZED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.WARPED_NYLIUM).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final Block WEATHERED_COPPER_LADDER = registerBlock("weathered_copper_ladder",
            new CopperLadderBlock(WeatheringCopper.WeatherState.WEATHERED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.WARPED_STEM).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final Block EXPOSED_COPPER_LADDER = registerBlock("exposed_copper_ladder",
            new CopperLadderBlock(WeatheringCopper.WeatherState.EXPOSED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final Block COPPER_LADDER = registerBlock("copper_ladder",
            new CopperLadderBlock(WeatheringCopper.WeatherState.UNAFFECTED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.COLOR_ORANGE).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));

    public static final Block IRON_LADDER = registerBlock("iron_ladder",
            new IronLadderBlock(BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().mapColor(MapColor.METAL).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final Block GOLD_LADDER = registerBlock("gold_ladder",
            new GoldLadderBlock(BlockBehaviour.Properties.of().forceSolidOff().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final Block DIAMOND_LADDER = registerBlock("diamond_ladder",
            new DiamondLadderBlock(BlockBehaviour.Properties.of().forceSolidOff().strength(3.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().mapColor(MapColor.DIAMOND).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final Block NETHERITE_LADDER = registerBlock("netherite_ladder",
            new NetheriteLadderBlock(BlockBehaviour.Properties.of().forceSolidOff().strength(5.0F, 1200.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK).noOcclusion().mapColor(MapColor.COLOR_BLACK).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));

    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return (p_50763_) -> p_50763_.getValue(LIGHTED) ? lightLevel : 0;
    }
    public static int getMapColor(Block ladderBlock) {
        if (ladderBlock.equals(OXIDIZED_COPPER_LADDER) || ladderBlock.equals(WAXED_OXIDIZED_COPPER_LADDER)) {
            return 1474182;
        } else if (ladderBlock.equals(WEATHERED_COPPER_LADDER) || ladderBlock.equals(WAXED_WEATHERED_COPPER_LADDER)) {
            return 3837580;
        } else if (ladderBlock.equals(EXPOSED_COPPER_LADDER) || ladderBlock.equals(WAXED_EXPOSED_COPPER_LADDER)) {
            return 8874850;
        } else if (ladderBlock.equals(COPPER_LADDER) || ladderBlock.equals(WAXED_COPPER_LADDER)) {
            return 14188339;
        } else if (ladderBlock.equals(IRON_LADDER)) {
            return 10987431;
        } else if (ladderBlock.equals(GOLD_LADDER)) {
            return 16445005;
        } else if (ladderBlock.equals(DIAMOND_LADDER)) {
            return 6085589;
        } else if (ladderBlock.equals(NETHERITE_LADDER)) {
            return 1644825;
        }
        return 0;
    }

    public static int getMorphId(Block ladderBlock) {
        if (ladderBlock.equals(Blocks.LADDER)) {
            return 99;
        } else if (ladderBlock.equals(OXIDIZED_COPPER_LADDER) || ladderBlock.equals(WAXED_OXIDIZED_COPPER_LADDER)) {
            return 1;
        } else if (ladderBlock.equals(WEATHERED_COPPER_LADDER) || ladderBlock.equals(WAXED_WEATHERED_COPPER_LADDER)) {
            return 2;
        } else if (ladderBlock.equals(EXPOSED_COPPER_LADDER) || ladderBlock.equals(WAXED_EXPOSED_COPPER_LADDER)) {
            return 3;
        } else if (ladderBlock.equals(COPPER_LADDER) || ladderBlock.equals(WAXED_COPPER_LADDER)) {
            return 4;
        } else if (ladderBlock.equals(IRON_LADDER)) {
            return 5;
        } else if (ladderBlock.equals(GOLD_LADDER)) {
            return 6;
        } else if (ladderBlock.equals(DIAMOND_LADDER)) {
            return 7;
        } else if (ladderBlock.equals(NETHERITE_LADDER)) {
            return 8;
        }else if (ladderBlock instanceof VineBlock) {
            return 9;
        }
        return 0;
    }
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(IronLadders.MODID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IronLadders.MODID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    public static void register() {
    }
}
