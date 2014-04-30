package doc.mods.dynamictanks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.common.CommonProxy;
import doc.mods.dynamictanks.common.ModConfig;
import doc.mods.dynamictanks.common.UpgradePlaceHandler;
import doc.mods.dynamictanks.items.ItemManager;
import doc.mods.dynamictanks.multiparts.Content;
import doc.mods.dynamictanks.multiparts.PartEventHandler;
import doc.mods.dynamictanks.packets.PacketHandler;

@Mod(modid = "dynamictanks", name = "Dynamic Liquid Tanks - Basics", version = "1.0")
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
    public void preInit(FMLPreInitializationEvent event) {
    	
        Configuration configFile = new Configuration(event.getSuggestedConfigurationFile());
        configFile.load();
        
        ModConfig.BlockIDs.blockController      = configFile.getBlock("BlockController", ModConfig.BlockIDs.blockController).getInt();
        ModConfig.BlockIDs.blockTank            = configFile.getBlock("BlockTank", ModConfig.BlockIDs.blockTank).getInt();
        ModConfig.BlockIDs.blockModifier         = configFile.getBlock("BlockModifier", ModConfig.BlockIDs.blockModifier).getInt();
        
        ModConfig.ItemIDs.hammerItem 			= configFile.getItem("Hammer", ModConfig.ItemIDs.hammerItem).getInt();
        ModConfig.ItemIDs.ironPlateItem			= configFile.getItem("Iron Mass", ModConfig.ItemIDs.ironPlateItem).getInt();
        ModConfig.ItemIDs.upgradeItems 			= configFile.getItem("Upgrades", ModConfig.ItemIDs.upgradeItems).getInt();
        
        ModConfig.miscBoolean.easyRecipes 		= configFile.get(configFile.CATEGORY_GENERAL, "Easy Recipes", ModConfig.miscBoolean.easyRecipes).getBoolean(false);
        ModConfig.miscBoolean.disableUpgrades 	= configFile.get(configFile.CATEGORY_GENERAL, "Disable Upgrades", ModConfig.miscBoolean.disableUpgrades).getBoolean(false);

        if (configFile.hasChanged())
            configFile.save();
        
        ItemManager.registerItems();
        BlockManager.registerBlocks();

        ItemManager.registerRecipes();
        BlockManager.registerCraftingRecipes();
        
        new Content().init();
        MinecraftForge.EVENT_BUS.register(new PartEventHandler());
        //MinecraftForge.EVENT_BUS.register(new ToolTipHookHandler());
       // PacketCustom.assignHandler(this, new DLT_PartSPH());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
        MinecraftForge.EVENT_BUS.register(new UpgradePlaceHandler());

        proxy.registerTileEntities();
        proxy.setCustomRenders();
        LanguageRegistry.instance().addStringLocalization("itemGroup.tabDynamicTanks", "Dynamic Liquid Tanks 2");
        NetworkRegistry.instance().registerGuiHandler(this, proxy);
    }
}
