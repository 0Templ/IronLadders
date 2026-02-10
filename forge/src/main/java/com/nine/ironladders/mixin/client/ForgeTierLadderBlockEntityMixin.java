package com.nine.ironladders.mixin.client;

import com.nine.ironladders.client.LadderRenderData;
import com.nine.ironladders.client.render.ForgeTierLadderBakedModel;
import com.nine.ironladders.common.block.entity.MetalLadderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MetalLadderBlockEntity.class)
public class ForgeTierLadderBlockEntityMixin extends BlockEntity {
	
	public ForgeTierLadderBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}
	
	@Override
	public ModelData getModelData() {
		MetalLadderBlockEntity be = ((MetalLadderBlockEntity) (Object) this);
		return ModelData.builder().with(ForgeTierLadderBakedModel.MODEL_DATA, new LadderRenderData(be.morphState(), be.modelType(), be.isHideAttachments())).build();
	}
	
	@Inject(method = "setChanged", at = @At("TAIL"))
	public void il$load(CallbackInfo ci) {
		requestModelDataUpdate();
	}
	
	@Inject(method = "loadAdditional", at = @At("TAIL"))
	public void il$load(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
		if (level != null && level.isClientSide) {
			requestModelDataUpdate();
		}
	}

}
