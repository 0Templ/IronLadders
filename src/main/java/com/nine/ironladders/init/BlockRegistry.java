package com.nine.ironladders.init;

import com.nine.ironladders.IronLadders;
import com.nine.ironladders.common.block.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BlockRegistry {
    public static final Block OXIDIZED_COPPER_LADDER = registerBlock("oxidized_copper_ladder",
            new CopperLadderBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.create().mapColor(MapColor.TEAL).strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.COPPER).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block WEATHERED_COPPER_LADDER = registerBlock("weathered_copper_ladder",
            new CopperLadderBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.create().mapColor(MapColor.DARK_AQUA).strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.COPPER).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block EXPOSED_COPPER_LADDER = registerBlock("exposed_copper_ladder",
            new CopperLadderBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.create().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.COPPER).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block COPPER_LADDER = registerBlock("copper_ladder",
            new CopperLadderBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.create().mapColor(MapColor.ORANGE).strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.COPPER).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block WAXED_OXIDIZED_COPPER_LADDER = registerBlock("waxed_oxidized_copper_ladder",
            new CopperLadderBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.create().mapColor(MapColor.TEAL).strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.COPPER).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block WAXED_WEATHERED_COPPER_LADDER = registerBlock("waxed_weathered_copper_ladder",
            new CopperLadderBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.create().mapColor(MapColor.DARK_AQUA).strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.COPPER).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block WAXED_EXPOSED_COPPER_LADDER = registerBlock("waxed_exposed_copper_ladder",
            new CopperLadderBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.create().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.COPPER).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block WAXED_COPPER_LADDER = registerBlock("waxed_copper_ladder",
            new CopperLadderBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.create().mapColor(MapColor.ORANGE).strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.COPPER).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block IRON_LADDER = registerBlock("iron_ladder",
            new IronLadderBlock(FabricBlockSettings.create().mapColor(MapColor.IRON_GRAY).strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block GOLD_LADDER = registerBlock("gold_ladder",
            new GoldLadderBlock(FabricBlockSettings.create().mapColor(MapColor.GOLD).strength(1.5F, 6.0F).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block DIAMOND_LADDER = registerBlock("diamond_ladder",
            new DiamondLadderBlock(FabricBlockSettings.create().mapColor(MapColor.DIAMOND_BLUE).strength(3.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block NETHERITE_LADDER = registerBlock("netherite_ladder",
            new NetheriteLadderBlock(FabricBlockSettings.create().mapColor(MapColor.BLACK).strength(5.0F, 1200.0F).requiresTool().sounds(BlockSoundGroup.NETHERITE).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));



    public static int getMorphId(Block ladderBlock) {
        if (ladderBlock.equals(Blocks.LADDER)) {
            return 99;
        } else if (ladderBlock.equals(OXIDIZED_COPPER_LADDER) || ladderBlock.equals(WAXED_OXIDIZED_COPPER_LADDER)) {
            return 1;
        } else if (ladderBlock.equals(WEATHERED_COPPER_LADDER) || ladderBlock.equals(WAXED_WEATHERED_COPPER_LADDER)) {
            return 2;
        } else if (ladderBlock.equals(EXPOSED_COPPER_LADDER) || ladderBlock.equals(WAXED_EXPOSED_COPPER_LADDER)) {
            return 3;
        } else if (ladderBlock.equals(COPPER_LADDER) || ladderBlock.equals(WAXED_COPPER_LADDER)) {
            return 4;
        } else if (ladderBlock.equals(IRON_LADDER)) {
            return 5;
        } else if (ladderBlock.equals(GOLD_LADDER)) {
            return 6;
        } else if (ladderBlock.equals(DIAMOND_LADDER)) {
            return 7;
        } else if (ladderBlock.equals(NETHERITE_LADDER)) {
            return 8;
        }else if (ladderBlock instanceof VineBlock) {
            return 9;
        }
        return 0;
    }
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(IronLadders.MODID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(IronLadders.MODID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    public static void register() {
    }
}
