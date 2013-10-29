package doc.mods.dynamictanks.block;


import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.common.ModConfig;

public class BlockManager {
	
	public class blockEventIds {
		public static final int insertStorageUpgrade = 0;
		public static final int insertCapacityUpgrade = 1;
	}
	
	public static BlockTankController BlockTankController = null;
	public static BlockTank BlockTank = null;
	public static BlockUpgrade BlockUpgrade = null;
	
	public static void registerBlocks()
	{
		BlockTankController = new BlockTankController(ModConfig.BlockIDs.blockController);
		BlockTank = new BlockTank(ModConfig.BlockIDs.blockTank);
		BlockUpgrade = new BlockUpgrade(ModConfig.BlockIDs.blockUpgrade);
		
		GameRegistry.registerBlock(BlockTankController, "dynamictanks.blocks.blockController");
		GameRegistry.registerBlock(BlockTank, "dynamictanks.blocks.blockTank");
		GameRegistry.registerBlock(BlockUpgrade, "dynamictanks.blocks.blockUpgrade");
		
		LanguageRegistry.addName(BlockTankController, "Tank Controller");
		LanguageRegistry.addName(BlockTank, "Tank");
		LanguageRegistry.addName(BlockUpgrade, "Upgrade Module");
	}
	
	public static void registerCraftingRecipes()
	{
	}
}
