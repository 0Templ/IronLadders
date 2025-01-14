package com.nine.ironladders.mixin.client;

import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LadderBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {

    @Inject(method = "doClick", at = @At("HEAD"), cancellable = true)
    private void ironladders$doClick(int slotId, int button, ClickType clickType, Player player, CallbackInfo ci) {
        AbstractContainerMenu containerMenu = (AbstractContainerMenu) (Object) this;
        if (ClientHelper.shiftPressed()) {
            if (slotId < 0) {
                return;
            }
            ItemStack heldItem = player.containerMenu.getCarried();
            Slot slot4 = containerMenu.slots.get(slotId);
            ItemStack itemStack7 = slot4.getItem();
            if (itemStack7.getItem() instanceof BlockItem ladderBlock && heldItem.getItem() instanceof MorphUpgradeItem morphUpgradeItem) {
                if (ladderBlock.getBlock() instanceof LadderBlock ladder) {
                    MorphUpgradeItem.writeMorphType(heldItem, Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(ladder)).toString());
                    if (!player.isSilent()) {
                        player.playSound(SoundEvents.SLIME_SQUISH, 0.5F, 1);
                    }
                    ci.cancel();
                }
            }
        }
    }

}
