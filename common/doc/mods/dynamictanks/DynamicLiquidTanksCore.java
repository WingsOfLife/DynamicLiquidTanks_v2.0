package doc.mods.dynamictanks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.apiMe.PotionRecipe;
import doc.mods.dynamictanks.biome.BiomeManager;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.common.CommonProxy;
import doc.mods.dynamictanks.common.CraftingHandler;
import doc.mods.dynamictanks.common.ModConfig;
import doc.mods.dynamictanks.common.TextureHandler;
import doc.mods.dynamictanks.items.ItemManager;
import doc.mods.dynamictanks.packets.PacketHandler;

@Mod(modid = "dynamictanks", name = "Dynamic Liquid Tanks", version = "1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { "dynamictanks" }, packetHandler = PacketHandler.class)
public class DynamicLiquidTanksCore
{
    @Instance("dynamictanks")
    public static DynamicLiquidTanksCore instance;

    @SidedProxy(clientSide = "doc.mods.dynamictanks.client.ClientProxy", serverSide = "doc.mods.dynamictanks.common.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabDynamicTanks = new CreativeTabs("tabDynamicTanks")
    {
        @Override
        public ItemStack getIconItemStack()
        {
            return new ItemStack(BlockManager.BlockTank);
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Configuration configFile = new Configuration(event.getSuggestedConfigurationFile());
        configFile.load();
        
        ModConfig.BlockIDs.blockController = configFile.getBlock("BlockController", ModConfig.BlockIDs.blockController).getInt();
        ModConfig.BlockIDs.blockTank = configFile.getBlock("BlockTank", ModConfig.BlockIDs.blockTank).getInt();
        ModConfig.BlockIDs.blockUpgrade = configFile.getBlock("BlockUpgrade", ModConfig.BlockIDs.blockUpgrade).getInt();
        ModConfig.BlockIDs.blockDuct = configFile.getBlock("BlockDuct", ModConfig.BlockIDs.blockDuct).getInt();
        ModConfig.BlockIDs.blockHauntedDirt = configFile.getTerrainBlock(configFile.CATEGORY_BLOCK, "BlockMysticDirt", ModConfig.BlockIDs.blockHauntedDirt, "MysticDirt ID must be bellow 256 as it is used in terrain generation.").getInt();
        ModConfig.BlockIDs.blockDangerousFlower = configFile.getBlock("BlockDangerousFlower", ModConfig.BlockIDs.blockDangerousFlower).getInt();
        ModConfig.BlockIDs.blockHauntedSappling = configFile.getBlock("BlockDangerousSappling", ModConfig.BlockIDs.blockHauntedSappling).getInt();
        ModConfig.BlockIDs.blockHauntedPlanks = configFile.getBlock("BlockMysticPlanks", ModConfig.BlockIDs.blockHauntedPlanks).getInt();
        ModConfig.BlockIDs.blockHauntedWood = configFile.getBlock("BlockMysticWood", ModConfig.BlockIDs.blockHauntedWood).getInt();
        ModConfig.BlockIDs.blockHauntedLeaf = configFile.getBlock("BlockMysticLeaf", ModConfig.BlockIDs.blockHauntedLeaf).getInt();
        ModConfig.BlockIDs.blockFPCMJ = configFile.getBlock("FPCMJ", ModConfig.BlockIDs.blockFPCMJ).getInt();
        ModConfig.BlockIDs.blockFPCRF = configFile.getBlock("FPCRF", ModConfig.BlockIDs.blockFPCRF).getInt();
        
        ModConfig.ItemIDs.hammerItem = configFile.getItem("Hammer", ModConfig.ItemIDs.hammerItem).getInt();
        ModConfig.ItemIDs.ironPlateItem = configFile.getItem("Iron Mass", ModConfig.ItemIDs.ironPlateItem).getInt();
        ModConfig.ItemIDs.liquidDiamondItem = configFile.getItem("Softened Diamond Mass", ModConfig.ItemIDs.liquidDiamondItem).getInt();
        ModConfig.ItemIDs.softDiamondItem = configFile.getItem("Softened Diamond", ModConfig.ItemIDs.softDiamondItem).getInt();
        ModConfig.ItemIDs.upgradeItems = configFile.getItem("Upgrades", ModConfig.ItemIDs.upgradeItems).getInt();
        ModConfig.ItemIDs.bucketPotion = configFile.getItem("Buckets", ModConfig.ItemIDs.bucketPotion).getInt();
        ModConfig.ItemIDs.chalciePotion = configFile.getItem("Chalice", ModConfig.ItemIDs.chalciePotion).getInt();
        
        ModConfig.FluidIDs.potion = configFile.getBlock("potion", ModConfig.FluidIDs.potion).getInt();
        ModConfig.FluidIDs.clense = configFile.getBlock("clense", ModConfig.FluidIDs.clense).getInt();
        ModConfig.FluidIDs.tnt = configFile.getBlock("fluidTNT", ModConfig.FluidIDs.tnt).getInt();
        ModConfig.FluidIDs.regen = configFile.getBlock("regen", ModConfig.FluidIDs.regen).getInt();
        ModConfig.FluidIDs.swift = configFile.getBlock("swift", ModConfig.FluidIDs.swift).getInt();
        ModConfig.FluidIDs.fire = configFile.getBlock("fire", ModConfig.FluidIDs.fire).getInt();
        ModConfig.FluidIDs.poison = configFile.getBlock("poison", ModConfig.FluidIDs.poison).getInt();
        ModConfig.FluidIDs.healing = configFile.getBlock("healing", ModConfig.FluidIDs.healing).getInt();
        ModConfig.FluidIDs.night = configFile.getBlock("night", ModConfig.FluidIDs.night).getInt();
        ModConfig.FluidIDs.weak = configFile.getBlock("weak", ModConfig.FluidIDs.weak).getInt();
        ModConfig.FluidIDs.strength = configFile.getBlock("strength", ModConfig.FluidIDs.strength).getInt();
        ModConfig.FluidIDs.slow = configFile.getBlock("slow", ModConfig.FluidIDs.slow).getInt();
        ModConfig.FluidIDs.harming = configFile.getBlock("harming", ModConfig.FluidIDs.harming).getInt();
        ModConfig.FluidIDs.water = configFile.getBlock("water", ModConfig.FluidIDs.water).getInt();
        ModConfig.FluidIDs.invis = configFile.getBlock("invis", ModConfig.FluidIDs.invis).getInt();
        ModConfig.FluidIDs.omniPow = configFile.getBlock("Omni-Power", ModConfig.FluidIDs.omniPow).getInt();
        
        ModConfig.BiomeIDs.potionBiome = configFile.get("Biomes", "PotionBiome", ModConfig.BiomeIDs.potionBiome).getInt();
        
        ModConfig.miscBoolean.terrainGen = configFile.get(configFile.CATEGORY_GENERAL, "terrain gen", ModConfig.miscBoolean.terrainGen).getBoolean(true);
        ModConfig.miscBoolean.enableLiquids = configFile.get(configFile.CATEGORY_GENERAL, "enable fluids", ModConfig.miscBoolean.enableLiquids).getBoolean(true);
        ModConfig.miscBoolean.easyRecipes = configFile.get(configFile.CATEGORY_GENERAL, "Easy Recipes", ModConfig.miscBoolean.easyRecipes).getBoolean(false);
        
        ModConfig.omniPowerSettings.RFPerTick = configFile.get("Omni-Power Settings", "RF Per Tick", 1, "How much RF per tick the FPC should collect.").getInt();
        ModConfig.omniPowerSettings.RFPerMiliB = configFile.get("Omni-Power Settings", "RF Per mB of OmniPower", 10, "How much RF = 1 mB").getInt();
        
        if (configFile.hasChanged())
        {
            configFile.save();
        }

        //MinecraftForge.TERRAIN_GEN_BUS.register(new GenerateFluidPuddles());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        GameRegistry.registerCraftingHandler(new CraftingHandler());
        ItemManager.registerItems();
        BlockManager.registerBlocks();

        if (ModConfig.miscBoolean.enableLiquids)
        {
            FluidManager.registerFluids();
        }

        ItemManager.registerRecipes();
        BlockManager.registerCraftingRecipes();

        if (ModConfig.miscBoolean.enableLiquids)
        {
            FluidManager.registerCraftingRecipes();
        }

        if (ModConfig.miscBoolean.terrainGen)
        {
            BiomeManager.registerBiomes();
        }

        proxy.registerTileEntities();
        proxy.setCustomRenders();
        LanguageRegistry.instance().addStringLocalization("itemGroup.tabDynamicTanks", "Dynamic Liquid Tanks 2");
        NetworkRegistry.instance().registerGuiHandler(this, proxy);
        if (ModConfig.miscBoolean.enableLiquids == true)
        	FluidManager.registerBuckets();
        MinecraftForge.EVENT_BUS.register(new TextureHandler());
        PotionRecipe.addRecipe(PotionRecipe.Types.Healing, PotionRecipe.collisionType.Entity, false, 0, 0);
    }

    @EventHandler
    public void receiveModCommunication(IMCEvent event)
    {
        ImmutableList<IMCMessage> messages = event.getMessages();

        for (IMCMessage currentMessage : messages)
        {
            if (currentMessage.key.equals("addInversionRecipe"))
            {
                NBTTagCompound received = currentMessage.getNBTValue();
                System.out.println("New Thing: " + received.getString("Type"));
            }
        }
    }
}
