package com.nine.ironladders.common.item;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.common.block.BaseMetalLadder;
import com.nine.ironladders.common.block.entity.MetalLadderEntity;
import com.nine.ironladders.common.component.CustomComponents;
import com.nine.ironladders.common.utils.LadderProperties;
import com.nine.ironladders.common.utils.LadderType;
import com.nine.ironladders.network.MorphPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Objects;

public class MorphUpgradeItem extends Item {


    public MorphUpgradeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || (Objects.equals(getMorphType(stack), "") && !player.isShiftKeyDown()) || !ILConfig.enableMorphLaddersUpgrade.get()) {
            return InteractionResult.FAIL;
        }
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if ((block instanceof LadderBlock)) {
            if (player.isShiftKeyDown()) {
                level.playSound(null, blockPos, SoundEvents.SLIME_SQUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
                setMorphType(stack, level, blockPos, blockState);
            } else if (block instanceof BaseMetalLadder) {
                if (morphSingleBlock(level, blockPos, stack)) {
                    level.playSound(null, blockPos, SoundEvents.LADDER_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
        return InteractionResult.FAIL;
    }

    private static void setMorphType(ItemStack stack, Level level, BlockPos pos, BlockState state) {
        String valueToSet = Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(state.getBlock())).toString();
        Minecraft mc = Minecraft.getInstance();
        if (level.getBlockEntity(pos) instanceof MetalLadderEntity ladderEntity && ladderEntity.getMorphState() != null) {
            if (level.isClientSide) {
                boolean ctrlPressed = (GLFW.glfwGetKey(mc.getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS
                        || GLFW.glfwGetKey(mc.getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS);
                if (ctrlPressed) {
                    valueToSet = Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(ladderEntity.getMorphState().getBlock())).toString();
                }
                PacketDistributor.sendToServer(new MorphPacket(valueToSet));
                writeMorphType(stack, valueToSet);
            }
        } else {
            writeMorphType(stack, valueToSet);
        }
    }

    public boolean morphSingleBlock(Level level, BlockPos blockPos, ItemStack stack) {
        if (level.getBlockEntity(blockPos) instanceof MetalLadderEntity metalLadderEntity) {
            Block block1 = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(getMorphType(stack)));
            Block block2 = level.getBlockState(blockPos).getBlock();
            if (block1 != null) {
                if (block1 == block2) {
                    if (metalLadderEntity.getMorphState() != null) {
                        metalLadderEntity.setMorphState(null);
                        ClientHelper.spawnMorphParticles(blockPos, block2.defaultBlockState(), level);
                    }
                    return false;
                }
                BlockState state = block1.withPropertiesOf(level.getBlockState(blockPos));
                metalLadderEntity.setMorphState((metalLadderEntity.getMorphState() != null && metalLadderEntity.getMorphState().is(state.getBlock())) ? null : state);
                ClientHelper.spawnMorphParticles(blockPos, metalLadderEntity.getMorphState() != null && metalLadderEntity.getMorphState().is(state.getBlock()) ? state : level.getBlockState(blockPos), level);
                return true;
            }
        }
        return false;
    }

    public void morphMultipleLadders(MetalLadderEntity entity, Level level, BlockPos blockPos, BlockState state, ItemStack stack) {
        int height = 1;
        boolean canGoUp = true;
        boolean canGoDown = true;
        Block startBlock = state.getBlock();
        LadderType startType = LadderType.DEFAULT;
        LadderType upperType = startType;
        LadderType bottomType = startType;
        Direction startFacingDirection = state.getValue(LadderProperties.FACING);
        Block morphBlock = entity.getMorphState() == null ? null : entity.getMorphState().getBlock();
        while (height < level.getMaxBuildHeight()) {
            if (startBlock instanceof BaseMetalLadder metalLadder) {
                startType = metalLadder.getType();
            }
            BlockState stateAbove = level.getBlockState(blockPos.above(height));
            BlockPos abovePos = blockPos.above(height);
            Block blockAbove = stateAbove.getBlock();

            BlockPos belowPos = blockPos.below(height);
            BlockState stateBelow = level.getBlockState(blockPos.below(height));
            Block blockBelow = stateBelow.getBlock();
            if (blockAbove instanceof BaseMetalLadder metalLadder) {
                upperType = metalLadder.getType();
            }
            if (blockBelow instanceof BaseMetalLadder metalLadder) {
                bottomType = metalLadder.getType();
            }
            if (canGoUp) {
                if (blockAbove instanceof BaseMetalLadder) {
                    Direction currentUpFacingDirection = stateAbove.getValue(LadderProperties.FACING);
                    canGoUp = startFacingDirection == currentUpFacingDirection && morphBlock != stateAbove.getBlock();
                } else {
                    canGoUp = false;
                }
            }
            if (canGoDown) {
                if (blockBelow instanceof BaseMetalLadder) {
                    Direction currentDownFacingDirection = stateBelow.getValue(LadderProperties.FACING);
                    canGoDown = startFacingDirection == currentDownFacingDirection && morphBlock != stateBelow.getBlock();
                } else {
                    canGoDown = false;
                }
            }
            if ((canGoUp || canGoDown)) {
                if (canGoUp) {
                    if (upperType == startType) {
                        morphSingleBlock(level, abovePos, stack);
                    }
                }
                if (canGoDown) {
                    if (bottomType == startType) {
                        morphSingleBlock(level, belowPos, stack);
                    }
                }
                height++;
            } else {
                break;
            }
        }
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    public static void writeMorphType(ItemStack stack, String type) {
        stack.set(CustomComponents.MORPH_TYPE.get(), type);
    }

    public static String getMorphType(ItemStack stack) {
        if (stack.get(CustomComponents.MORPH_TYPE.get()) != null){
            return stack.get(CustomComponents.MORPH_TYPE.get());
        }
        return "";
    }

    public static Component getMorphTypeMod(ItemStack stack) {
        String namespace = getMorphType(stack).split(":")[0];
        return Component.literal(ModList.get().getModContainerById(namespace).map(modContainer -> modContainer.getModInfo().getDisplayName()).orElse(namespace));
    }

    public static Component getMorphContent(ItemStack stack) {
        return Component.literal(Component.translatable("block." + getMorphType(stack).replace(':', '.')).getString());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (!ILConfig.enableMorphLaddersUpgrade.get()) {
            tooltip.add(Component.translatable("ironladders.tooltip.disabled").withStyle(ChatFormatting.RED));
            return;
        }
        boolean shiftDown = Screen.hasShiftDown();
        Component modName = ClientHelper.componentWithColor(getMorphTypeMod(stack).copy().withStyle(ChatFormatting.GRAY), 0xcbcbcb);
        Component morphContent = getMorphContent(stack);
        if (!Objects.equals(getMorphType(stack), "")) {
            if (shiftDown) {
                tooltip.add(Component.translatable("ironladders.tooltip.morph_upgrade.type_1", modName, morphContent).withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.add(Component.translatable("ironladders.tooltip.morph_upgrade.type_0", morphContent).withStyle(ChatFormatting.GRAY));
            }
        }
        Component component1 = ClientHelper.componentWithColor(Component.translatable("ironladders.tooltip.shift"), shiftDown ? 0xcbcbcb : 0x808080);
        Component component2 = Component.translatable("ironladders.tooltip.hold_for", component1).withStyle(ChatFormatting.GRAY);
        tooltip.add(component2);
        Component component3 = ClientHelper.componentWithColor(Component.translatable("ironladders.tooltip.morph_upgrade.info_2"), 0xcbcbcb);
        if (shiftDown) {
            tooltip.add(Component.translatable("ironladders.tooltip.hiding_upgrade.info_0").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("ironladders.tooltip.morph_upgrade.info_1", component3).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("ironladders.tooltip.morph_upgrade.info_3").withStyle(ChatFormatting.GRAY));
            if (ModList.get().getModContainerById("modernfix").isPresent()) {
                Component mod = ClientHelper.componentWithColor(Component.literal(ModList.get().getModContainerById("modernfix").get().getModInfo().getDisplayName()), 0xf33838);
                tooltip.add(Component.translatable("ironladders.tooltip.warning.modernfix", mod).withStyle(ChatFormatting.RED));
            }
        }
    }
}
