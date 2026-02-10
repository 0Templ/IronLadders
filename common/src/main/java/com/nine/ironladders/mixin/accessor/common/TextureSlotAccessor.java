package com.nine.ironladders.mixin.accessor.common;

import net.minecraft.data.models.model.TextureSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TextureSlot.class)
public interface TextureSlotAccessor {
	
	@Invoker("create")
	static TextureSlot il$create(String id, TextureSlot parent) {
		throw new AssertionError();
	}
	
}
