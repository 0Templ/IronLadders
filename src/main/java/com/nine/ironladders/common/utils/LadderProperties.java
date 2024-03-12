package com.nine.ironladders.common.utils;

import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;

public class LadderProperties {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final BooleanProperty HAS_SIGNAL = BooleanProperty.of("has_signal");
    public static final BooleanProperty LIGHTED = BooleanProperty.of("lighted");
    public static final BooleanProperty HIDDEN = BooleanProperty.of("hidden");
    public static final EnumProperty<MorphType> MORPH_TYPE = EnumProperty.of("morph", MorphType.class);
    public static final EnumProperty<LadderPositions> STATE_IN_CHAIN = EnumProperty.of("neighbour", LadderPositions.class);

}
