package com.nine.ironladders.client;

import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MorphModelPredicateProvider implements ClampedItemPropertyFunction {
    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientLevel clientWorld, @Nullable LivingEntity livingEntity, int i) {
        if (stack.getItem() instanceof MorphUpgradeItem){
            return ((MorphUpgradeItem)stack.getItem()).getMorphType(stack);
        }
        return 1F;
    }
    @Deprecated
    public float call(ItemStack itemStack, @Nullable ClientLevel clientWorld, @Nullable LivingEntity livingEntity, int i) {
        return Mth.clamp(this.unclampedCall(itemStack, clientWorld, livingEntity, i), 0.0F, 100.0F);
    }
}
