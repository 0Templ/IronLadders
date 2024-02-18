package com.nine.ironladders.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
public class BaseMetalLadder extends LadderBlock {
    private LadderType type;
    public BaseMetalLadder(Properties p_54345_, LadderType type) {
        super(p_54345_);
        this.type = type;
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(WATERLOGGED, false));
    }

    public LadderType getType(){
        return this.type;
    }
    public int getSpeedMultiplier() {
        return 0;
    }

}
