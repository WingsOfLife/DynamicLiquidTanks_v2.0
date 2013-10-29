package doc.mods.dynamictanks.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import doc.mods.dynamictanks.client.gui.GuiController;
import doc.mods.dynamictanks.client.gui.ControllerContainer;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;
import doc.mods.dynamictanks.tileentity.UpgradeTileEntity;

public class CommonProxy implements IGuiHandler
{
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(ControllerTileEntity.class, "dynamictanks.tile.controllerTile");
		GameRegistry.registerTileEntity(TankTileEntity.class, "dynamictanks.tile.tankTile");
		GameRegistry.registerTileEntity(UpgradeTileEntity.class, "dynamictanks.tile.upgradeTile");
	}

	@Override
	public Object getServerGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if (tileEntity instanceof UpgradeTileEntity)
			return null;
		
		if (tileEntity instanceof ControllerTileEntity)
			return new ControllerContainer(player.inventory, (UpgradeTileEntity) tileEntity);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if (tileEntity instanceof UpgradeTileEntity)
			return null;
		
		if (tileEntity instanceof ControllerTileEntity)
			return new GuiController(player.inventory, (UpgradeTileEntity) tileEntity);
		
		return null;
	}
}
