package com.nine.ironladders.common.block;

import com.nine.ironladders.ILConfig;
import com.nine.ironladders.common.utils.LadderType;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public class CopperLadderBlock extends BaseMetalLadder implements WeatheringCopper {

    private final WeatheringCopper.WeatherState weatherState;

    public CopperLadderBlock(Properties properties, LadderType type, WeatherState state) {
        super(properties, type);
        weatherState = state;
    }

    @Override
    public int getSpeedMultiplier() {
        return ILConfig.copperLadderSpeedMultiplier.get();
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        this.changeOverTime(state, level, pos, randomSource);
    }

//    @Override
//    @Nullable
//    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
//        Player player = context.getPlayer();
//        if (ItemAbilities.AXE_WAX_OFF == itemAbility) {
//            if (WeatheringLadder.WAXED.get().get(state.getBlock()) != null){
//                System.out.println("WAX OFF");
//                awardPlayer(player);
//            }
//        }
//        return super.getToolModifiedState(state, context, itemAbility, simulate);
//    }

    private void awardPlayer(Player player){
        if (player instanceof ServerPlayer serverPlayer) {
            ResourceLocation advancementId = ResourceLocation.fromNamespaceAndPath("minecraft", "husbandry/wax_off");
            AdvancementHolder advancement = serverPlayer.server.getAdvancements().get(advancementId);
            if (advancement != null) {
                serverPlayer.getAdvancements().award(advancement, "wax_off");
            }
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public float getChanceModifier() {
        return this.getAge() == WeatheringCopper.WeatherState.UNAFFECTED ? 0.75F : 1.0F;
    }

    @Override
    public WeatheringCopper.WeatherState getAge() {
        return this.weatherState;
    }

}
