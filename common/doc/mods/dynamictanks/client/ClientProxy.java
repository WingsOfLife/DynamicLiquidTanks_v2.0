package doc.mods.dynamictanks.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import doc.mods.dynamictanks.UP.FPCTileEntity_Basic;
import doc.mods.dynamictanks.client.gui.GuiController;
import doc.mods.dynamictanks.client.render.FPCRender;
import doc.mods.dynamictanks.client.render.PotionBucketDamage;
import doc.mods.dynamictanks.client.render.PotionChaliceDamage;
import doc.mods.dynamictanks.client.render.RenderTank;
import doc.mods.dynamictanks.common.CommonProxy;
import doc.mods.dynamictanks.common.ModConfig;
import doc.mods.dynamictanks.items.ItemManager;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;
import doc.mods.dynamictanks.tileentity.UpgradeTileEntity;

public class ClientProxy extends CommonProxy
{
	public static int tankRender;
	public static int ductRender;
	public static int renderPass;

	@Override
	public void setCustomRenders()
	{
		tankRender = RenderingRegistry.getNextAvailableRenderId();
		ductRender = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(new RenderTank());
		RenderingRegistry.registerBlockHandler(new FPCRender());

		ClientRegistry.bindTileEntitySpecialRenderer(FPCTileEntity_Basic.class, new FPCRender());

		if (ModConfig.miscBoolean.enableLiquids == true) {
			MinecraftForgeClient.registerItemRenderer(ItemManager.buckets.itemID, new PotionBucketDamage());
			MinecraftForgeClient.registerItemRenderer(ItemManager.chalice.itemID, new PotionChaliceDamage());
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof UpgradeTileEntity)
		{
			return null;
		}

		if (tileEntity instanceof ControllerTileEntity)
		{
			return new GuiController(player.inventory, (ControllerTileEntity) tileEntity);
		}

		if (tileEntity instanceof TankTileEntity)
		{
			return new GuiController(player.inventory, ((TankTileEntity) tileEntity).getControllerTE());
		}

		return null;
	}
}
