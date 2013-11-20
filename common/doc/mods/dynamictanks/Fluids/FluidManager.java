package doc.mods.dynamictanks.Fluids;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.common.BucketHandler;
import doc.mods.dynamictanks.common.ModConfig;
import doc.mods.dynamictanks.helpers.PotionDamage;
import doc.mods.dynamictanks.helpers.CPotionHelper;
import doc.mods.dynamictanks.items.ItemManager;

public class FluidManager {

	public static ArrayList<Block> blockType = new ArrayList<Block>();

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

		regenFluid = new Fluid(CPotionHelper.nameFromMeta(0)).setBlockID(ModConfig.FluidIDs.regen).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(regenFluid);
		regenBlock = new FluidPotion(ModConfig.FluidIDs.regen, regenFluid, Material.water, 
				CPotionHelper.nameFromMeta(0), CPotionHelper.potions[0]);
		GameRegistry.registerBlock(regenBlock, CPotionHelper.nameFromMeta(0));
		LanguageRegistry.addName(regenBlock, CPotionHelper.nameFromMeta(0));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(regenFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 0), new ItemStack(Item.bucketEmpty)));
		blockType.add(regenBlock);

		swiftFluid = new Fluid(CPotionHelper.nameFromMeta(1)).setBlockID(ModConfig.FluidIDs.swift).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(swiftFluid);
		swiftBlock = new FluidPotion(ModConfig.FluidIDs.swift, swiftFluid, Material.water, 
				CPotionHelper.nameFromMeta(1), CPotionHelper.potions[1]);
		GameRegistry.registerBlock(swiftBlock, CPotionHelper.nameFromMeta(1));
		LanguageRegistry.addName(swiftBlock, CPotionHelper.nameFromMeta(1));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(swiftFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 1), new ItemStack(Item.bucketEmpty)));
		blockType.add(swiftBlock);

		fireFluid = new Fluid(CPotionHelper.nameFromMeta(2)).setBlockID(ModConfig.FluidIDs.fire).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(fireFluid);
		fireBlock = new FluidPotion(ModConfig.FluidIDs.fire, fireFluid, Material.water, 
				CPotionHelper.nameFromMeta(2), CPotionHelper.potions[2]);
		GameRegistry.registerBlock(fireBlock, CPotionHelper.nameFromMeta(2));
		LanguageRegistry.addName(fireBlock, CPotionHelper.nameFromMeta(2));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(fireFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 2), new ItemStack(Item.bucketEmpty)));
		blockType.add(fireBlock);

		poisonFluid = new Fluid(CPotionHelper.nameFromMeta(3)).setBlockID(ModConfig.FluidIDs.poison).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(poisonFluid);
		poisonBlock = new FluidPotion(ModConfig.FluidIDs.poison, poisonFluid, Material.water, 
				CPotionHelper.nameFromMeta(3), CPotionHelper.potions[3]);
		GameRegistry.registerBlock(poisonBlock, CPotionHelper.nameFromMeta(3));
		LanguageRegistry.addName(poisonBlock, CPotionHelper.nameFromMeta(3));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(poisonFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 3), new ItemStack(Item.bucketEmpty)));
		blockType.add(poisonBlock);

		healingFluid = new Fluid(CPotionHelper.nameFromMeta(4)).setBlockID(ModConfig.FluidIDs.healing).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(healingFluid);
		healingBlock = new FluidPotion(ModConfig.FluidIDs.healing, healingFluid, Material.water, 
				CPotionHelper.nameFromMeta(4), CPotionHelper.potions[4]);
		GameRegistry.registerBlock(healingBlock, CPotionHelper.nameFromMeta(4));
		LanguageRegistry.addName(healingBlock, CPotionHelper.nameFromMeta(4));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(healingFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 4), new ItemStack(Item.bucketEmpty)));
		blockType.add(healingBlock);

		nightFluid = new Fluid(CPotionHelper.nameFromMeta(5)).setBlockID(ModConfig.FluidIDs.night).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(nightFluid);
		nightBlock = new FluidPotion(ModConfig.FluidIDs.night, nightFluid, Material.water, 
				CPotionHelper.nameFromMeta(5), CPotionHelper.potions[5]);
		GameRegistry.registerBlock(nightBlock, CPotionHelper.nameFromMeta(5));
		LanguageRegistry.addName(nightBlock, CPotionHelper.nameFromMeta(5));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(nightFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 5), new ItemStack(Item.bucketEmpty)));
		blockType.add(nightBlock);

		weakFluid = new Fluid(CPotionHelper.nameFromMeta(6)).setBlockID(ModConfig.FluidIDs.weak).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(weakFluid);
		weakBlock = new FluidPotion(ModConfig.FluidIDs.weak, weakFluid, Material.water, 
				CPotionHelper.nameFromMeta(6), CPotionHelper.potions[6]);
		GameRegistry.registerBlock(weakBlock, CPotionHelper.nameFromMeta(6));
		LanguageRegistry.addName(weakBlock, CPotionHelper.nameFromMeta(6));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(weakFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 6), new ItemStack(Item.bucketEmpty)));
		blockType.add(weakBlock);

		strengthFluid = new Fluid(CPotionHelper.nameFromMeta(7)).setBlockID(ModConfig.FluidIDs.strength).setViscosity(3500).setLuminosity(7);
		FluidRegistry.registerFluid(strengthFluid);
		strengthBlock = new FluidPotion(ModConfig.FluidIDs.strength, strengthFluid, Material.water, 
				CPotionHelper.nameFromMeta(7), CPotionHelper.potions[7]);
		GameRegistry.registerBlock(strengthBlock, CPotionHelper.nameFromMeta(7));
		LanguageRegistry.addName(strengthBlock, CPotionHelper.nameFromMeta(7));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(strengthFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 7), new ItemStack(Item.bucketEmpty)));
		blockType.add(strengthBlock);

		slowFluid = new Fluid(CPotionHelper.nameFromMeta(8)).setBlockID(ModConfig.FluidIDs.slow).setViscosity(3500).setLuminosity(8);
		FluidRegistry.registerFluid(slowFluid);
		slowBlock = new FluidPotion(ModConfig.FluidIDs.slow, slowFluid, Material.water, 
				CPotionHelper.nameFromMeta(8), CPotionHelper.potions[8]);
		GameRegistry.registerBlock(slowBlock, CPotionHelper.nameFromMeta(8));
		LanguageRegistry.addName(slowBlock, CPotionHelper.nameFromMeta(8));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(slowFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 8), new ItemStack(Item.bucketEmpty)));
		blockType.add(slowBlock);

		harmingFluid = new Fluid(CPotionHelper.nameFromMeta(9)).setBlockID(ModConfig.FluidIDs.harming).setViscosity(3500).setLuminosity(9);
		FluidRegistry.registerFluid(harmingFluid);
		harmingBlock = new FluidPotion(ModConfig.FluidIDs.harming, harmingFluid, Material.water, 
				CPotionHelper.nameFromMeta(9), CPotionHelper.potions[9]);
		GameRegistry.registerBlock(harmingBlock, CPotionHelper.nameFromMeta(9));
		LanguageRegistry.addName(harmingBlock, CPotionHelper.nameFromMeta(9));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(harmingFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 9), new ItemStack(Item.bucketEmpty)));
		blockType.add(harmingBlock);

		waterFluid = new Fluid(CPotionHelper.nameFromMeta(10)).setBlockID(ModConfig.FluidIDs.water).setViscosity(3500).setLuminosity(10);
		FluidRegistry.registerFluid(waterFluid);
		waterBlock = new FluidPotion(ModConfig.FluidIDs.water, waterFluid, Material.water, 
				CPotionHelper.nameFromMeta(10), CPotionHelper.potions[10]);
		GameRegistry.registerBlock(waterBlock, CPotionHelper.nameFromMeta(10));
		LanguageRegistry.addName(waterBlock, CPotionHelper.nameFromMeta(10));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(waterFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 10), new ItemStack(Item.bucketEmpty)));
		blockType.add(waterBlock);

		invisFluid = new Fluid(CPotionHelper.nameFromMeta(11)).setBlockID(ModConfig.FluidIDs.invis).setViscosity(3500).setLuminosity(11);
		FluidRegistry.registerFluid(invisFluid);
		invisBlock = new FluidPotion(ModConfig.FluidIDs.invis, invisFluid, Material.water, 
				CPotionHelper.nameFromMeta(11), CPotionHelper.potions[11]);
		GameRegistry.registerBlock(invisBlock, CPotionHelper.nameFromMeta(11));
		LanguageRegistry.addName(invisBlock, CPotionHelper.nameFromMeta(11));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(new FluidStack(invisFluid, 1000),
				new ItemStack(ItemManager.buckets, 1, 11), new ItemStack(Item.bucketEmpty)));
		blockType.add(invisBlock);
	}

	public static void registerCraftingRecipes() {
		/*GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 0), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[0]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 1), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[1]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 2), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[2]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 3), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[3]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 4), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[4]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 5), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[5]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 6), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[6]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 7), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[7]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 8), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[8]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 9), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[9]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 10), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[10]),
			'W', Item.bucketWater
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.buckets, 1, 11), new Object[] {
			"PP ", "W  ", "   ",
			'P', new ItemStack(Item.potion, 1, PotionIdHelper.potions[11]),
			'W', Item.bucketWater
		});*/		
	}

	public static void registerBuckets() {
		BucketHandler.INSTANCE.buckets.put(regenBlock, new ItemStack(ItemManager.buckets, 1, 0));
		BucketHandler.INSTANCE.buckets.put(swiftBlock, new ItemStack(ItemManager.buckets, 1, 1));
		BucketHandler.INSTANCE.buckets.put(fireBlock, new ItemStack(ItemManager.buckets, 1, 2));
		BucketHandler.INSTANCE.buckets.put(poisonBlock, new ItemStack(ItemManager.buckets, 1, 3));
		BucketHandler.INSTANCE.buckets.put(healingBlock, new ItemStack(ItemManager.buckets, 1, 4));
		BucketHandler.INSTANCE.buckets.put(nightBlock, new ItemStack(ItemManager.buckets, 1, 5));
		BucketHandler.INSTANCE.buckets.put(weakBlock, new ItemStack(ItemManager.buckets, 1, 6));
		BucketHandler.INSTANCE.buckets.put(strengthBlock, new ItemStack(ItemManager.buckets, 1, 7));
		BucketHandler.INSTANCE.buckets.put(slowBlock, new ItemStack(ItemManager.buckets, 1, 8));
		BucketHandler.INSTANCE.buckets.put(harmingBlock, new ItemStack(ItemManager.buckets, 1, 9));
		BucketHandler.INSTANCE.buckets.put(waterBlock, new ItemStack(ItemManager.buckets, 1, 10));
		BucketHandler.INSTANCE.buckets.put(invisBlock, new ItemStack(ItemManager.buckets, 1, 11));
		
		BucketHandler.INSTANCE.chalice.put(regenBlock, new ItemStack(ItemManager.chalice, 1, 1));
		BucketHandler.INSTANCE.chalice.put(swiftBlock, new ItemStack(ItemManager.chalice, 1, 2));
		BucketHandler.INSTANCE.chalice.put(fireBlock, new ItemStack(ItemManager.chalice, 1, 3));
		BucketHandler.INSTANCE.chalice.put(poisonBlock, new ItemStack(ItemManager.chalice, 1, 4));
		BucketHandler.INSTANCE.chalice.put(healingBlock, new ItemStack(ItemManager.chalice, 1, 5));
		BucketHandler.INSTANCE.chalice.put(nightBlock, new ItemStack(ItemManager.chalice, 1, 6));
		BucketHandler.INSTANCE.chalice.put(weakBlock, new ItemStack(ItemManager.chalice, 1, 7));
		BucketHandler.INSTANCE.chalice.put(strengthBlock, new ItemStack(ItemManager.chalice, 1, 8));
		BucketHandler.INSTANCE.chalice.put(slowBlock, new ItemStack(ItemManager.chalice, 1, 9));
		BucketHandler.INSTANCE.chalice.put(harmingBlock, new ItemStack(ItemManager.chalice, 1, 10));
		BucketHandler.INSTANCE.chalice.put(waterBlock, new ItemStack(ItemManager.chalice, 1, 11));
		BucketHandler.INSTANCE.chalice.put(invisBlock, new ItemStack(ItemManager.chalice, 1, 12));

		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
	}

}
