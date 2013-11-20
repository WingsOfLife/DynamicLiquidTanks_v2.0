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
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.Fluids.FluidManager;
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

	public static CreativeTabs tabDynamicTanks = new CreativeTabs("tabDynamicTanks") {
		@Override
		public ItemStack getIconItemStack() {
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

		ModConfig.ItemIDs.hammerItem = configFile.getItem("Hammer", ModConfig.ItemIDs.hammerItem).getInt();
		ModConfig.ItemIDs.ironPlateItem = configFile.getItem("Iron Mass", ModConfig.ItemIDs.ironPlateItem).getInt();
		ModConfig.ItemIDs.liquidDiamondItem = configFile.getItem("Softened Diamond Mass", ModConfig.ItemIDs.liquidDiamondItem).getInt();
		ModConfig.ItemIDs.softDiamondItem = configFile.getItem("Softened Diamond", ModConfig.ItemIDs.softDiamondItem).getInt();
		ModConfig.ItemIDs.upgradeItems = configFile.getItem("Upgrades", ModConfig.ItemIDs.upgradeItems).getInt();

		ModConfig.FluidIDs.potion = configFile.getBlock("potion", ModConfig.FluidIDs.potion).getInt();
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

		if(configFile.hasChanged())
			configFile.save();
		
		//MinecraftForge.TERRAIN_GEN_BUS.register(new GenerateFluidPuddles());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerCraftingHandler(new CraftingHandler());
		
		ItemManager.registerItems();		
		BlockManager.registerBlocks();
		FluidManager.registerFluids();

		ItemManager.registerRecipes();
		BlockManager.registerCraftingRecipes();
		FluidManager.registerCraftingRecipes();
		
		BiomeManager.registerBiomes();
		
		proxy.registerTileEntities();
		proxy.setCustomRenders();

		LanguageRegistry.instance().addStringLocalization("multifurnace.container.multifurnace", "Multi-Furnace");

		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		FluidManager.registerBuckets();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new TextureHandler());
	}
}
