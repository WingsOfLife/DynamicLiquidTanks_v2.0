package doc.mods.dynamictanks;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.common.CommonProxy;
import doc.mods.dynamictanks.common.ModConfig;
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

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration configFile = new Configuration(event.getSuggestedConfigurationFile());
		configFile.load();

		ModConfig.BlockIDs.blockController = configFile.getBlock("BlockController", ModConfig.BlockIDs.blockController).getInt();

		if(configFile.hasChanged())
			configFile.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		BlockManager.registerBlocks();
		BlockManager.registerCraftingRecipes();
		
		ItemManager.registerItems();
		ItemManager.registerRecipes();

		proxy.registerTileEntities();

		LanguageRegistry.instance().addStringLocalization("multifurnace.container.multifurnace", "Multi-Furnace");

		NetworkRegistry.instance().registerGuiHandler(this, proxy);
	}
}
