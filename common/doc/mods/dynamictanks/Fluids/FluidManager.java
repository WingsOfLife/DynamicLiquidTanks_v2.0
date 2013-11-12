package doc.mods.dynamictanks.Fluids;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.common.ModConfig;
import doc.mods.dynamictanks.helpers.PotionIdHelper;
import doc.mods.dynamictanks.items.ItemManager;

public class FluidManager {

	public static ArrayList<FluidPotion> blockType = new ArrayList<FluidPotion>();
	
	public static Block potionBlock = null;
	public static FluidPotion regenBlock = null;
	public static FluidPotion swiftBlock = null;
	public static FluidPotion fireBlock = null;
	public static FluidPotion poisonBlock = null;
	public static FluidPotion healingBlock = null;
	public static FluidPotion nightBlock = null;
	public static FluidPotion weakBlock = null;
	public static FluidPotion strengthBlock = null;
	public static FluidPotion slowBlock = null;
	public static FluidPotion harmingBlock = null;
	public static FluidPotion waterBlock = null;
	public static FluidPotion invisBlock = null;
	
	public static Fluid potionFluid = null;
	public static Fluid regenFluid = null;
	public static Fluid swiftFluid = null;
	public static Fluid fireFluid = null;
	public static Fluid poisonFluid = null;
	public static Fluid healingFluid = null;
	public static Fluid nightFluid = null;
	public static Fluid weakFluid = null;
	public static Fluid strengthFluid = null;
	public static Fluid slowFluid = null;
	public static Fluid harmingFluid = null;
	public static Fluid waterFluid = null;
	public static Fluid invisFluid = null;
	
