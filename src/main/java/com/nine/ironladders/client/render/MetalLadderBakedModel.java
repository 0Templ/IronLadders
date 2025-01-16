package com.nine.ironladders.client.render;

import com.nine.ironladders.common.block.BaseMetalLadder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.DelegateBakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MetalLadderBakedModel extends DelegateBakedModel {

    public static final ModelProperty<BlockState> MORPH_MODEL_PROPERTY = new ModelProperty<>();

    public MetalLadderBakedModel(BakedModel bakedModel) {
        super(bakedModel);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(ModelData data) {
        if (data == null) {
            return super.getParticleIcon(ModelData.builder().build());
        }
        BlockState morphState = data.get(MORPH_MODEL_PROPERTY);
        if (morphState == null) {
            return super.getParticleIcon(data);
        }
        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(morphState);
        return model.getParticleIcon(morphState.getBlock() instanceof BaseMetalLadder ? null : data);
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        BlockState morphState = data != null ? data.get(MORPH_MODEL_PROPERTY) : null;
        Minecraft mc = Minecraft.getInstance();
        ChunkRenderTypeSet typeSet;
        if (state.getBlock() instanceof BaseMetalLadder) {
            typeSet = super.getRenderTypes(state, rand, ModelData.EMPTY);
        } else {
            typeSet = morphState != null ? mc.getBlockRenderer().getBlockModel(morphState).getRenderTypes(morphState, rand, data) : ItemBlockRenderTypes.getRenderLayers(state);
        }
        return ChunkRenderTypeSet.union(typeSet);
    }

    @Override
    @NotNull
    public List<BakedQuad> getQuads(BlockState state, @Nullable Direction side, @Nonnull RandomSource rand, @Nonnull ModelData extraData, RenderType renderType) {
        List<BakedQuad> result = new ArrayList<>();
        BlockState morphState = extraData.get(MORPH_MODEL_PROPERTY);
        if (morphState != null) {
            morphState = morphState.getBlock().withPropertiesOf(state);
            BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(morphState);
            if (morphState.getBlock() instanceof BaseMetalLadder) {
                result.addAll(model.getQuads(morphState, side, rand, ModelData.EMPTY, renderType));
            } else {
                result.addAll(model.getQuads(morphState, side, rand, extraData, renderType));
            }
            return result;
        }
        result.addAll(super.getQuads(state, side, rand, extraData, renderType));
        return result;
    }
}
