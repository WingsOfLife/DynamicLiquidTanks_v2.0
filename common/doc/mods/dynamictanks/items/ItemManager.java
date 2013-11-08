package doc.mods.dynamictanks.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.common.ModConfig;

public class ItemManager {

	public static Item upgradeItem = null;
	public static Item hammerItem = null;
	public static Item ironPlateItem = null;
	public static Item liquidDiamond = null;
	public static Item softDiamond = null;
	
	public static void registerItems() {
		upgradeItem = new UpgradeItems(ModConfig.ItemIDs.upgradeItems);
		hammerItem = new HammerItem(ModConfig.ItemIDs.hammerItem);
		ironPlateItem = new IronPlateItem(ModConfig.ItemIDs.ironPlateItem);
		liquidDiamond = new liquidDiamondItem(ModConfig.ItemIDs.liquidDiamondItem);
		softDiamond = new SoftenedDiamondItem(ModConfig.ItemIDs.softDiamondItem);
		
		for (int i = 0; i < UpgradeItems.names.length; i++)
			LanguageRegistry.addName(new ItemStack(upgradeItem, 1, i), UpgradeItems.names[i]);
		LanguageRegistry.addName(new ItemStack(hammerItem), "Hammer");
		LanguageRegistry.addName(new ItemStack(ironPlateItem), "Iron Mass");
		LanguageRegistry.addName(new ItemStack(liquidDiamond), "Softened Diamond Mass");
		LanguageRegistry.addName(new ItemStack(softDiamond), "Softened Diamond");
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
