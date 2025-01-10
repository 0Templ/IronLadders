package com.nine.ironladders.common.block;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.utils.LadderType;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

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
    public boolean isRandomlyTicking(BlockState state) {
        return WeatheringLadder.getNext(state.getBlock()).isPresent();
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockpos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() instanceof HoneycombItem) {
            return WeatheringLadder.getWaxed(blockState).map((waxedState) -> {
                        if (player instanceof ServerPlayer serverPlayer) {
                            ResourceLocation advancementId = new ResourceLocation("minecraft", "husbandry/wax_on");
                            Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(advancementId);
                            if (advancement != null) {
                                serverPlayer.getAdvancements().award(advancement, "wax_on");
                            }
                        }
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                        level.levelEvent(player, 3003, blockpos, 0);
                        level.setBlock(blockpos, waxedState, Block.UPDATE_ALL_IMMEDIATE);

                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
            ).orElse(InteractionResult.PASS);
        }
        return InteractionResult.PASS;

    }

    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }
}
