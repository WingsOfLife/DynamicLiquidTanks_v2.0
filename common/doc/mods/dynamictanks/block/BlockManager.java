package doc.mods.dynamictanks.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.UP.BlockFPC_RF;
import doc.mods.dynamictanks.common.ModConfig;
import doc.mods.dynamictanks.items.ControllerItem;
import doc.mods.dynamictanks.items.ItemManager;

public class BlockManager
{
    public class blockEventIds
    {
        public static final int insertStorageUpgrade = 0;
        public static final int insertCapacityUpgrade = 1;
    }

    public static BlockTankController BlockTankController = null;
    public static BlockTank BlockTank = null;
    public static BlockUpgrade BlockUpgrade = null;
    public static BlockDuct BlockDuct = null;
    public static BlockHauntedDirt BlockHD = null;
    public static BlockHauntedWood BlockHW = null;
    public static BlockHauntedLeaves BlockHL = null;
    public static BlockHauntedSappling BlockHS = null;
    public static BlockHauntedPlank BlockHP = null;
    public static BlockDangerousFlowers BlockDF = null;
    public static BlockFPC_RF BlockFPC_RF = null;

    public static void registerBlocks()
    {
        BlockTankController = new BlockTankController(ModConfig.BlockIDs.blockController);
        BlockTank = new BlockTank(ModConfig.BlockIDs.blockTank);
        BlockUpgrade = new BlockUpgrade(ModConfig.BlockIDs.blockUpgrade);
        BlockDuct = new BlockDuct(ModConfig.BlockIDs.blockDuct);
        BlockHD = new BlockHauntedDirt(ModConfig.BlockIDs.blockHauntedDirt);
        BlockHW = new BlockHauntedWood(ModConfig.BlockIDs.blockHauntedWood);
        BlockHL = new BlockHauntedLeaves(ModConfig.BlockIDs.blockHauntedLeaf, 3, Material.leaves, false);
        BlockHS = new BlockHauntedSappling(ModConfig.BlockIDs.blockHauntedSappling, 0);
        BlockHP = new BlockHauntedPlank(ModConfig.BlockIDs.blockHauntedPlanks);
        BlockDF = new BlockDangerousFlowers(ModConfig.BlockIDs.blockDangerousFlower, 0, Material.plants);
        BlockFPC_RF = new BlockFPC_RF(ModConfig.BlockIDs.blockFPC);
        
        GameRegistry.registerBlock(BlockTankController, ControllerItem.class, "dynamictanks.blocks.blockController");
        GameRegistry.registerBlock(BlockTank, "dynamictanks.blocks.blockTank");
        GameRegistry.registerBlock(BlockUpgrade, "dynamictanks.blocks.blockUpgrade");
        GameRegistry.registerBlock(BlockDuct, "dynamictanks.blocks.blockDuct");
        GameRegistry.registerBlock(BlockHD, "dynamictanks.blocks.blockHauntedDirt");
        GameRegistry.registerBlock(BlockHW, "dynamictanks.blocks.blockHauntedWood");
        GameRegistry.registerBlock(BlockHL, "dynamictanks.blocks.blockHauntedLeaf");
        GameRegistry.registerBlock(BlockHS, "dynamictanks.blocks.blockHauntedSappling");
        GameRegistry.registerBlock(BlockHP, "dynamictanks.blocks.blockHauntedPlank");
        GameRegistry.registerBlock(BlockDF, "dynamictanks.blocks.blockHauntedFlower");
        GameRegistry.registerBlock(BlockFPC_RF, "dynamictanks.blocks.blockFluidPowerCondenser");
        
        LanguageRegistry.addName(BlockTankController, "Tank Controller");
        LanguageRegistry.addName(BlockTank, "Tank");
        LanguageRegistry.addName(BlockUpgrade, "Upgrade Module");
        LanguageRegistry.addName(BlockDuct, "Duct W.I.P");
        LanguageRegistry.addName(BlockHD, "Mystic Mud");
        LanguageRegistry.addName(BlockHW, "Mystic Wood");
        LanguageRegistry.addName(BlockHL, "Mystic Leaf");
        LanguageRegistry.addName(BlockHS, "Mystic Sapling");
        LanguageRegistry.addName(BlockHP, "Mystic Plank");
        LanguageRegistry.addName(BlockDF, "Mystic Flower");        
        LanguageRegistry.addName(BlockFPC_RF, "F.P.C. (Redstone Flux)");
        
        OreDictionary.registerOre("logWood", new ItemStack(BlockHW));
        OreDictionary.registerOre("saplingTree", new ItemStack(BlockHS));
        OreDictionary.registerOre("plankWood", new ItemStack(BlockHP));
    }

    public static void registerCraftingRecipes()
    {
        GameRegistry.addShapedRecipe(new ItemStack(BlockTank, 2), new Object[]
                                     {
                                         "GPG", "PGP", "GPG",
                                         'G', Block.thinGlass,
                                         'P', ItemManager.ironPlateItem
                                     });
        GameRegistry.addShapedRecipe(new ItemStack(BlockTankController), new Object[]
                                     {
                                         "BPB", "PLP", "BPB",
                                         'B', Block.fenceIron,
                                         'P', ItemManager.ironPlateItem,
                                         'L', Block.lever
                                     });
        GameRegistry.addShapedRecipe(new ItemStack(BlockUpgrade), new Object[]
                                     {
                                         "REG", "ILI", "GER",
                                         'R', Block.blockRedstone,
                                         'E', Item.eyeOfEnder,
                                         'G', Block.glowStone,
                                         'I', Block.blockNetherQuartz,
                                         'L', ItemManager.liquidDiamond
                                     });
        GameRegistry.addShapedRecipe(new ItemStack(BlockDuct, 4), new Object[]
                                     {
                                         "PBP", "RRR", "PBP",
                                         'P', Block.thinGlass,
                                         'B', Block.fenceIron,
                                         'R', Item.redstone
                                     });
        GameRegistry.addShapelessRecipe(new ItemStack(BlockHP, 4), BlockHW);
    }
}