	public static void registerFluids() {
		
		potionFluid = new Fluid("potion").setBlockID(ModConfig.FluidIDs.potion).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(potionFluid);
		potionBlock = new FluidPotion(ModConfig.FluidIDs.potion, potionFluid, Material.water, 
				"potion", 0);
		GameRegistry.registerBlock(potionBlock, "potion");
		LanguageRegistry.addName(potionBlock, "potion");
		
		regenFluid = new Fluid(PotionIdHelper.nameFromMeta(0)).setBlockID(ModConfig.FluidIDs.regen).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(regenFluid);
		regenBlock = new FluidPotion(ModConfig.FluidIDs.regen, regenFluid, Material.water, 
				PotionIdHelper.nameFromMeta(0), PotionIdHelper.potions[0]);
		GameRegistry.registerBlock(regenBlock, PotionIdHelper.nameFromMeta(0));
		LanguageRegistry.addName(regenBlock, PotionIdHelper.nameFromMeta(0));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(regenFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 0), new ItemStack(Item.bucketEmpty)));
		blockType.add(regenBlock);
		
		swiftFluid = new Fluid(PotionIdHelper.nameFromMeta(1)).setBlockID(ModConfig.FluidIDs.swift).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(swiftFluid);
		swiftBlock = new FluidPotion(ModConfig.FluidIDs.swift, swiftFluid, Material.water, 
				PotionIdHelper.nameFromMeta(1), PotionIdHelper.potions[1]);
		GameRegistry.registerBlock(swiftBlock, PotionIdHelper.nameFromMeta(1));
		LanguageRegistry.addName(swiftBlock, PotionIdHelper.nameFromMeta(1));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(swiftFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(swiftBlock);
		
		fireFluid = new Fluid(PotionIdHelper.nameFromMeta(2)).setBlockID(ModConfig.FluidIDs.fire).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(fireFluid);
		fireBlock = new FluidPotion(ModConfig.FluidIDs.fire, fireFluid, Material.water, 
				PotionIdHelper.nameFromMeta(2), PotionIdHelper.potions[2]);
		GameRegistry.registerBlock(fireBlock, PotionIdHelper.nameFromMeta(2));
		LanguageRegistry.addName(fireBlock, PotionIdHelper.nameFromMeta(2));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(fireFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(fireBlock);
		
		poisonFluid = new Fluid(PotionIdHelper.nameFromMeta(3)).setBlockID(ModConfig.FluidIDs.poison).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(poisonFluid);
		poisonBlock = new FluidPotion(ModConfig.FluidIDs.poison, poisonFluid, Material.water, 
				PotionIdHelper.nameFromMeta(3), PotionIdHelper.potions[3]);
		GameRegistry.registerBlock(poisonBlock, PotionIdHelper.nameFromMeta(3));
		LanguageRegistry.addName(poisonBlock, PotionIdHelper.nameFromMeta(3));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(poisonFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(poisonBlock);
		
		healingFluid = new Fluid(PotionIdHelper.nameFromMeta(4)).setBlockID(ModConfig.FluidIDs.healing).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(healingFluid);
		healingBlock = new FluidPotion(ModConfig.FluidIDs.healing, healingFluid, Material.water, 
				PotionIdHelper.nameFromMeta(4), PotionIdHelper.potions[4]);
		GameRegistry.registerBlock(healingBlock, PotionIdHelper.nameFromMeta(4));
		LanguageRegistry.addName(healingBlock, PotionIdHelper.nameFromMeta(4));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(healingFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(healingBlock);
		
		nightFluid = new Fluid(PotionIdHelper.nameFromMeta(5)).setBlockID(ModConfig.FluidIDs.night).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(nightFluid);
		nightBlock = new FluidPotion(ModConfig.FluidIDs.night, nightFluid, Material.water, 
				PotionIdHelper.nameFromMeta(5), PotionIdHelper.potions[5]);
		GameRegistry.registerBlock(nightBlock, PotionIdHelper.nameFromMeta(5));
		LanguageRegistry.addName(nightBlock, PotionIdHelper.nameFromMeta(5));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(nightFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(nightBlock);
		
		weakFluid = new Fluid(PotionIdHelper.nameFromMeta(6)).setBlockID(ModConfig.FluidIDs.weak).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(weakFluid);
		weakBlock = new FluidPotion(ModConfig.FluidIDs.weak, weakFluid, Material.water, 
				PotionIdHelper.nameFromMeta(6), PotionIdHelper.potions[6]);
		GameRegistry.registerBlock(weakBlock, PotionIdHelper.nameFromMeta(6));
		LanguageRegistry.addName(weakBlock, PotionIdHelper.nameFromMeta(6));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(weakFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(weakBlock);
		
		strengthFluid = new Fluid(PotionIdHelper.nameFromMeta(7)).setBlockID(ModConfig.FluidIDs.strength).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(strengthFluid);
		strengthBlock = new FluidPotion(ModConfig.FluidIDs.strength, strengthFluid, Material.water, 
				PotionIdHelper.nameFromMeta(7), PotionIdHelper.potions[7]);
		GameRegistry.registerBlock(strengthBlock, PotionIdHelper.nameFromMeta(7));
		LanguageRegistry.addName(strengthBlock, PotionIdHelper.nameFromMeta(7));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(strengthFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(strengthBlock);
		
		slowFluid = new Fluid(PotionIdHelper.nameFromMeta(8)).setBlockID(ModConfig.FluidIDs.slow).setViscosity(3500).setLuminosity(8);
		FluidRegistry.registerFluid(slowFluid);
		slowBlock = new FluidPotion(ModConfig.FluidIDs.slow, slowFluid, Material.water, 
				PotionIdHelper.nameFromMeta(8), PotionIdHelper.potions[8]);
		GameRegistry.registerBlock(slowBlock, PotionIdHelper.nameFromMeta(8));
		LanguageRegistry.addName(slowBlock, PotionIdHelper.nameFromMeta(8));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(slowFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(slowBlock);
		
		harmingFluid = new Fluid(PotionIdHelper.nameFromMeta(9)).setBlockID(ModConfig.FluidIDs.harming).setViscosity(3500).setLuminosity(9);
		FluidRegistry.registerFluid(harmingFluid);
		harmingBlock = new FluidPotion(ModConfig.FluidIDs.harming, harmingFluid, Material.water, 
				PotionIdHelper.nameFromMeta(9), PotionIdHelper.potions[9]);
		GameRegistry.registerBlock(harmingBlock, PotionIdHelper.nameFromMeta(9));
		LanguageRegistry.addName(harmingBlock, PotionIdHelper.nameFromMeta(9));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(harmingFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(harmingBlock);
		
		waterFluid = new Fluid(PotionIdHelper.nameFromMeta(10)).setBlockID(ModConfig.FluidIDs.water).setViscosity(3500).setLuminosity(10);
		FluidRegistry.registerFluid(waterFluid);
		waterBlock = new FluidPotion(ModConfig.FluidIDs.water, waterFluid, Material.water, 
				PotionIdHelper.nameFromMeta(10), PotionIdHelper.potions[10]);
		GameRegistry.registerBlock(waterBlock, PotionIdHelper.nameFromMeta(10));
		LanguageRegistry.addName(waterBlock, PotionIdHelper.nameFromMeta(10));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(waterFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(waterBlock);
		
		invisFluid = new Fluid(PotionIdHelper.nameFromMeta(11)).setBlockID(ModConfig.FluidIDs.invis).setViscosity(3500).setLuminosity(11);
		FluidRegistry.registerFluid(invisFluid);
		invisBlock = new FluidPotion(ModConfig.FluidIDs.invis, invisFluid, Material.water, 
				PotionIdHelper.nameFromMeta(11), PotionIdHelper.potions[11]);
		GameRegistry.registerBlock(invisBlock, PotionIdHelper.nameFromMeta(11));
		LanguageRegistry.addName(invisBlock, PotionIdHelper.nameFromMeta(11));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(invisFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(invisBlock);
	}

}
