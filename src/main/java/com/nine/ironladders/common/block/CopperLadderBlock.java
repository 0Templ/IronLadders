package com.nine.ironladders.common.block;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.utils.LadderType;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CopperLadderBlock extends BaseMetalLadder implements WeatheringLadder {

    private final WeatherState weatherState;

    @Override
    public int getSpeedMultiplier() {
        return ILConfig.copperLadderSpeedMultiplier.get();
    }

    public CopperLadderBlock(WeatherState weatherState, Properties properties) {
        super(properties, LadderType.COPPER);
        this.weatherState = weatherState;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.onRandomTick(state, level, pos, random);
    }

    @Override
    @Nullable
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        ItemStack itemStack = context.getItemInHand();
        Player player = context.getPlayer();
        if (!itemStack.canPerformAction(toolAction) || player == null)
            return null;
        else if (ToolActions.AXE_SCRAPE == toolAction) {
            return WeatheringLadder.getPrevious(state).orElse(null);
        }
        else if (ToolActions.AXE_WAX_OFF == toolAction) {
            if (WeatheringLadder.WAX_OFF_BY_BLOCK.get().get(state.getBlock()) != null){
                awardPlayer(player, "wax_off");
                return Optional.ofNullable(WeatheringLadder.WAX_OFF_BY_BLOCK.get().get(state.getBlock())).map(block -> block.withPropertiesOf(state)).orElse(null);
            }
        }
        return null;
    }

    private void awardPlayer(Player player, String string){
        if (player instanceof ServerPlayer serverPlayer) {
            ResourceLocation advancementId = new ResourceLocation("minecraft", "husbandry/" + string);
            Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(advancementId);
            if (advancement != null) {
                serverPlayer.getAdvancements().award(advancement, string);
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return WeatheringLadder.getNext(state.getBlock()).isPresent();
    }

    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }
}
