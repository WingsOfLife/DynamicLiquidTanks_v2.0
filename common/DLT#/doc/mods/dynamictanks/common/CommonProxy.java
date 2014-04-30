package doc.mods.dynamictanks.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import doc.mods.dynamictanks.client.gui.ContainerAsset;
import doc.mods.dynamictanks.client.gui.ContainerController;
import doc.mods.dynamictanks.client.gui.GuiAsset;
import doc.mods.dynamictanks.client.gui.GuiController;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.DuctTileEntity;
import doc.mods.dynamictanks.tileentity.ModifierTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;

public class CommonProxy implements IGuiHandler
{
    public void setCustomRenders() {}

    public void registerTileEntities()
    {
        GameRegistry.registerTileEntity(ControllerTileEntity.class, "dynamictanks.tile.controllerTile");
        GameRegistry.registerTileEntity(TankTileEntity.class, "dynamictanks.tile.tankTile");
        GameRegistry.registerTileEntity(DuctTileEntity.class, "dynamictanks.tile.ductTile");
        GameRegistry.registerTileEntity(ModifierTileEntity.class, "dynamictanks.tile.assetTile");
    }

    @Override
    public Object getServerGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof ControllerTileEntity)
        {
            return new ContainerController(player.inventory, (ControllerTileEntity) tileEntity);
        }

        if (tileEntity instanceof TankTileEntity)
        {
            return new ContainerController(player.inventory, ((TankTileEntity) tileEntity).getControllerTE());
        }
        
        if (tileEntity instanceof ModifierTileEntity)
        {
            return new ContainerAsset(player.inventory, (ModifierTileEntity) tileEntity);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof ControllerTileEntity)
        {
            return new GuiController(player.inventory, (ControllerTileEntity) tileEntity);
        }

        if (tileEntity instanceof TankTileEntity)
        {
            return new GuiController(player.inventory, ((TankTileEntity) tileEntity).getControllerTE());
        }
        
        if (tileEntity instanceof ModifierTileEntity)
        {
            return new GuiAsset(player.inventory, (ModifierTileEntity) tileEntity);
        }

        return null;
    }
}
