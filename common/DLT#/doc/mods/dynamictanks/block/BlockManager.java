package doc.mods.dynamictanks.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.common.ModConfig;
import doc.mods.dynamictanks.items.ControllerItem;
import doc.mods.dynamictanks.items.ItemManager;

public class BlockManager
{
	public class blockEventIds {

		public static final int insertCapacityUpgrade = 1;
	}

	public static BlockTankController BlockTankController = null;
	public static BlockTank BlockTank                     = null;
	public static BlockModifier BlockModifier			  = null;
	public static BlockPiping BlockPiping			      = null;

	public static void registerBlocks()
	{
		BlockTankController = new BlockTankController(ModConfig.BlockIDs.blockController);
		BlockTank           = new BlockTank(ModConfig.BlockIDs.blockTank);
		BlockModifier       = new BlockModifier(ModConfig.BlockIDs.blockModifier);
		BlockPiping 		= new BlockPiping(ModConfig.BlockIDs.blockDuct);
		//BlockUpgrade        = new BlockUpgrade(ModConfig.BlockIDs.blockUpgrade);

		GameRegistry.registerBlock(BlockTankController, ControllerItem.class, "dynamictanks.blocks.blockController");
		GameRegistry.registerBlock(BlockTank, "dynamictanks.blocks.blockTank");
		GameRegistry.registerBlock(BlockModifier, "dynamictanks.blocks.blockModifier");
		GameRegistry.registerBlock(BlockPiping, "dynamictanks.blocks.blockPiping");
		//GameRegistry.registerBlock(BlockUpgrade, "dynamictanks.blocks.blockUpgrade");

		LanguageRegistry.addName(BlockTankController, "Tank Structure");
		LanguageRegistry.addName(BlockTank, "Tank");
		LanguageRegistry.addName(BlockModifier, "Fluid Asset Manager");
		LanguageRegistry.addName(BlockPiping, "Managed Cabling");
		//LanguageRegistry.addName(BlockUpgrade, "Upgrade Module");
	}

	public static void registerCraftingRecipes()
	{
		GameRegistry.addShapedRecipe(new ItemStack(BlockTank, 2), new Object[]
				{
			"GPG", "PGP", "GPG",
			'G', Block.thinGlass,
			'P', Item.ingotIron
				});
		GameRegistry.addShapedRecipe(new ItemStack(BlockTankController), new Object[]
				{
			"BPB", "PLP", "BPB",
			'B', Block.fenceIron,
			'P', Item.ingotIron,
			'L', Block.lever
				});
	}
}

