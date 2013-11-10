package doc.mods.dynamictanks.block;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.common.ModConfig;
import doc.mods.dynamictanks.items.ControllerItem;
import doc.mods.dynamictanks.items.ItemManager;

public class BlockManager {
	
	public class blockEventIds {
		public static final int insertStorageUpgrade = 0;
		public static final int insertCapacityUpgrade = 1;
	}
	
	public static BlockTankController BlockTankController = null;
	public static BlockTank BlockTank = null;
	public static BlockUpgrade BlockUpgrade = null;
	public static BlockDuct BlockDuct = null;
	
	public static void registerBlocks() {
		BlockTankController = new BlockTankController(ModConfig.BlockIDs.blockController);
		BlockTank = new BlockTank(ModConfig.BlockIDs.blockTank);
		BlockUpgrade = new BlockUpgrade(ModConfig.BlockIDs.blockUpgrade);
		BlockDuct = new BlockDuct(ModConfig.BlockIDs.blockDuct);
		
		GameRegistry.registerBlock(BlockTankController, ControllerItem.class, "dynamictanks.blocks.blockController");
		GameRegistry.registerBlock(BlockTank, "dynamictanks.blocks.blockTank");
		GameRegistry.registerBlock(BlockUpgrade, "dynamictanks.blocks.blockUpgrade");
		GameRegistry.registerBlock(BlockDuct, "dynamictanks.blocks.blockDuct");
		
		LanguageRegistry.addName(BlockTankController, "Tank Controller");
		LanguageRegistry.addName(BlockTank, "Tank");
		LanguageRegistry.addName(BlockUpgrade, "Upgrade Module");
		LanguageRegistry.addName(BlockDuct, "Duct");
	}
	
	public static void registerCraftingRecipes() {
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockTank, 2), new Object[] {
			"GPG", "PGP", "GPG",
			'G', Block.thinGlass,
			'P', ItemManager.ironPlateItem
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockTankController), new Object[] {
			"BPB", "PLP", "BPB",
			'B', Block.fenceIron,
			'P', ItemManager.ironPlateItem,
			'L', Block.lever
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(BlockUpgrade), new Object[] {
			"REG", "ILI", "GER",
			'R', Block.blockRedstone,
			'E', Item.eyeOfEnder,
			'G', Block.glowStone,
			'I', Block.blockNetherQuartz,
			'L', ItemManager.liquidDiamond
		});
	}
}
