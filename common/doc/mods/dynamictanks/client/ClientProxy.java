package doc.mods.dynamictanks.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;
import doc.mods.dynamictanks.client.gui.GuiController;
import doc.mods.dynamictanks.client.render.RenderTank;
import doc.mods.dynamictanks.common.CommonProxy;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;
import doc.mods.dynamictanks.tileentity.UpgradeTileEntity;

public class ClientProxy extends CommonProxy {
	
	public static int tankRender;
	public static int renderPass;
	
	@Override
	public void setCustomRenders() {
		tankRender = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(new RenderTank());
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof UpgradeTileEntity)
			return null;
		
		if (tileEntity instanceof ControllerTileEntity)
			return new GuiController(player.inventory, (ControllerTileEntity) tileEntity);

		if (tileEntity instanceof TankTileEntity)
			return new GuiController(player.inventory, ((TankTileEntity) tileEntity).getControllerTE());
		
		return null;
	}
}
