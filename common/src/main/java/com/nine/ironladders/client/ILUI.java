package com.nine.ironladders.client;

import net.minecraft.network.chat.Component;

public class ILUI {
	
	public static final class Text {
		
		public static final Component SHIFT = Component.translatable("input.ironladders.shift");
		public static final Component SHIFT_CLICK = Component.translatable("input.ironladders.shift_click");
		public static final Component SHIFT_LBM = Component.translatable("input.ironladders.shift_lbm");
		public static final Component SHIFT_RBM = Component.translatable("input.ironladders.shift_rbm");
	
		public static final Component CLICK = Component.translatable("input.ironladders.click");
	
		public static final Component RBM = Component.translatable("input.ironladders.rbm");
		
		public static final Component LBM = Component.translatable("input.ironladders.lbm");
		

	}
	
	public static final class Color {
		
		public static final int BLACK = 0xFF000000;
		public static final int GRAY = 0xFFAAAAAA;
		public static final int SOFT_GRAY = 0xFFc8c8c8;
		public static final int LIGHT_LIGHT_GRAY = 0xFFdedede;
		public static final int WHITE = 0xFFFFFFFF;
		public static final int YELLOW = 0XFFffff55;
		public static final int RED = 0XFF5c0700;
		
	}

	// Helpers
	public static Component withColor(Component component, int argb) {
		return component.copy().withStyle(style -> style.withColor(argb & 0xFFFFFF));
	}
	
}
