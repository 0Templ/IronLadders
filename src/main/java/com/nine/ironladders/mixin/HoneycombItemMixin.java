package com.nine.ironladders.mixin;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Ljava/util/Optional;map(Ljava/util/function/Function;)Ljava/util/Optional;"))
    public void ironladders$useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = context.getPlayer();
        if (player instanceof ServerPlayer serverPlayer){
            ResourceLocation advancementId = ResourceLocation.fromNamespaceAndPath("minecraft", "husbandry/wax_on");
            AdvancementHolder advancement = serverPlayer.server.getAdvancements().get(advancementId);
            if (advancement != null) {
                serverPlayer.getAdvancements().award(advancement, "wax_on");
            }
        }
    }
}
