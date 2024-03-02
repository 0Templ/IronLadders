package com.nine.ironladders.common;

import com.nine.ironladders.common.item.MorphType;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;


public class BlockStateUtils {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final BooleanProperty HAS_SIGNAL = BooleanProperty.of("has_signal");
    public static final BooleanProperty LIGHTED = BooleanProperty.of("lighted");
    public static final BooleanProperty HIDDEN = BooleanProperty.of("hidden");
    public static final EnumProperty<MorphType> MORPH_TYPE = EnumProperty.of("morph", MorphType.class);
    public static BlockState getStateWithSyncedProps(BlockState state, BlockState state_1){
        return state.with(WATERLOGGED, state_1.get(WATERLOGGED))
                .with(LIGHTED, state_1.get(LIGHTED))
                .with(POWERED, state_1.get(POWERED))
                .with(HIDDEN, state_1.get(HIDDEN))
                .with(HAS_SIGNAL, state_1.get(HAS_SIGNAL))
                .with(MORPH_TYPE, state_1.get(MORPH_TYPE));
    }
    public static BlockState getStateWithSyncedPropsNoP(BlockState state, BlockState state_1){
        return state.with(WATERLOGGED, state_1.get(WATERLOGGED))
                .with(LIGHTED, state_1.get(LIGHTED))
                .with(POWERED, state_1.get(POWERED))
                .with(HIDDEN, state_1.get(HIDDEN))
                .with(MORPH_TYPE, state_1.get(MORPH_TYPE));
    }

}
