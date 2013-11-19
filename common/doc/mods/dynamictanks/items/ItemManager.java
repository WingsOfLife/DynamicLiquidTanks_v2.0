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
import doc.mods.dynamictanks.helpers.PotionHelper;

public class ItemManager {

	public static Item upgradeItem = null;
	public static Item hammerItem = null;
	public static Item ironPlateItem = null;
	public static Item liquidDiamond = null;
	public static Item softDiamond = null;
	public static Item buckets = null;
	
	public static void registerItems() {
		upgradeItem = new UpgradeItems(ModConfig.ItemIDs.upgradeItems);
		hammerItem = new HammerItem(ModConfig.ItemIDs.hammerItem);
		ironPlateItem = new IronPlateItem(ModConfig.ItemIDs.ironPlateItem);
		liquidDiamond = new liquidDiamondItem(ModConfig.ItemIDs.liquidDiamondItem);
		softDiamond = new SoftenedDiamondItem(ModConfig.ItemIDs.softDiamondItem);
		buckets = new BucketPotion(ModConfig.ItemIDs.bucketPotion);
		
		for (int i = 0; i < UpgradeItems.names.length; i++)
			LanguageRegistry.addName(new ItemStack(upgradeItem, 1, i), UpgradeItems.names[i]);
		LanguageRegistry.addName(new ItemStack(hammerItem), "Hammer");
		LanguageRegistry.addName(new ItemStack(ironPlateItem), "Iron Mass");
		LanguageRegistry.addName(new ItemStack(liquidDiamond), "Softened Diamond Mass");
		LanguageRegistry.addName(new ItemStack(softDiamond), "Softened Diamond");
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
	}
	
	public static void registerRecipes() {
		
		GameRegistry.addSmelting(softDiamond.itemID, new ItemStack(liquidDiamond, 1), 0.5F);	
		
		GameRegistry.addShapedRecipe(new ItemStack(hammerItem), new Object[] {
			"ISI", "ITI", " T ",
			'I', Item.ingotIron,
			'S', Item.silk,
			'T', Item.stick
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(upgradeItem, 1, 0), new Object[] {
			"GSG", "RDR", "GSG",
			'G', Block.glowStone,
			'S', Block.daylightSensor,
			'R', Block.blockRedstone,
			'D', Item.diamond
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(upgradeItem, 1, 1), new Object[] {
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
	}
	
}
