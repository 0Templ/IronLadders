package com.nine.ironladders.client;

import com.nine.ironladders.mixin.accessor.client.GuiAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;

public class ClientHelper {
	
	public static void raiseItemName(){
		if (Minecraft.getInstance().gui instanceof GuiAccessor accessor){
			accessor.setToolHighlightTimer(50);
		}
	}
	
	public static boolean shiftPressed(){
		return Screen.hasShiftDown();
	}
	
	public static boolean altPressed(){
		return Screen.hasAltDown();
	}
	
	public static boolean playerInCreative(){
		return Minecraft.getInstance().player != null && Minecraft.getInstance().player.isCreative();
	}
	
	public static Player player(){
		return Minecraft.getInstance().player;
	}
	
}
