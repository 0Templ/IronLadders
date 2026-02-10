package com.nine.ironladders.common.block.entity;

import com.nine.ironladders.client.model.ModelType;
import com.nine.ironladders.common.block.MetalLadderBlock;
import com.nine.ironladders.init.ILBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetalLadderBlockEntity extends BlockEntity {
	
	private static final String MORPH_TYPE_KEY = "il_morph_type";
	private static final String UPGRADES_KEY = "il_upgrades";
	private static final String MODEL_TYPE_KEY = "il_model_type";
	private static final String HIDE_ATTACHMENTS_KEY = "il_hide_attachments";
	
	private BlockState morphState;
	private ModelType modelType;
	private int redstoneSignal = 0;
	private boolean hideAttachments;
	
	private final Map<Item, Integer> drops = new HashMap<>();
	
	public MetalLadderBlockEntity(BlockPos pos, BlockState blockState) {
		super(ILBlockEntities.METAL_LADDER.get(), pos, blockState);
	}
	
	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.contains(MORPH_TYPE_KEY)) {
			BlockState s = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound(MORPH_TYPE_KEY));
			morphState = !s.isAir() ? s : null;
		} else {
			morphState = null;
		}
		if (tag.contains(MODEL_TYPE_KEY)){
			String modelKey = tag.getString(MODEL_TYPE_KEY);
			modelType = ModelType.byKey(modelKey);
		} else {
			modelType = null;
		}
		
		hideAttachments = tag.contains(HIDE_ATTACHMENTS_KEY) && tag.getBoolean(HIDE_ATTACHMENTS_KEY);
		
		drops.clear();
		ListTag list = tag.getList(UPGRADES_KEY, Tag.TAG_COMPOUND);
		for (int i = 0; i < list.size(); i++) {
			CompoundTag t = list.getCompound(i);
			String s = t.getString("id");
			int count = t.getInt("count");
			if (count <= 0) continue;
			var id = ResourceLocation.tryParse(s);
			if (id != null) {
				var item = BuiltInRegistries.ITEM.get(id);
				drops.put(item, count);
			}
		}
		if (level != null && level.isClientSide) {
			BlockState state = getBlockState();
			level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_CLIENTS);
			level.getLightEngine().checkBlock(getBlockPos());
		}
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (morphState != null) tag.put(MORPH_TYPE_KEY, NbtUtils.writeBlockState(morphState));
		if (modelType != null) tag.putString(MODEL_TYPE_KEY, modelType.key);
		tag.putBoolean(HIDE_ATTACHMENTS_KEY, hideAttachments);
		
		ListTag list = new ListTag();
		for (var e : drops.entrySet()) {
			CompoundTag t = new CompoundTag();
			t.putString("id", e.getKey().toString());
			t.putInt("count", e.getValue());
			list.add(t);
		}
		tag.put(UPGRADES_KEY, list);
	}
	
	@Override
	public void setChanged() {
		super.setChanged();
		if (level == null || level.isClientSide) {
			return;
		}
		BlockState state = getBlockState();
		int light = resolveLightLevel(state);
		if (state.getValue(MetalLadderBlock.LIGHT_LEVEL) != light) {
			level.setBlock(worldPosition, state.setValue(MetalLadderBlock.LIGHT_LEVEL, light), Block.UPDATE_ALL);
			level.getLightEngine().checkBlock(getBlockPos());
		} else {
			level.sendBlockUpdated(getBlockPos(), state, state, Block.UPDATE_CLIENTS);
		}
	}
	
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return saveWithId(registries);
	}
	
	
	public List<ItemStack> getStacksToDrop(){
		List<ItemStack> ret = new ArrayList<>();
		for (var entry : drops.entrySet()) {
			ItemStack stack = new ItemStack(entry.getKey());
			stack.setCount(entry.getValue());
			ret.add(stack);
		}
		return ret;
	}
	
	public void addToDrop(Item item, int count){
		if (count <= 0) return;
		drops.merge(item, count, Integer::sum);
	}
	
	public void removeFromDrop(Item item){
		drops.remove(item);
	}
	
	public boolean hasItem(Item item){
		return drops.get(item) != null;
	}
	
	private int resolveLightLevel(BlockState state) {
		int light = 0;
		if (morphState != null) {
			if (morphState.getBlock() instanceof MetalLadderBlock metalLadderBlock) {
				light = metalLadderBlock.getDefaultLight();
			}
			else {
				light = morphState.getLightEmission();
			}
		}
		else {
			light = ((MetalLadderBlock) state.getBlock()).getDefaultLight();
		}
		if (state.getValue(MetalLadderBlock.LIGHTED)) {
			light = Math.max(MetalLadderBlock.LIGHTED_MODIFIER_LEVEL, light);
		}
		return light;
	}
	
	public boolean hasValidMorphState() {
		return morphState != null;
	}
	
	public BlockState morphState() {
		return morphState;
	}
	
	public void setMorphState(BlockState state) {
		this.morphState = state;
		setChanged();
	}
	
	public void setModelType(ModelType type) {
		if (type != modelType){
			modelType = type;
			setChanged();
		}
	}
	
	public ModelType modelType() {
		return modelType;
	}
	
	public int redstoneSignal() {
		return redstoneSignal;
	}
	
	public void setRedstoneSignal(int value) {
		redstoneSignal = Math.min(value, 15);
	}
	
	public void setHideAttachments(boolean value) {
		if (value != hideAttachments){
			this.hideAttachments = value;
			setChanged();
		}
	}
	
	public boolean isHideAttachments() {
		return hideAttachments;
	}
}
