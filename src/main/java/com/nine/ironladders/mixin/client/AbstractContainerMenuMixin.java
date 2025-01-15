package com.nine.ironladders.mixin.client;

import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraftforge.registries.ForgeRegistries;
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
        ItemStack heldItem = player.containerMenu.getCarried();
        if (ClientHelper.shiftPressed()) {
            if (slotId < 0) {
                return;
            }
            Slot slot4 = containerMenu.slots.get(slotId);
            ItemStack itemStack7 = slot4.getItem();
            if (heldItem.getItem() instanceof MorphUpgradeItem &&  itemStack7.getItem() instanceof BlockItem ladderBlock) {
                if (ladderBlock.getBlock() instanceof LadderBlock ladder) {
                    MorphUpgradeItem.writeMorphType(heldItem, Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(ladder)).toString());
                    if (!player.isSilent()) {
                        player.playSound(SoundEvents.SLIME_SQUISH, 0.5F, 1);
                    }
                    ci.cancel();
                }
            }
        }
    }

}
