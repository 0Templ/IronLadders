package com.nine.ironladders.common.item.base;

import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.client.ILUI;
import com.nine.ironladders.client.tooltip.TooltipSource;
import com.nine.ironladders.init.ILComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class StoredChargeToolItem extends Item implements InventoryInteractiveItem, ContextTooltipItem {

	protected StoredChargeToolItem(Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public boolean onClickWith(ItemStack self, ItemStack other, int button, boolean shift){
		return handleClick(self, other, button, shift);
	}

	@Override
	public boolean onClickedBy(ItemStack self, ItemStack other, int button, boolean shift){
		return handleClick(self, other, button, shift);
	}
	
	@Override
	public void appendContextTooltip(
			ItemStack stack,
			Level level,
			List<Component> components,
			TooltipFlag flag,
			TooltipSource type
	) {
		if (type == TooltipSource.REFERENCE){
			components.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
			Component cost = ILUI.withColor(Component.translatable(chargeItem().getDescriptionId()), ILUI.Color.SOFT_GRAY);
			components.add(Component.translatable("item.ironladders.stored_charge_tool.cost_info",
					cost).withStyle(ChatFormatting.GRAY));
		}
		else {
			int current = getCharges(stack);
			boolean shouldHighlight = !ClientHelper.playerInCreative() && current == 0;
			components.add(Component.translatable("item.ironladders.stored_charge_tool.balance",
					Component.translatable(chargeItem().getDescriptionId()),
					ILUI.withColor(Component.literal(String.valueOf(current)), shouldHighlight ? ILUI.Color.YELLOW : ILUI.Color.SOFT_GRAY),
					maxSavedCharges()).withStyle(ChatFormatting.GRAY));
			if (current == 0 && !ClientHelper.playerInCreative()){
				components.add(Component.translatable("item.ironladders.stored_charge_tool.empty").withStyle(ChatFormatting.GRAY));
			}
			else {
				components.add(Component.translatable(
								"item.ironladders.stored_charge_tool.remove",
								ILUI.withColor(ILUI.Text.SHIFT_RBM, ClientHelper.shiftPressed() ? ILUI.Color.GRAY : ILUI.Color.SOFT_GRAY))
						.withStyle(ChatFormatting.GRAY));
			}
		}
	}

	@Override
	public Component getHoverTooltip(ItemStack carried, ItemStack hovered, boolean shift) {
		boolean toolOnCarried = carried.is(this);
		boolean toolOnHovered = hovered.is(this);
		if (!toolOnCarried && !toolOnHovered) {
			return null;
		}
		var stack = toolOnCarried ? carried : hovered;
		var costStack = toolOnCarried ? hovered : carried;
		if (!costStack.is(chargeItem())) {
			return null;
		}
		int current = getCharges(stack);
		if (current >= maxSavedCharges()){
			return Component.translatable("item.ironladders.stored_charge_tool.full").withStyle(ChatFormatting.GRAY);
		}
		return Component.translatable("item.ironladders.stored_charge_tool.add",
				ILUI.withColor(ILUI.Text.CLICK, ILUI.Color.SOFT_GRAY)).withStyle(ChatFormatting.GRAY);
	}

	public boolean correctChargeItem(Item item){
		return item.equals(chargeItem());
	}

	public abstract Item chargeItem();

	public int maxSavedCharges(){
		return 99;
	}

	public static int getCharges(ItemStack stack){
		return stack.getOrDefault(ILComponents.SAVED_CHARGES_COUNT.get(), 0);
	}

	public final int changeCharges(ItemStack stack, int value){
		int current = getCharges(stack);
		int toSet = Mth.clamp(current + value,0, maxSavedCharges());
		if (toSet != current) {
			stack.set(ILComponents.SAVED_CHARGES_COUNT.get(), toSet);
		}
		return toSet - current;
	}

	private boolean handleClick(ItemStack stack, ItemStack otherStack, int button, boolean shift){
		var match = ToolChargeMatch.create(stack, otherStack);
		if (match != null) {
			int current = match.charge().getCount();
			if (current == 0) return false;
			if (button == 1) current = 1;
			int diff = ((StoredChargeToolItem) (match.tool.getItem())).changeCharges(match.tool, current);
			if (diff > 0){
				match.charge().shrink(diff);
			}
			return diff != 0;
		}
		return false;
	}

	public record ToolChargeMatch(ItemStack tool, ItemStack charge) {

		public static ToolChargeMatch create(ItemStack a, ItemStack b) {
			if (a.getItem() instanceof StoredChargeToolItem tool && tool.correctChargeItem(b.getItem())) {
				return new ToolChargeMatch(a, b);
			}
			if (b.getItem() instanceof StoredChargeToolItem tool && tool.correctChargeItem(a.getItem())) {
				return new ToolChargeMatch(b, a);
			}
			return null;
		}
		
	}
	
}
