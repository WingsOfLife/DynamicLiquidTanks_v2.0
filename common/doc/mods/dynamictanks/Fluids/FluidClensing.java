package doc.mods.dynamictanks.Fluids;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.Fluids.tileentity.ClensingTileEntity;

public class FluidClensing extends BlockFluidClassic implements ITileEntityProvider
{
    public FluidClensing(int id, Fluid fluid, Material material)
    {
        super(id, fluid, material);
        setUnlocalizedName("dynamictanks.fluids.clensingWater");
        setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
    }

    @SideOnly(Side.CLIENT)
    protected Icon[] theIcon;

    @Override
    public Icon getIcon(int side, int meta)
    {
        return side != 0 && side != 1 ? this.theIcon[0] : this.theIcon[1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        this.theIcon = new Icon[]
        {
            iconRegister.registerIcon("dynamictanks:" + "clense_still"),
            iconRegister.registerIcon("dynamictanks:" + "clense_flow")
        };
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlockMaterial(x,  y,  z).isLiquid())
        {
            return false;
        }

        return super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z)
    {
        if (world.getBlockMaterial(x,  y,  z).isLiquid())
        {
            return false;
        }

        return super.displaceIfPossible(world, x, y, z);
    }

    /*
     * TileEntity Methods
     */

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }

    @Override
    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new ClensingTileEntity();
    }
}
