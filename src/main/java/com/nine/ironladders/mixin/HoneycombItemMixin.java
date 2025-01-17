package com.nine.ironladders.mixin;

import com.nine.ironladders.common.block.WeatheringLadder;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {

    @Inject(method = "getWaxed", at = @At("HEAD"), cancellable = true)
    private static void ironladders$getWaxed(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
        if (state.getBlock() instanceof WeatheringLadder){
            cir.setReturnValue(Optional.ofNullable(WeatheringLadder.WAXABLES.get().get(state.getBlock())).map((block -> block.withPropertiesOf(state))));
        }
    }

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Ljava/util/Optional;map(Ljava/util/function/Function;)Ljava/util/Optional;"))
    public void ironladders$useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = context.getPlayer();
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
        if (WeatheringLadder.getWaxed(blockState).isEmpty()){
            return;
        }
        if (player instanceof ServerPlayer serverPlayer){
            ResourceLocation advancementId = ResourceLocation.fromNamespaceAndPath("minecraft", "husbandry/wax_on");
            Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(advancementId);
            if (advancement != null) {
                serverPlayer.getAdvancements().award(advancement, "wax_on");
            }
        }
    }
}
