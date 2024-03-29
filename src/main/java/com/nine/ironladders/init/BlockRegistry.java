package com.nine.ironladders.init;


import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IronLadders.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IronLadders.MODID);

    public static final RegistryObject<Block> WAXED_OXIDIZED_COPPER_LADDER = register("waxed_oxidized_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.OXIDIZED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.WARPED_NYLIUM).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> WAXED_WEATHERED_COPPER_LADDER = register("waxed_weathered_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.WEATHERED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.WARPED_STEM).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> WAXED_EXPOSED_COPPER_LADDER = register("waxed_exposed_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.EXPOSED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> WAXED_COPPER_LADDER = register("waxed_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.UNAFFECTED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.COLOR_ORANGE).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));

    public static final RegistryObject<Block> OXIDIZED_COPPER_LADDER = register("oxidized_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.OXIDIZED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.WARPED_NYLIUM).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> WEATHERED_COPPER_LADDER = register("weathered_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.WEATHERED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.WARPED_STEM).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> EXPOSED_COPPER_LADDER = register("exposed_copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.EXPOSED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> COPPER_LADDER = register("copper_ladder",
            () -> new CopperLadderBlock(WeatheringCopper.WeatherState.UNAFFECTED,BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.COLOR_ORANGE).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));


    public static final RegistryObject<Block> IRON_LADDER = register("iron_ladder",
            () -> new IronLadderBlock(BlockBehaviour.Properties.of().forceSolidOff().strength(2.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().mapColor(MapColor.METAL).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> GOLD_LADDER = register("gold_ladder",
            () -> new GoldLadderBlock(BlockBehaviour.Properties.of().forceSolidOff().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> DIAMOND_LADDER = register("diamond_ladder",
            () -> new DiamondLadderBlock(BlockBehaviour.Properties.of().forceSolidOff().strength(3.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().mapColor(MapColor.DIAMOND).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> NETHERITE_LADDER = register("netherite_ladder",
            () -> new NetheriteLadderBlock(BlockBehaviour.Properties.of().forceSolidOff().strength(5.0F, 1200.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK).noOcclusion().mapColor(MapColor.COLOR_BLACK).pushReaction(PushReaction.DESTROY).lightLevel(litBlockEmission(13))));

    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return (p_50763_) -> p_50763_.getValue(LIGHTED) ? lightLevel : 0;
    }
    public static int getMorphId(Block ladderBlock) {
        if (ladderBlock.equals(Blocks.LADDER)) {
            return 99;
        } else if (ladderBlock.equals(OXIDIZED_COPPER_LADDER.get()) || ladderBlock.equals(WAXED_OXIDIZED_COPPER_LADDER.get())) {
            return 1;
        } else if (ladderBlock.equals(WEATHERED_COPPER_LADDER.get()) || ladderBlock.equals(WAXED_WEATHERED_COPPER_LADDER.get())) {
            return 2;
        } else if (ladderBlock.equals(EXPOSED_COPPER_LADDER.get()) || ladderBlock.equals(WAXED_EXPOSED_COPPER_LADDER.get())) {
            return 3;
        } else if (ladderBlock.equals(COPPER_LADDER.get()) || ladderBlock.equals(WAXED_COPPER_LADDER.get())) {
            return 4;
        } else if (ladderBlock.equals(IRON_LADDER.get())) {
            return 5;
        } else if (ladderBlock.equals(GOLD_LADDER.get())) {
            return 6;
        } else if (ladderBlock.equals(DIAMOND_LADDER.get())) {
            return 7;
        } else if (ladderBlock.equals(NETHERITE_LADDER.get())) {
            return 8;
        } else if (ladderBlock instanceof VineBlock) {
            return 9;
        }
        return 0;
    }
    public static final BooleanProperty LIGHTED = BooleanProperty.create("lighted");

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<Block> block) {
        RegistryObject<? extends Block> ret = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return (RegistryObject<T>) ret;
    }

}