package com.nine.ironladders.init;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.entity.MetalLadderEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, IronLadders.MODID);
    

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MetalLadderEntity>> METAL_LADDER = BLOCK_ENTITIES.register("metal_ladder_entity",
            () -> BlockEntityType.Builder.of(MetalLadderEntity::new,
                    BlockRegistry.ALUMINUM_LADDER.get(),
                    BlockRegistry.BRONZE_LADDER.get(),
                    BlockRegistry.COPPER_LADDER.get(),
                    BlockRegistry.DIAMOND_LADDER.get(),
                    BlockRegistry.OBSIDIAN_LADDER.get(),
                    BlockRegistry.GOLD_LADDER.get(),
                    BlockRegistry.CRYING_OBSIDIAN_LADDER.get(),
                    BlockRegistry.EXPOSED_COPPER_LADDER.get(),
                    BlockRegistry.WEATHERED_COPPER_LADDER.get(),
                    BlockRegistry.IRON_LADDER.get(),
                    BlockRegistry.LEAD_LADDER.get(),
                    BlockRegistry.NETHERITE_LADDER.get(),
                    BlockRegistry.BEDROCK_LADDER.get(),
                    BlockRegistry.OXIDIZED_COPPER_LADDER.get(),
                    BlockRegistry.SILVER_LADDER.get(),
                    BlockRegistry.STEEL_LADDER.get(),
                    BlockRegistry.TIN_LADDER.get(),
                    BlockRegistry.WAXED_COPPER_LADDER.get(),
                    BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get(),
                    BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get(),
                    BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get()
            ).build(null));

}
