package com.nine.ironladders.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.lwjgl.glfw.GLFW;

public class ClientHelper {

    public static boolean checkGoForward(Player player) {
        if (player instanceof LocalPlayer localPlayer) {
            return localPlayer.input.forwardImpulse > 0;
        }
        return false;
    }

    public static boolean shiftPressed() {
        Minecraft mc = Minecraft.getInstance();
        return GLFW.glfwGetKey(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS
                || GLFW.glfwGetKey(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS;
    }

    public static Component componentWithColor(Component component, int color) {
        Style style = Style.EMPTY.withColor(color);
        return component.copy().withStyle(style);
    }

    public static void addParticles(BlockPos blockPos, BlockState state, Level level) {
        addParticles(blockPos, state, level, new BlockParticleOption(ParticleTypes.BLOCK, state));
    }

    public static void addParticles(BlockPos blockPos, BlockState state, Level level, ParticleOptions particleOptions) {
        if (level.isClientSide) {
            VoxelShape shape = state.getShape(level, blockPos);
            RandomSource random = level.random;
            shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                for (int i = 0; i < 7; i++) {
                    double x = blockPos.getX() + random.nextDouble() * (maxX - minX) + minX;
                    double y = blockPos.getY() + random.nextDouble() * (maxY - minY) + minY;
                    double z = blockPos.getZ() + random.nextDouble() * (maxZ - minZ) + minZ;
                    level.addParticle(
                            particleOptions,
                            x, y, z,
                            0.0, 0.0, 0.0
                    );
                }
            });
        }
    }
}
