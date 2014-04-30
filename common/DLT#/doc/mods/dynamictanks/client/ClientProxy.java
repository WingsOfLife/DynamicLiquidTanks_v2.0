package doc.mods.dynamictanks.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;
import doc.mods.dynamictanks.client.gui.GuiAsset;
import doc.mods.dynamictanks.client.gui.GuiController;
import doc.mods.dynamictanks.client.render.RenderPipe;
import doc.mods.dynamictanks.client.render.RenderStruture;
import doc.mods.dynamictanks.client.render.RenderTank;
import doc.mods.dynamictanks.common.CommonProxy;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.ModifierTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;

public class ClientProxy extends CommonProxy
{
    public static int tankRender;
    public static int renderPass;
    public static int controlPass;
    public static int pipeRender;

    @Override
    public void setCustomRenders()
    {
        tankRender = RenderingRegistry.getNextAvailableRenderId();
        controlPass = RenderingRegistry.getNextAvailableRenderId();
        pipeRender = RenderingRegistry.getNextAvailableRenderId();
        
        RenderingRegistry.registerBlockHandler(new RenderTank());
        RenderingRegistry.registerBlockHandler(new RenderStruture());
        RenderingRegistry.registerBlockHandler(new RenderPipe());
        //ClientRegistry.bindTileEntitySpecialRenderer(ControllerTileEntity.class, new RenderController());
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
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
