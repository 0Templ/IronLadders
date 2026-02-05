package com.nine.ironladders.client.tab;

import net.minecraft.client.gui.GuiGraphics;

public record TabIcon(TabIconPart iconOne, TabIconPart iconTwo, TabIconPart iconThree) {
	
	public void render(GuiGraphics graphics, int x, int y) {
		x -= 1;
		y -= 1;
		iconOne.render(graphics, x, y + 4);
		iconTwo.render(graphics, x + 4, y);
		iconThree.render(graphics, x + 9, y + 4);
	}
	
}
