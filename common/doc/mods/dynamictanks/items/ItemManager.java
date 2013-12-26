package doc.mods.dynamictanks.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.common.ModConfig;
import doc.mods.dynamictanks.helpers.CPotionHelper;

public class ItemManager
{
	public static Item upgradeItem = null;
	public static Item hammerItem = null;
	public static Item ironPlateItem = null;
	public static Item liquidDiamond = null;
	public static Item softDiamond = null;
	public static Item buckets = null;
	public static Item chalice = null;

	public static void registerItems()
	{
		upgradeItem = new UpgradeItems(ModConfig.ItemIDs.upgradeItems);
		hammerItem = new HammerItem(ModConfig.ItemIDs.hammerItem);
		ironPlateItem = new IronPlateItem(ModConfig.ItemIDs.ironPlateItem);
		liquidDiamond = new liquidDiamondItem(ModConfig.ItemIDs.liquidDiamondItem);
		softDiamond = new SoftenedDiamondItem(ModConfig.ItemIDs.softDiamondItem);
		if (ModConfig.miscBoolean.enableLiquids == true) {
			buckets = new BucketPotion(ModConfig.ItemIDs.bucketPotion);
			chalice = new ChalicePotion(ModConfig.ItemIDs.chalciePotion);
		}

		for (int i = 0; i < UpgradeItems.names.length; i++)
			LanguageRegistry.addName(new ItemStack(upgradeItem, 1, i), UpgradeItems.names[i]);

		LanguageRegistry.addName(new ItemStack(hammerItem), "Hammer");
		LanguageRegistry.addName(new ItemStack(ironPlateItem), "Iron Mass");
		LanguageRegistry.addName(new ItemStack(liquidDiamond), "Softened Diamond Mass");
		LanguageRegistry.addName(new ItemStack(softDiamond), "Softened Diamond");
		
		if (ModConfig.miscBoolean.enableLiquids == false)
			return;
		// ================================ Bucket ========================== //
		LanguageRegistry.addName(new ItemStack(buckets, 1, 0), "Bucket of " + BucketPotion.names[0]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 1), "Bucket of " + BucketPotion.names[1]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 2), "Bucket of " + BucketPotion.names[2]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 3), "Bucket of " + BucketPotion.names[3]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 4), "Bucket of " + BucketPotion.names[4]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 5), "Bucket of " + BucketPotion.names[5]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 6), "Bucket of " + BucketPotion.names[6]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 7), "Bucket of " + BucketPotion.names[7]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 8), "Bucket of " + BucketPotion.names[8]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 9), "Bucket of " + BucketPotion.names[9]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 10), "Bucket of " + BucketPotion.names[10]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 11), "Bucket of " + BucketPotion.names[11]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 12), "Bucket of " + BucketPotion.names[12]);
		LanguageRegistry.addName(new ItemStack(buckets, 1, 13), "Liquid Nitroglycerine");
		LanguageRegistry.addName(new ItemStack(buckets, 1, 14), "Omni-Power Fluid");
		// ================================ Chalice ========================== //
		LanguageRegistry.addName(new ItemStack(chalice, 1, 0), "Chalice");
		LanguageRegistry.addName(new ItemStack(chalice, 1, 1), "Chalice of " + ChalicePotion.names[1]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 2), "Chalice of " + ChalicePotion.names[2]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 3), "Chalice of " + ChalicePotion.names[3]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 4), "Chalice of " + ChalicePotion.names[4]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 5), "Chalice of " + ChalicePotion.names[5]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 6), "Chalice of " + ChalicePotion.names[6]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 7), "Chalice of " + ChalicePotion.names[7]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 8), "Chalice of " + ChalicePotion.names[8]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 9), "Chalice of " + ChalicePotion.names[9]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 10), "Chalice of " + ChalicePotion.names[10]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 11), "Chalice of " + ChalicePotion.names[11]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 12), "Chalice of " + ChalicePotion.names[12]);
		LanguageRegistry.addName(new ItemStack(chalice, 1, 13), "Chalice of " + ChalicePotion.names[13]);
	}

	public static void registerRecipes()
	{
		GameRegistry.addSmelting(softDiamond.itemID, new ItemStack(liquidDiamond, 1), 0.5F);
		GameRegistry.addShapedRecipe(new ItemStack(hammerItem), new Object[]
				{
			"ISI", "ITI", " T ",
			'I', Item.ingotIron,
			'S', Item.silk,
			'T', Item.stick
				});
		GameRegistry.addShapedRecipe(new ItemStack(upgradeItem, 1, 0), new Object[]
				{
			"GSG", "RDR", "GSG",
			'G', Block.glowStone,
			'S', Block.daylightSensor,
			'R', Block.blockRedstone,
			'D', Item.diamond
				});
		GameRegistry.addShapedRecipe(new ItemStack(upgradeItem, 1, 1), new Object[]
				{
			"GSD", "RLR", "DSG",
			'G', Block.glowStone,
			'S', Block.daylightSensor,
			'R', Block.blockRedstone,
			'D', Item.diamond,
			'L', new ItemStack(liquidDiamond, 1, Short.MAX_VALUE)
				});
		GameRegistry.addShapelessRecipe(new ItemStack(softDiamond),
				new ItemStack(hammerItem, 1, 0), Item.diamond
				);
		GameRegistry.addShapelessRecipe(new ItemStack(ironPlateItem, 4),
				Item.ingotIron, Item.ingotIron, Item.ingotIron, Item.ingotIron,
				new ItemStack(hammerItem, 1, Short.MAX_VALUE)
				);
		if (ModConfig.miscBoolean.enableLiquids == false)
			return;
		GameRegistry.addShapelessRecipe(new ItemStack(buckets, 1, 13),
				Block.tnt, Block.tnt, Item.bucketMilk, Item.redstone
				);
		GameRegistry.addShapedRecipe(new ItemStack(chalice, 1), new Object[]
				{
			"ICI", " I ", "III",
			'I', Item.ingotGold,
			'C', new ItemStack(buckets, 1, 12)
				});
	}
}
