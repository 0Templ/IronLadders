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

    Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> {
        return ImmutableBiMap.<Block, Block>builder().put(BlockRegistry.COPPER_LADDER.get(), BlockRegistry.EXPOSED_COPPER_LADDER.get()).put(BlockRegistry.EXPOSED_COPPER_LADDER.get(), BlockRegistry.WEATHERED_COPPER_LADDER.get()).put(BlockRegistry.WEATHERED_COPPER_LADDER.get(), BlockRegistry.OXIDIZED_COPPER_LADDER.get()).build();
    });

    Supplier<BiMap<Block, Block>> WAXED = Suppliers.memoize(() -> {
        return ImmutableBiMap.<Block, Block>builder().put(BlockRegistry.COPPER_LADDER.get(), BlockRegistry.WAXED_COPPER_LADDER.get()).put(BlockRegistry.EXPOSED_COPPER_LADDER.get(), BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get()).put(BlockRegistry.WEATHERED_COPPER_LADDER.get(), BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get()).put(BlockRegistry.OXIDIZED_COPPER_LADDER.get(), BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get()).build();
    });
    Supplier<BiMap<Block, Block>> UNWAXED = Suppliers.memoize(() -> {
        return WAXED.get().inverse();
    });

    Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> {
        return NEXT_BY_BLOCK.get().inverse();
    });

    static Optional<Block> getPrevious(Block p_154891_) {
        return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(p_154891_));
    }
    static Optional<BlockState> getWaxed(BlockState p_154900_) {
        return getWaxed(p_154900_.getBlock()).map((p_154903_) -> {
            return p_154903_.withPropertiesOf(p_154900_);
        });
    }
    static Optional<BlockState> getUnWaxed(BlockState p_154900_) {
        return getUnWaxed(p_154900_.getBlock()).map((p_154903_) -> {
            return p_154903_.withPropertiesOf(p_154900_);
        });
    }
    static Optional<Block> getWaxed(Block p_154891_) {
        return Optional.ofNullable(WAXED.get().get(p_154891_));
    }
    static Optional<Block> getUnWaxed(Block p_154891_) {
        return Optional.ofNullable(UNWAXED.get().get(p_154891_));
    }

    static Optional<BlockState> getPrevious(BlockState p_154900_) {
        return getPrevious(p_154900_.getBlock()).map((p_154903_) -> {
            return p_154903_.withPropertiesOf(p_154900_);
        });
    }
    static Optional<Block> getNext(Block p_154905_) {
        return Optional.ofNullable(NEXT_BY_BLOCK.get().get(p_154905_));
    }


    @Override
    default Optional<BlockState> getNext(BlockState p_154893_) {
        return getNext(p_154893_.getBlock()).map((p_154896_) -> {
            return p_154896_.withPropertiesOf(p_154893_);
        });
    }
    static Block getFirst(Block p_154898_) {
        Block block = p_154898_;
        for(Block block1 = PREVIOUS_BY_BLOCK.get().get(p_154898_); block1 != null; block1 = PREVIOUS_BY_BLOCK.get().get(block1)) {
            block = block1;
        }
        return block;
    }

    static BlockState getFirst(BlockState p_154907_) {
        return getFirst(p_154907_.getBlock()).withPropertiesOf(p_154907_);
    }

    default float getChanceModifier() {
        return this.getAge() == WeatherState.UNAFFECTED ? 0.75F : 1.0F;
    }
}