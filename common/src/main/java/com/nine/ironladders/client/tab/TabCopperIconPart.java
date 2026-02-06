package com.nine.ironladders.client.tab;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WeatheringCopper;

public class TabCopperIconPart extends TabIconPart{
	
	private static final ResourceLocation COPPER = createLocationIcon("copper");
	private static final ResourceLocation EXPOSED_COPPER = createLocationIcon("exposed_copper");
	private static final ResourceLocation WEATHERED_COPPER = createLocationIcon("weathered_copper");
	private static final ResourceLocation OXIDIZED_COPPER = createLocationIcon("oxidized_copper");
	
	private static int ticksPerAge = 2100;
	
	private int tickCount = 0;
	private WeatheringCopper.WeatherState weatherState = WeatheringCopper.WeatherState.UNAFFECTED;
	
	public TabCopperIconPart() {
		super("");
	}
	
	public void render(GuiGraphics graphics, int x, int y) {
		if (weatherState != WeatheringCopper.WeatherState.OXIDIZED && ++tickCount >= ticksPerAge) {
			weatherState = switch (weatherState) {
				case UNAFFECTED -> WeatheringCopper.WeatherState.EXPOSED;
				case EXPOSED -> WeatheringCopper.WeatherState.WEATHERED;
				case WEATHERED -> WeatheringCopper.WeatherState.OXIDIZED;
				default -> null;
			};
			ticksPerAge *= weatherState == WeatheringCopper.WeatherState.OXIDIZED ? 0 : 2;
		}
		ResourceLocation texture = switch (weatherState) {
			case UNAFFECTED -> COPPER;
			case EXPOSED -> EXPOSED_COPPER;
			case WEATHERED -> WEATHERED_COPPER;
			case OXIDIZED -> OXIDIZED_COPPER;
		};
		graphics.blit(texture, x, y, 0, 0, SIZE, SIZE, SIZE, SIZE);
	}
	
	
}
