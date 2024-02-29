package com.nine.ironladders.common;

import com.nine.ironladders.common.item.MorphType;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class BlockStateUtils {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty HAS_SIGNAL = BooleanProperty.create("has_signal");
    public static final BooleanProperty LIGHTED = BooleanProperty.create("lighted");
    public static final BooleanProperty HIDDEN = BooleanProperty.create("hidden");
    public static final EnumProperty<MorphType> MORPH_TYPE = EnumProperty.create("morph", MorphType.class);
    public static BlockState getStateWithSyncedProps(BlockState state, BlockState state_1){
        return state.setValue(WATERLOGGED, state_1.getValue(WATERLOGGED))
                .setValue(LIGHTED, state_1.getValue(LIGHTED))
                .setValue(POWERED, state_1.getValue(POWERED))
                .setValue(HIDDEN, state_1.getValue(HIDDEN))
                .setValue(HAS_SIGNAL, state_1.getValue(HAS_SIGNAL))
                .setValue(MORPH_TYPE, state_1.getValue(MORPH_TYPE));
    }
    public static BlockState getStateWithSyncedPropsNoP(BlockState state, BlockState state_1){
        return state.setValue(WATERLOGGED, state_1.getValue(WATERLOGGED))
                .setValue(LIGHTED, state_1.getValue(LIGHTED))
                .setValue(POWERED, state_1.getValue(POWERED))
                .setValue(HIDDEN, state_1.getValue(HIDDEN))
                .setValue(MORPH_TYPE, state_1.getValue(MORPH_TYPE));
    }

}
