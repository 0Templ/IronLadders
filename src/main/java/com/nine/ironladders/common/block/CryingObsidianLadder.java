package com.nine.ironladders.common.block;

import com.nine.ironladders.common.block.entity.MetalLadderEntity;
import com.nine.ironladders.common.utils.LadderType;
import com.nine.ironladders.init.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CryingObsidianLadder extends BaseMetalLadder implements EntityBlock {

    public CryingObsidianLadder(Properties properties, LadderType type) {
        super(properties.pushReaction(PushReaction.BLOCK).noOcclusion(), type);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MetalLadderEntity metalLadderEntity && metalLadderEntity.getMorphState() != null && state.getBlock() == BlockRegistry.CRYING_OBSIDIAN_LADDER.get()) {
            super.animateTick(state, level, pos, random);
            return;
        }
        if (random.nextInt(11) == 0) {
            spawnTearParticles(state, level, pos, random);
        }
    }

    public static void spawnTearParticles(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction direction = Direction.getRandom(random);
        VoxelShape shape = state.getShape(level, pos);
        BlockState blockState = level.getBlockState(pos);
        if (direction != Direction.UP) {
            if (!state.canOcclude() || !blockState.isFaceSturdy(level, pos, direction.getOpposite()))
                shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                    double x = pos.getX() + random.nextDouble() * (maxX - minX) + minX;
                    double y = pos.getY() + random.nextDouble() * (maxY - minY) + minY;
                    double z = pos.getZ() + random.nextDouble() * (maxZ - minZ) + minZ;
                    level.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, x, y, z, 0.0F, 0.0F, 0.0F);
                });
        }
    }
}
