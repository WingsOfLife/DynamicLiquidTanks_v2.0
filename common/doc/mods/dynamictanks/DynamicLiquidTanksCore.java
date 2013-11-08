package doc.mods.dynamictanks;

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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

	private static Fluid dltFluidPotion;
	public static Fluid fluidPotion;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration configFile = new Configuration(event.getSuggestedConfigurationFile());
		configFile.load();

		ModConfig.BlockIDs.blockController = configFile.getBlock("BlockController", ModConfig.BlockIDs.blockController).getInt();

		if(configFile.hasChanged())
			configFile.save();

		//Fluid
		dltFluidPotion = new Fluid("potion").setDensity(800).setViscosity(1500);
		FluidRegistry.registerFluid(dltFluidPotion);

		fluidPotion = FluidRegistry.getFluid("potion");
		fluidPotion.setBlockID(ModConfig.FluidIDs.fluidPotion);
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
	@SideOnly(Side.CLIENT)
	public void textureHook(TextureStitchEvent.Post event) {
		if (event.map.textureType == 0) {
			dltFluidPotion.setIcons(FluidManager.fluidPotion.getBlockTextureFromSide(1));
			fluidPotion.setIcons(FluidManager.fluidPotion.getBlockTextureFromSide(1));
		}
	}
}
