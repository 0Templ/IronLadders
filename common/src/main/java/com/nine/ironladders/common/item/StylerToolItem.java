package com.nine.ironladders.common.item;

import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.client.ILUI;
import com.nine.ironladders.client.model.ModelType;
import com.nine.ironladders.client.tooltip.TooltipContext;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.common.block.entity.MetalLadderBlockEntity;
import com.nine.ironladders.common.item.base.BlockHitInteractiveItem;
import com.nine.ironladders.common.item.base.ContextTooltipItem;
import com.nine.ironladders.common.item.base.CustomHighlightNameItem;
import com.nine.ironladders.common.item.base.HotBarScrollableItem;
import com.nine.ironladders.common.util.PositionUtils;
import com.nine.ironladders.network.packet.c2s.ModelTypePacket;
import com.nine.ironladders.platform.Platform;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;

import java.util.List;
import java.util.Objects;

public class StylerToolItem extends Item implements
		ContextTooltipItem, HotBarScrollableItem, CustomHighlightNameItem, BlockHitInteractiveItem {
	
	private static final int MAX_DEPTH = 256;
	private static final String MODEL_TYPE_KEY = "il_styler_model_type";
	
	public StylerToolItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public MutableComponent getCustomHighlightName(ItemStack stack, MutableComponent original) {
		var current = getType(stack);
		if (current != null){
			return Component.translatable("item.ironladders.ladder_styler_tool.custom_highlight", original, getTypeAsComponent(current));
		}
		return original;
	}
	
	@Override
	public void appendContextTooltip(ItemStack stack, Level level, List<Component> components, TooltipFlag flag, TooltipContext type) {
		if (type == TooltipContext.REFERENCE){
			components.add(Component.translatable("item.ironladders.ladder_styler_tool.desc").withStyle(ChatFormatting.GRAY));
//			components.add(Component.translatable("item.ironladders.ladder_styler_tool.cost_info").withStyle(ChatFormatting.GRAY));
		}
		else {
			var current = getType(stack);
			if (current != null){
				Component typeComponent = ILUI.withColor(getTypeAsComponent(stack), ILUI.Color.SOFT_GRAY);
				components.add(Component.translatable("item.ironladders.ladder_styler_tool.current_type", typeComponent).withStyle(ChatFormatting.GRAY));
			}
			int color = ClientHelper.shiftPressed() ? ILUI.Color.GRAY : ILUI.Color.SOFT_GRAY;
			components.add(Component.translatable("item.ironladders.ladder_styler_tool.switch",
							ILUI.withColor(Component.translatable("item.ironladders.ladder_styler_tool.switch.input_0"), color),
							ILUI.withColor(Component.translatable("item.ironladders.ladder_styler_tool.switch.input_1"), color))
					.withStyle(ChatFormatting.GRAY));
			components.add(Component.translatable("item.ironladders.ladder_styler_tool.reset",
							ILUI.withColor(ILUI.Text.LBM, ILUI.Color.SOFT_GRAY))
					.withStyle(ChatFormatting.GRAY));
			components.add(Component.translatable("item.ironladders.ladder_styler_tool.multiple_use",
							ILUI.withColor(ILUI.Text.SHIFT, color))
					.withStyle(ChatFormatting.GRAY));
			
		}
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (player.isShiftKeyDown()) {
			ItemStack stack = player.getItemInHand(hand);
			ModelType next = nextType(stack, true);
			writeType(stack, next);
			if (!level.isClientSide()){
				ClientHelper.raiseItemName();
			}			return InteractionResultHolder.success(player.getItemInHand(hand));
		}
		return InteractionResultHolder.pass(player.getItemInHand(hand));
	}
	
	@Override
	public boolean onHotBarMouseScroll(Player player, ItemStack stack, double xOffset, double yOffset) {
		if (player.isShiftKeyDown() && !player.getCooldowns().isOnCooldown(this)){
			ModelType next = nextType(stack, yOffset > 0);
			writeType(stack, next);
			Platform.NETWORK.sendToServer(new ModelTypePacket(next));
			ClientHelper.raiseItemName();
			return true;
		}
		return false;
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		var pos = context.getClickedPos();
		var level = context.getLevel();
		var state = level.getBlockState(pos);
		var block = state.getBlock();
		var stack = context.getItemInHand();
		var player = context.getPlayer();
		if (!state.isAir()){
			if (block instanceof MetalLadderBlock) {
				if (level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be) {
					if (player != null && player.isShiftKeyDown()) {
						if (!level.isClientSide()) {
							if (!player.getCooldowns().isOnCooldown(this)) {
								applyMultipleStyle(level, pos, getType(stack));
								player.getCooldowns().addCooldown(this, 10);
								level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
							}
						}
						return InteractionResult.SUCCESS;
					}
					if (!level.isClientSide()) {
						ModelType type = getType(stack);
						if (!Objects.equals(be.modelType(), type)) {
							be.setModelType(type);
						}
						level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
					}
				}
				return InteractionResult.SUCCESS;
			}
			return InteractionResult.FAIL;
		}
		
		return InteractionResult.PASS;
	}

	public static void applyMultipleStyle(Level level, BlockPos startPos, ModelType type) {
		if (level.isClientSide) return;
		if (!(level.getBlockEntity(startPos) instanceof MetalLadderBlockEntity startBe)) return;
		var startState = startBe.getBlockState();
		if (!startState.hasProperty(LadderBlock.FACING)) return;
		var startDir = startState.getValue(LadderBlock.FACING);
		PositionUtils.walkAndApply(
				startPos,
				MAX_DEPTH,
				pos -> getStyleTarget(level, pos, startDir),
				(pos, foundBe) -> {
					if (!Objects.equals(foundBe.modelType(), type)) {
						foundBe.setModelType(type);
					}
				},
				Direction.DOWN, Direction.UP
		);
	}

	private static MetalLadderBlockEntity getStyleTarget(Level level, BlockPos pos, Direction startDir) {
		var state = level.getBlockState(pos);
		if (!(state.getBlock() instanceof MetalLadderBlock)) return null;
		if (!state.hasProperty(LadderBlock.FACING) || state.getValue(LadderBlock.FACING) != startDir) return null;
		if (level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be) {
			return be;
		}
		return null;
	}
	
	private ModelType nextType(ItemStack stack, boolean forward){
		ModelType current = getType(stack);
		ModelType next;
		if (current != null){
			next = current.cycle(forward);
		}
		else {
			next = ModelType.VANILLA;
		}
		return next;
	}
	
	public static Component getTypeAsComponent(ItemStack stack){
		return getTypeAsComponent(getType(stack));
	}
	
	public static Component getTypeAsComponent(ModelType type){
		if (type == null) return Component.empty();
		return Component.translatable("model.ironladders." + type.key);
	}
	
	public static ModelType getType(ItemStack stack){
		if (stack.getTag() == null || !stack.getTag().contains(MODEL_TYPE_KEY)){
			return null;
		}
		return ModelType.byKey(stack.getTag().getString(MODEL_TYPE_KEY));
	}
	
	public static void writeType(ItemStack stack, ModelType type){
		stack.getOrCreateTag().putString(MODEL_TYPE_KEY, type.key);
	}

	@Override
	public boolean onBlockHit(Player player, Level level, ItemStack stack, Block block, BlockPos pos) {
		if (block instanceof MetalLadderBlock) {
			if (!level.isClientSide()) {
				if (player.isShiftKeyDown()) {
					if (!player.getCooldowns().isOnCooldown(this)) {
						applyMultipleStyle(level, pos, null);
						player.getCooldowns().addCooldown(this, 10);
						level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
					}
				} else if (level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be) {
					if (be.modelType() != null) {
						be.setModelType(null);
					}
					level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
				}
			}
			return true;
		}
		return false;
	}

	
}
