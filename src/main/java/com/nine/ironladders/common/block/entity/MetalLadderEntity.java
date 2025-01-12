package com.nine.ironladders.common.block.entity;

import com.nine.ironladders.client.render.MetalLadderBakedModel;
import com.nine.ironladders.init.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;


public class MetalLadderEntity extends BlockEntity {

    private BlockState morphState;

    public MetalLadderEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.METAL_LADDER.get(), pos, state);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains("morph_type")) {
            BlockState state = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound("morph_type"));
            if (!state.isAir()) {
                morphState = state;
                return;
            }
        }
        morphState = null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (morphState != null) {
            tag.put("morph_type", NbtUtils.writeBlockState(this.morphState));
        }
    }

    @Override
    @NotNull
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithId(pRegistries);
    }

    @Override
    @NotNull
    public ModelData getModelData() {
        return ModelData.builder().with(MetalLadderBakedModel.MORPH_MODEL_PROPERTY, morphState).build();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        setChanged();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider lookupProvider) {
        this.handleUpdateTag(packet.getTag(), lookupProvider);
    }

    public void setMorphState(BlockState state) {
        this.morphState = state;
        setChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level == null) {
            return;
        }
        requestModelDataUpdate();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        level.getLightEngine().checkBlock(getBlockPos());
        level.updateNeighborsAt(getBlockPos(), getBlockState().getBlock());
        getBlockState().updateNeighbourShapes(level, worldPosition, 2);
    }

    public BlockState getMorphState() {
        return morphState;
    }
}
