package com.nine.ironladders.client;

import com.nine.ironladders.common.item.MorphUpgradeItem;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class MorphModelPredicateProvider implements ClampedModelPredicateProvider {
    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i) {
        if (stack.getItem() instanceof MorphUpgradeItem){
            return ((MorphUpgradeItem)stack.getItem()).getMorphType(stack);
        }
        return 1F;
    }
    @Deprecated
    public float call(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i) {
        return MathHelper.clamp(this.unclampedCall(itemStack, clientWorld, livingEntity, i), 0.0F, 10.0F);
    }
}
