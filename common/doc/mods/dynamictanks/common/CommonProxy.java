package doc.mods.dynamictanks.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import doc.mods.dynamictanks.Fluids.tileentity.ClensingTileEntity;
import doc.mods.dynamictanks.Fluids.tileentity.PotionTileEntity;
import doc.mods.dynamictanks.Fluids.tileentity.TNTTileEntity;
import doc.mods.dynamictanks.UP.GeneratorTileEntity;
import doc.mods.dynamictanks.client.gui.ContainerController;
import doc.mods.dynamictanks.client.gui.ContainerMixer;
import doc.mods.dynamictanks.client.gui.GuiController;
import doc.mods.dynamictanks.client.gui.GuiMixer;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.DuctTileEntity;
import doc.mods.dynamictanks.tileentity.PotionDisperserTileEntity;
import doc.mods.dynamictanks.tileentity.PotionMixerTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;
import doc.mods.dynamictanks.tileentity.UpgradeTileEntity;

public class CommonProxy implements IGuiHandler
{
    public void setCustomRenders() {}

    public void registerTileEntities()
    {
        GameRegistry.registerTileEntity(ControllerTileEntity.class, "dynamictanks.tile.controllerTile");
        GameRegistry.registerTileEntity(TankTileEntity.class, "dynamictanks.tile.tankTile");
        GameRegistry.registerTileEntity(UpgradeTileEntity.class, "dynamictanks.tile.upgradeTile");
        GameRegistry.registerTileEntity(DuctTileEntity.class, "dynamictanks.tile.ductTile");
        GameRegistry.registerTileEntity(PotionTileEntity.class, "dynamictanks.tile.potionTile");
        GameRegistry.registerTileEntity(ClensingTileEntity.class, "dynamictanks.tile.cleansingTile");
        GameRegistry.registerTileEntity(TNTTileEntity.class, "dynamictanks.tile.tntTile");
        //GameRegistry.registerTileEntity(FPCTileEntity_Basic.class, "dynamictanks.tile.fpcTile");
        //GameRegistry.registerTileEntity(FPCTileEntity_Consumer.class, "dynamictanks.tile.fpcTile_RF");
        GameRegistry.registerTileEntity(GeneratorTileEntity.class, "dynamictanks.tile.generatorTile");
        GameRegistry.registerTileEntity(PotionMixerTileEntity.class, "dynamictanks.tile.potionMixerTile");
        GameRegistry.registerTileEntity(PotionDisperserTileEntity.class, "dynamictanks.tile.potionDisperserTile");
    }

    @Override
    public Object getServerGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof UpgradeTileEntity)
        {
            return null;
        }

        if (tileEntity instanceof ControllerTileEntity)
        {
            return new ContainerController(player.inventory, (ControllerTileEntity) tileEntity);
        }

        if (tileEntity instanceof TankTileEntity)
        {
            return new ContainerController(player.inventory, ((TankTileEntity) tileEntity).getControllerTE());
        }

        if (tileEntity instanceof PotionMixerTileEntity)
        {
            return new ContainerMixer(player.inventory, (PotionMixerTileEntity) tileEntity);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z)
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

        if (tileEntity instanceof PotionMixerTileEntity)
        {
            return new GuiMixer(player.inventory, (PotionMixerTileEntity) tileEntity);
        }

        return null;
    }
}
