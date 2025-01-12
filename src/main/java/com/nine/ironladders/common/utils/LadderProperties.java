package com.nine.ironladders.common.utils;

import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class LadderProperties {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty HAS_SIGNAL = BooleanProperty.create("has_signal");
    public static final BooleanProperty LIGHTED = BooleanProperty.create("lighted");
    public static final BooleanProperty HIDDEN = BooleanProperty.create("hidden");
    public static final EnumProperty<LadderPositions> STATE_IN_CHAIN = EnumProperty.create("neighbour", LadderPositions.class);

}
