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
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "copper_ingots"));
			default -> new ILItemMaterial(Items.COPPER_INGOT);
		};
		
		this.IRON_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/iron"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "iron_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.GOLD_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/gold"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "gold_ingots"));
			default -> new ILItemMaterial(Items.GOLD_INGOT);
		};
		
		this.DIAMOND = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "gems/diamond"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "diamonds"));
			default -> new ILItemMaterial(Items.DIAMOND);
		};
		
		this.NETHERITE = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/netherite"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "netherite_ingots"));
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
			default -> new ILTagMaterial(ILTags.createItemTag("c", "redstone_dusts"));
		};
		this.REDSTONE_TORCH = new ILItemMaterial(Items.REDSTONE_TORCH);
		this.LAPIS = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "gems/lapis"));
			default -> new ILTagMaterial(ILTags.createItemTag("c", "lapis"));
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
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "bronze_ingots"));
			default -> new ILItemMaterial(Items.GOLD_INGOT);
		};
		
		this.TIN_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/tin"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "tin_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		this.STEEL_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/steel"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "steel_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		this.SILVER_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/silver"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "silver_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		this.ALUMINUM_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/aluminum"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "aluminum_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		this.LEAD_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/lead"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "lead_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		this.PLATINUM_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/platinum"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "platinum_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.CHROMIUM_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/chromium"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "chromium_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.ADVANCED_ALLOY_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/advanced_alloy"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "advanced_alloy_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.NICKEL_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/nickel"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "nickel_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.TUNGSTEN_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/tungsten"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "tungsten_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.TUNGSTEN_STEEL_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/tungsten_steel"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "tungstensteel_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.TITANIUM_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/titanium"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "titanium_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.ZINC_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/zinc"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "zinc_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.INVAR_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/invar"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "invar_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.ELECTRUM_INGOT = switch (target) {
			case FORGE -> new ILTagMaterial(ILTags.createItemTag("forge", "ingots/electrum"));
			case FABRIC -> new ILTagMaterial(ILTags.createItemTag("c", "electrum_ingots"));
			default -> new ILItemMaterial(Items.IRON_INGOT);
		};
		
		this.NETHERITE_SCRAP = new ILItemMaterial(Items.NETHERITE_SCRAP);
		
	}
	
}
