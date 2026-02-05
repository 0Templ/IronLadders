package com.nine.ironladders.client.tab;

import com.nine.ironladders.ILCommon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class TabIconPart {
	
	protected final int SIZE = 16;
	
	public final ResourceLocation location;
	
	public TabIconPart(String key){
		this.location = createLocationIcon(key);
	}
	
	protected static ResourceLocation createLocationIcon(String key){
		return new ResourceLocation(ILCommon.MODID, "textures/gui/icon/tab/icon_" + key + ".png");
	}
	
	public void render(GuiGraphics graphics, int x, int y) {
		graphics.blit(location, x, y, 0, 0, SIZE, SIZE, SIZE, SIZE);
	}
	
}
