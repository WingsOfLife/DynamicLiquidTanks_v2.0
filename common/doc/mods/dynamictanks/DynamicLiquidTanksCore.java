package doc.mods.dynamictanks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.common.CommonProxy;
import doc.mods.dynamictanks.common.CraftingHandler;
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

		ModConfig.FluidIDs.regen = configFile.getBlock("Regen", ModConfig.FluidIDs.regen).getInt();

		if(configFile.hasChanged())
			configFile.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerCraftingHandler(new CraftingHandler());
		
		ItemManager.registerItems();		
		BlockManager.registerBlocks();
		FluidManager.registerFluids();

		ItemManager.registerRecipes();
		BlockManager.registerCraftingRecipes();
		
		proxy.registerTileEntities();
		proxy.setCustomRenders();

		LanguageRegistry.instance().addStringLocalization("multifurnace.container.multifurnace", "Multi-Furnace");

		NetworkRegistry.instance().registerGuiHandler(this, proxy);
	}

	@ForgeSubscribe
	public void postStitch(TextureStitchEvent.Post event) {
		FluidManager.potionFluid.setIcons(FluidManager.potionBlock.getBlockTextureFromSide(0), FluidManager.potionBlock.getBlockTextureFromSide(1));
	}
}
