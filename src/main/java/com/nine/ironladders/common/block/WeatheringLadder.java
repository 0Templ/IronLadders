package com.nine.ironladders.common.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public interface WeatheringLadder extends WeatheringCopper {

    Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder().put(BlockRegistry.COPPER_LADDER.get(), BlockRegistry.EXPOSED_COPPER_LADDER.get()).put(BlockRegistry.EXPOSED_COPPER_LADDER.get(), BlockRegistry.WEATHERED_COPPER_LADDER.get()).put(BlockRegistry.WEATHERED_COPPER_LADDER.get(), BlockRegistry.OXIDIZED_COPPER_LADDER.get()).build());

    Supplier<BiMap<Block, Block>> WAXABLES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder().put(BlockRegistry.COPPER_LADDER.get(), BlockRegistry.WAXED_COPPER_LADDER.get()).put(BlockRegistry.EXPOSED_COPPER_LADDER.get(), BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get()).put(BlockRegistry.WEATHERED_COPPER_LADDER.get(), BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get()).put(BlockRegistry.OXIDIZED_COPPER_LADDER.get(), BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get()).build());

    Supplier<BiMap<Block, Block>> WAX_OFF_BY_BLOCK = Suppliers.memoize(() -> WAXABLES.get().inverse());

    Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> NEXT_BY_BLOCK.get().inverse());

    static Optional<Block> getPrevious(Block block) {
        return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(block));
    }

    static Optional<BlockState> getWaxed(BlockState state) {
        return getWaxed(state.getBlock()).map((block) -> block.withPropertiesOf(state));
    }

    static Optional<Block> getWaxed(Block block) {
        return Optional.ofNullable(WAXABLES.get().get(block));
    }

    static Optional<BlockState> getPrevious(BlockState state) {
        return getPrevious(state.getBlock()).map((block) -> block.withPropertiesOf(state));
    }

    static Optional<Block> getNext(Block block) {
        return Optional.ofNullable(NEXT_BY_BLOCK.get().get(block));
    }


    @Override
    default Optional<BlockState> getNext(BlockState state) {
        return getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
    }

    static Block getFirst(Block block) {
        Block block2 = block;
        for (Block block1 = PREVIOUS_BY_BLOCK.get().get(block); block1 != null; block1 = PREVIOUS_BY_BLOCK.get().get(block1)) {
            block2 = block1;
        }
        return block2;
    }

    static BlockState getFirst(BlockState state) {
        return getFirst(state.getBlock()).withPropertiesOf(state);
    }

    default float getChanceModifier() {
        return this.getAge() == WeatherState.UNAFFECTED ? 0.75F : 1.0F;
    }
}