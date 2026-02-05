package com.nine.ironladders.client;

import com.nine.ironladders.client.model.ModelType;
import net.minecraft.world.level.block.state.BlockState;

public record LadderRenderData(BlockState state, ModelType type, boolean hideAttachments) {}
