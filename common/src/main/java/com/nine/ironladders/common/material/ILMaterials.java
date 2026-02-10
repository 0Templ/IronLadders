package com.nine.ironladders.common.material;

import com.nine.ironladders.common.util.ILTags;
import com.nine.ironladders.platform.Platform;
import com.nine.ironladders.platform.util.LoaderTarget;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class ILMaterials {
	
	public static final ILMaterials PLATFORM_MATERIALS = new ILMaterials(Platform.CORE.currentLoader());
	
	public final ILMaterial COPPER_INGOT;
	public final ILMaterial IRON_INGOT;
	public final ILMaterial GOLD_INGOT;
	public final ILMaterial DIAMOND;
	public final ILMaterial NETHERITE;
	public final ILMaterial OBSIDIAN;
	public final ILMaterial CRYING_OBSIDIAN;
	
	public final ILMaterial BRONZE_INGOT;
	public final ILMaterial TIN_INGOT;
	public final ILMaterial STEEL_INGOT;
	public final ILMaterial SILVER_INGOT;
	public final ILMaterial ALUMINUM_INGOT;
	public final ILMaterial LEAD_INGOT;
	public final ILMaterial PLATINUM_INGOT;
	public final ILMaterial CHROMIUM_INGOT;
	public final ILMaterial ADVANCED_ALLOY_INGOT;
	public final ILMaterial NICKEL_INGOT;
	public final ILMaterial TUNGSTEN_INGOT;
	public final ILMaterial TUNGSTEN_STEEL_INGOT;
	public final ILMaterial TITANIUM_INGOT;
	public final ILMaterial ZINC_INGOT;
	public final ILMaterial INVAR_INGOT;
	public final ILMaterial ELECTRUM_INGOT;
	
	
	public final ILMaterial SLIME_BALL;
	public final ILMaterial PAPPER;
	public final ILMaterial OBSERVER;
	public final ILMaterial REDSTONE_DUST;
	public final ILMaterial REDSTONE_TORCH;
	public final ILMaterial LAPIS;
	public final ILMaterial TRIM_TEMPLATE;
	public final ILMaterial GLOWSTONE_DUST;
	public final ILMaterial GLOWSTONE;
	public final ILMaterial DYE;
	public final ILMaterial GLASS_BLOCK;
	public final ILMaterial NETHERITE_SCRAP;
	
	
	public ILMaterials(LoaderTarget target) {
		
		this.COPPER_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/copper"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/copper"));
			default -> new ILItemMaterial(Items.COPPER_INGOT);
		};
		
		this.IRON_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/iron"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/iron"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.GOLD_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/gold"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/gold"));
			default -> new ILItemMaterial(Items.GOLD_INGOT);
		};
		
		this.DIAMOND = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "gems/diamond"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "gems/diamond"));
			default -> new ILItemMaterial(Items.DIAMOND);
		};
		
		this.NETHERITE = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/netherite"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/netherite"));
			default -> new ILItemMaterial(Items.NETHERITE_INGOT);
		};
		
		this.OBSIDIAN = switch (target) {
			case FORGE -> new ILItemMaterial(Items.OBSIDIAN);
			default -> new ILItemMaterial(Items.OBSIDIAN);
		};
		
		this.CRYING_OBSIDIAN = new ILItemMaterial(Items.CRYING_OBSIDIAN);
		
		
		this.SLIME_BALL = switch (target) {
			case FORGE -> new ILItemMaterial(Items.SLIME_BALL);
			default -> new ILItemMaterial(Items.SLIME_BALL);
		};
		
		this.PAPPER = switch (target) {
			case FORGE -> new ILItemMaterial(Items.PAPER);
			default -> new ILItemMaterial(Items.PAPER);
		};
		this.OBSERVER = new ILItemMaterial(Items.OBSERVER);
		this.REDSTONE_DUST = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "dusts/redstone"));
			default -> new ILTagMaterial(ILTags.createItemTag("c", "dusts/redstone"));
		};
		this.REDSTONE_TORCH = new ILItemMaterial(Items.REDSTONE_TORCH);
		this.LAPIS = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "gems/lapis"));
			default -> new ILTagMaterial(ILTags.createItemTag("c", "gems/lapis"));
		};
		this.TRIM_TEMPLATE = new ILTagMaterial(ItemTags.TRIM_TEMPLATES);
		
		this.GLOWSTONE_DUST = switch (target) {
			case FORGE -> new ILItemMaterial(Items.GLOWSTONE_DUST);
			default -> new ILItemMaterial(Items.GLOWSTONE_DUST);
		};
		
		this.GLOWSTONE = switch (target) {
			case FORGE -> new ILItemMaterial(Items.GLOWSTONE);
			default -> new ILItemMaterial(Items.GLOWSTONE);
		};
		this.DYE = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "dyes"));
			default -> new ILTagMaterial(ILTags.createItemTag("c", "dyes"));
		};
		this.GLASS_BLOCK = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "glass"));
			default -> new ILTagMaterial(ILTags.createItemTag("c", "glass_blocks"));
		};
		
		
		this.BRONZE_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/bronze"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/bronze"));
			default -> new ILItemMaterial(Items.GOLD_INGOT);
		};
		
		this.TIN_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/tin"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/tin"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		this.STEEL_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/steel"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/steel"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		this.SILVER_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/silver"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/silver"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		this.ALUMINUM_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/aluminum"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/aluminum"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		this.LEAD_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/lead"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/lead"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		this.PLATINUM_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/platinum"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/platinum"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.CHROMIUM_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/chromium"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/chromium"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.ADVANCED_ALLOY_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/advanced_alloy"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/advanced_alloy"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.NICKEL_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/nickel"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/nickel"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.TUNGSTEN_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/tungsten"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/tungsten"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.TUNGSTEN_STEEL_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/tungsten_steel"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/tungsten_steel"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.TITANIUM_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/titanium"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/titanium"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.ZINC_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/zinc"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/zinc"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.INVAR_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/invar"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/invar"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.ELECTRUM_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/electrum"));
			case FABRIC, NEOFORGE -> new ILTagMaterial(ILTags.createItemTag("c", "ingots/electrum"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.NETHERITE_SCRAP = new ILItemMaterial(Items.NETHERITE_SCRAP);
		
	}
	
}

