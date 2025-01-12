package com.nine.ironladders.common.block;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.utils.LadderType;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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

    public CopperLadderBlock(WeatherState weatherState, Properties p_154926_) {
        super(p_154926_, LadderType.COPPER);
        this.weatherState = weatherState;
    }
    @Override
    public void randomTick(BlockState p_222665_, ServerLevel p_222666_, BlockPos p_222667_, RandomSource p_222668_) {
        this.changeOverTime(p_222665_, p_222666_, p_222667_, p_222668_);
    }

    public boolean isRandomlyTicking(BlockState p_154935_) {
        return WeatheringLadder.getNext(p_154935_.getBlock()).isPresent();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemstack, BlockState blockState, Level level, BlockPos blockpos, Player player, InteractionHand p_336117_, BlockHitResult p_332723_){
        if (itemstack.getItem() instanceof HoneycombItem) {
            return WeatheringLadder.getWaxed(blockState).map((waxedState) -> {
                        if (player instanceof ServerPlayer serverPlayer) {
                            ResourceLocation advancementId = ResourceLocation.fromNamespaceAndPath( "minecraft", "husbandry/wax_on");
                            AdvancementHolder advancement = serverPlayer.server.getAdvancements().get(advancementId);
                            if (advancement != null) {
                                serverPlayer.getAdvancements().award(advancement, "wax_on");
                            }
                        }
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                        level.levelEvent(player, 3003, blockpos, 0);
                        level.setBlock(blockpos, waxedState, Block.UPDATE_ALL_IMMEDIATE);

                        return ItemInteractionResult.sidedSuccess(level.isClientSide);
                    }
            ).orElse(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }
}
