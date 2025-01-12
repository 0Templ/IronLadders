package com.nine.ironladders.mixin;

import com.nine.ironladders.common.block.WeatheringLadder;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {

    @Inject(method = "evaluateNewBlockState", at = @At("TAIL"),cancellable = true)
    private void ironladders$useOn(Level level, BlockPos pos, Player player, BlockState state, UseOnContext p_40529_, CallbackInfoReturnable<Optional<BlockState>> cir) {
        AxeItem axeItem = (AxeItem) (Object) this;
        Block block = state.getBlock();
        if (block instanceof WeatheringLadder) {
            Optional<BlockState> optional1 = Optional.ofNullable(WeatheringLadder.UNWAXED.get().get(block))
                    .map(block1 -> block1.withPropertiesOf(state));
            if (optional1.isPresent()){
                if (player instanceof ServerPlayer) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        ResourceLocation advancementId = ResourceLocation.fromNamespaceAndPath("minecraft", "husbandry/wax_off");
                        AdvancementHolder advancement = serverPlayer.server.getAdvancements().get(advancementId);
                        if (advancement != null) {
                            serverPlayer.getAdvancements().award(advancement, "wax_off");
                        }
                    }
                }
                level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3004, pos, 0);
                    cir.setReturnValue(optional1);
                }
            else {
                Optional<BlockState> optional2 = Optional.ofNullable(WeatheringLadder.PREVIOUS_BY_BLOCK.get().get(block))
                        .map(p_150694_ -> p_150694_.withPropertiesOf(state));
                if (optional2.isPresent()) {
                    cir.setReturnValue(optional2);
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.levelEvent(player, 3005, pos, 0);
                }
            }
        }
    }

}