package com.nine.ironladders.client;

import com.nine.ironladders.IronLadders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ClientHelper {

    public static Map<String, String> morphModels;

    public static void initializeMorphModels(ResourceManager resourceManager) {
        if (morphModels == null || morphModels.isEmpty()) {
            morphModels = new HashMap<>();
            String modelsPath = "models/item/morph_ladders";
            Predicate<ResourceLocation> filter = resourceLocation -> resourceLocation.getPath().endsWith(".json");
            for (var v : resourceManager.listResources(modelsPath, filter).keySet()) {
                String location = v.toString();
                try {
                    String key = (location.replace(".json", "").split("/")[3]).replace("morph_", "");
                    String value = location.split(":")[0] + ":morph_ladders/" + location.split("/")[3].replace(".json", "");
                    morphModels.put(key, value);
                } catch (Exception ignored) {
                    IronLadders.LOGGER.warn("Failed to load morph model: " + location);
                }
            }
        }
    }

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

    public static void spawnMorphParticles(BlockPos blockPos, BlockState state, Level level) {
        if (level.isClientSide) {
            VoxelShape shape = state.getShape(level, blockPos);
            RandomSource random = level.random;
            shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                for (int i = 0; i < 7; i++) {
                    double x = blockPos.getX() + random.nextDouble() * (maxX - minX) + minX;
                    double y = blockPos.getY() + random.nextDouble() * (maxY - minY) + minY;
                    double z = blockPos.getZ() + random.nextDouble() * (maxZ - minZ) + minZ;
                    level.addParticle(
                            new BlockParticleOption(ParticleTypes.BLOCK, state),
                            x, y, z,
                            0.0, 0.0, 0.0
                    );
                }
            });
        }
    }

    public static void spawnUpgradeParticles(BlockPos blockPos, BlockState state, Level level) {
        if (level.isClientSide) {
            VoxelShape shape = state.getShape(level, blockPos);
            RandomSource random = level.random;
            shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                for (int i = 0; i < 7; i++) {
                    double x = blockPos.getX() + random.nextDouble() * (maxX - minX) + minX;
                    double y = blockPos.getY() + random.nextDouble() * (maxY - minY) + minY;
                    double z = blockPos.getZ() + random.nextDouble() * (maxZ - minZ) + minZ;
                    level.addParticle(
                            ParticleTypes.HAPPY_VILLAGER,
                            x, y, z,
                            0.0, 0.0, 0.0
                    );
                }
            });
        }
    }
}
