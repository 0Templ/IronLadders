package com.nine.ironladders.common.item;

import com.nine.ironladders.client.ClientHelper;
import com.nine.ironladders.client.ILUI;
import com.nine.ironladders.client.tooltip.TooltipContext;
import com.nine.ironladders.common.block.entity.MetalLadderBlockEntity;
import com.nine.ironladders.common.item.base.BlockHitInteractiveItem;
import com.nine.ironladders.common.item.base.ContextTooltipItem;
import com.nine.ironladders.common.item.base.InventoryInteractiveItem;
import com.nine.ironladders.common.util.MorphType;
import com.nine.ironladders.common.util.PositionUtils;
import com.nine.ironladders.platform.Platform;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class MorphToolItem extends Item implements
		InventoryInteractiveItem, ContextTooltipItem, BlockHitInteractiveItem {
	
	private static final int MAX_MORPH = 256;
	
	private static final String MORPH_TYPE_KEY = "il_morph_type";
	private static final String MORPH_TYPE_ID = "il_morph_type_id";
	
	public MorphToolItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		var pos = context.getClickedPos();
		var level = context.getLevel();
		var state = level.getBlockState(pos);
		var block = state.getBlock();
		var player = context.getPlayer();
		var stack = context.getItemInHand();
		if (player == null) return InteractionResult.PASS;
		if (validMorphSource(block)) {
			if (player.isShiftKeyDown()) {
				if (!level.isClientSide) {
					if (!player.getCooldowns().isOnCooldown(this)) {
						applyMultipleMorph(stack, level, pos);
						player.getCooldowns().addCooldown(this, 10);
					}
				}
				return InteractionResult.SUCCESS;
			}
			if (!level.isClientSide
					&& level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be
					&& tryApplyMorph(stack, level, pos, be, 10)) {
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
	
	public static boolean validMorphSource(Block block){
		return block instanceof LadderBlock;
	}
	
	public static boolean shouldMorph(MetalLadderBlockEntity be, BlockState morphState) {
		if (morphState == null) return false;
		var currentMorphState = be.morphState();
		var state = be.getBlockState();
		return !(currentMorphState == null && state.getBlock() == morphState.getBlock());
	}
	
	public static BlockState morphStateToApply(MetalLadderBlockEntity be, BlockState morphState) {
		var current = be.morphState();
		if (current != null && current.getBlock() == morphState.getBlock()) {
			return be.getBlockState();
		}
		return morphState;
	}
	
	
	public static void applyMorph(MetalLadderBlockEntity be, BlockState state) {
		if (state != null && state.getBlock() == be.getBlockState().getBlock()) {
			state = null;
		}
		be.setMorphState(state);
	}
	
	public static void applyMultipleMorph(ItemStack stack, Level level, BlockPos startPos) {
                if (level.isClientSide) return;
                if (!(level.getBlockEntity(startPos) instanceof MetalLadderBlockEntity startBe)) return;
		
		var startState = startBe.getBlockState();
		if (!startState.hasProperty(LadderBlock.FACING)) return;
		
		var startDir = startState.getValue(LadderBlock.FACING);
		var morph = morphState(stack, startState);
		if (morph == null) return;
		
		var morphToApply = morphStateToApply(startBe, morph);
		
		boolean isServer = level instanceof ServerLevel;
		boolean[] applied = new boolean[] { false };
		PositionUtils.walkAndApply(
				startPos,
				MAX_MORPH,
				pos -> getMorphTarget(level, pos, startState, morph, startDir),
				(pos, foundBe) -> {
					applyMorph(foundBe, morphToApply);
					applied[0] = true;
					if (isServer) {
						addMorphParticles((ServerLevel) level, visualState(startBe), pos, 7);
					}
				},
				Direction.DOWN, Direction.UP
		);
		if (applied[0]) {
			playMorphSound(level, startPos, morphToApply);
		}
	}
	
	public static MetalLadderBlockEntity getMorphTarget(Level level, BlockPos pos, BlockState startState, BlockState morphState, Direction startDir) {
		var state = level.getBlockState(pos);
//		if (state.getBlock() != startState.getBlock()) return null;
		if (!state.hasProperty(LadderBlock.FACING) || state.getValue(LadderBlock.FACING) != startDir) return null;
		if (level.getBlockEntity(pos) instanceof MetalLadderBlockEntity be) {
			if (shouldMorph(be, morphState)) return be;
		}
		return null;
	}
	
	private static boolean canWrite(ItemStack stack, Block block){
		var tag = stack.getOrCreateTag();
		var type = BuiltInRegistries.BLOCK.getKey(block);
		String typeKey = type.toString();
		return !(tag.contains(MORPH_TYPE_KEY) && tag.getString(MORPH_TYPE_KEY).equals(typeKey));
	}

	public static void writeMorphType(ItemStack stack, Block block) {
		var tag = stack.getOrCreateTag();
		var type = BuiltInRegistries.BLOCK.getKey(block);
		String typeKey = type.toString();
		tag.putString(MORPH_TYPE_KEY, typeKey);
		var morphType = MorphType.KEY_ID_MAP.get(typeKey);
		int id = morphType == null ? MorphType.UNKNOWN.id : morphType.id;
		tag.putInt(MORPH_TYPE_ID, id);
	}

	public static String morphStateString(ItemStack stack) {
		var tag = stack.getTag();
		return tag == null ? "" : tag.getString(MORPH_TYPE_KEY);
	}
	
	public static int morphStateId(ItemStack stack) {
		var tag = stack.getTag();
		return tag == null ? 0 : tag.getInt(MORPH_TYPE_ID);
	}
	
	public static BlockState morphState(ItemStack stack, BlockState original) {
		var current = morphStateString(stack);
		if (current.isEmpty()) return null;
		ResourceLocation id = new ResourceLocation(current);
		var optBlock = BuiltInRegistries.BLOCK.getOptional(id);
		if (optBlock.isEmpty()) return null;

		var block = optBlock.get();
		if (original == null) return block.defaultBlockState();
		if (block == original.getBlock()) return original;
		return block.withPropertiesOf(original);
	}
	
	private static boolean tryApplyMorph(ItemStack stack, Level level, BlockPos pos, MetalLadderBlockEntity be, int particleAmount) {
		BlockState original = be.getBlockState();
		BlockState morph = morphState(stack, original);
		if (!shouldMorph(be, morph)) return false;

		BlockState morphToApply = morphStateToApply(be, morph);
		applyMorph(be, morphToApply);
		playMorphEffects(level, pos, visualState(be), particleAmount);
		return true;
	}

	private static void playMorphEffects(Level level, BlockPos pos, BlockState state, int particleAmount) {
		if (level instanceof ServerLevel serverLevel) {
			addMorphParticles(serverLevel, state, pos, particleAmount);
		}
		playMorphSound(level, pos, state);
	}

	public static void playMorphSound(Level level, BlockPos pos, BlockState morphState) {
		if (morphState == null) return;
		Block block = morphState.getBlock();
		SoundEvent event = block.getSoundType(morphState).getPlaceSound();
		level.playSound(null, pos, event, SoundSource.PLAYERS, 1.0F, 1.0F);
	}
	
	private static void addMorphParticles(ServerLevel level, BlockState state, BlockPos pos, int amount) {
		VoxelShape shape = state.getShape(level, pos);
		RandomSource random = level.getRandom();
		shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
			for (int i = 0; i < amount; i++) {
				double x = pos.getX() + random.nextDouble() * (maxX - minX) + minX;
				double y = pos.getY() + random.nextDouble() * (maxY - minY) + minY;
				double z = pos.getZ() + random.nextDouble() * (maxZ - minZ) + minZ;
				ParticleOptions opt = new BlockParticleOption(ParticleTypes.BLOCK, state);
				level.sendParticles(opt, x, y, z, 1, 0, 0, 0, 0);
			}
		});
	}
	
	public static BlockState visualState(MetalLadderBlockEntity be) {
		return be.morphState() == null ? be.getBlockState() : be.morphState();
	}
	
	
	@Override
	public boolean onBlockHit(Player player, Level level, ItemStack stack, Block block, BlockPos pos) {
		if (validMorphSource(block) && !player.isShiftKeyDown()) {
			if (!level.isClientSide()) {
				if (canWrite(stack, block)) {
					writeMorphType(stack, block);
					level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void appendContextTooltip(
			ItemStack stack,
			Level level,
			List<Component> components,
			TooltipFlag flag,
			TooltipContext type
	) {
		if (type == TooltipContext.REFERENCE){
			components.add(Component.translatable("item.ironladders.ladder_morph_tool.desc").withStyle(ChatFormatting.GRAY));
		} else {
			var current = morphStateString(stack);
			boolean shiftPressed = ClientHelper.shiftPressed();
			if (!current.isEmpty()) {
				var parts = current.split(":");
				if (parts.length == 2){
					var modName = Platform.CORE.getModName(parts[0]);
					var ladderId = "block." + current.replace(":", ".");
					components.add(Component.translatable("item.ironladders.ladder_morph_tool.current_style",
							ILUI.withColor(Component.literal(modName), ILUI.Color.SOFT_GRAY) ,
							ILUI.withColor(Component.translatable(ladderId), ILUI.Color.SOFT_GRAY))
							.withStyle(ChatFormatting.GRAY));
				}
			}
			components.add(Component.translatable(
							"item.ironladders.ladder_morph_tool.copy_style",
							ILUI.withColor(ILUI.Text.LBM, ILUI.Color.SOFT_GRAY))
					.withStyle(ChatFormatting.GRAY));
			components.add(Component.translatable(
							"item.ironladders.ladder_morph_tool.apply_style",
							ILUI.withColor(ILUI.Text.RBM, ILUI.Color.SOFT_GRAY))
					.withStyle(ChatFormatting.GRAY));
			components.add(Component.translatable(
							"item.ironladders.ladder_morph_tool.multiple_use",
							ILUI.withColor(ILUI.Text.SHIFT, shiftPressed ? ILUI.Color.GRAY : ILUI.Color.SOFT_GRAY))
					.withStyle(ChatFormatting.GRAY));
		}
	}
	
	@Override
	public Component getHoverTooltip(ItemStack carried, ItemStack hovered, boolean shift) {
		if (carried.is(this)){
			var hoveredItem = hovered.getItem();
			if (hoveredItem instanceof BlockItem blockItem && validMorphSource(blockItem.getBlock())){
				var currentMorph = morphState(carried, null);
				if (currentMorph != null && !currentMorph.getBlock().equals(blockItem.getBlock())){
					return Component.translatable("item.ironladders.ladder_morph_tool.hover",
									ILUI.withColor(ILUI.Text.SHIFT_CLICK,
											shift ? ILUI.Color.GRAY : ILUI.Color.SOFT_GRAY))
							.withStyle(ChatFormatting.GRAY);
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean onClickWith(ItemStack self, ItemStack other, int button, boolean shift) {
		if (!shift) return false;
		if (other.getItem() instanceof BlockItem blockItem && validMorphSource(blockItem.getBlock())){
			writeMorphType(self, blockItem.getBlock());
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onClickedBy(ItemStack self, ItemStack other, int button, boolean shift) {
		return false;
	}

	
}
