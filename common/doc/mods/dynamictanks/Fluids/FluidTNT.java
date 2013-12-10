package doc.mods.dynamictanks.Fluids;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.Fluids.tileentity.TNTTileEntity;
import doc.mods.dynamictanks.helpers.StringHelper;

public class FluidTNT extends BlockFluidClassic implements ITileEntityProvider
{
    public Random par5Random = new Random();

    public FluidTNT(int id, Fluid fluid, Material material, String name)
    {
        super(id, fluid, material);
        setUnlocalizedName("dynamictanks.fluids." + StringHelper.removeSpaces(name));
        setQuantaPerBlock(2);
        //setBurnProperties(blockID, 200, 200);
        isBlockContainer = true;
    }

    @SideOnly(Side.CLIENT)
    protected Icon[] theIcon;

    @Override
    public Icon getIcon(int side, int meta)
    {
        /*		if (meta == 0)
        	return theIcon[0];
        if (activated)
        	return theIcon[1];*/
        return side != 0 && side != 1 ? this.theIcon[0] : this.theIcon[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        this.theIcon = new Icon[]
        {
            iconRegister.registerIcon("dynamictanks:" + "tnt_still"),
            iconRegister.registerIcon("dynamictanks:" + "tnt_flow")
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

    @Override
    public void onEntityCollidedWithBlock(World par1World, int x, int y, int z, Entity par5Entity)
    {
        if (par5Entity.isBurning())
        {
            TNTTileEntity tntTile = (TNTTileEntity) par1World.getBlockTileEntity(x, y, z);

            if (!tntTile.getPrimed())
            {
                tntTile.setPrimed(true);
            }
        }

        if (par5Entity instanceof EntityItem && isSourceBlock(par1World, x, y, z))
        {
            EntityItem collide = (EntityItem) par5Entity;

            if (collide.getEntityItem().itemID == Item.gunpowder.itemID)
            {
                TNTTileEntity tntTile = (TNTTileEntity) par1World.getBlockTileEntity(x, y, z);

                if (par1World.isRemote)
                {
                    float f = (float) x + par5Random.nextFloat() * 0.1F;
                    float f1 = (float) y + par5Random.nextFloat() + 0.5F;
                    float f2 = (float) z + par5Random.nextFloat();
                    par1World.spawnParticle("cloud", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
                }

                if (!tntTile.getPrimed())
                {
                    collide.setDead();
                }

                tntTile.setPrimed(true);
                par1World.markBlockForRenderUpdate(x, y, z);
            }
        }
    }

    /*@SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1World, int par2, int par3, int par4, int side) {
    	boolean isPrimed = ((TNTTileEntity) par1World.getBlockTileEntity(par2, par3, par4)).getPrimed();
    	int meta = par1World.getBlockMetadata(par2, par3, par4);
    	if (meta == 0)
    		return theIcon[0];
    	if (isPrimed)
    		return theIcon[1];
    	return side != 0 && side != 1 ? this.theIcon[0] : this.theIcon[0];
    } */

    @Override
    public void randomDisplayTick(World par1World, int x, int y, int z, Random par5Random)
    {
        boolean isPrimed = ((TNTTileEntity) par1World.getBlockTileEntity(x, y, z)).getPrimed();

        for (int i = 0; i < 3; i++)
            if (isPrimed)
            {
                float f = (float) x + par5Random.nextFloat() - 0.5F * 0.1F;
                float f1 = (float) y + par5Random.nextFloat() + 0.5F;
                float f2 = (float) z + par5Random.nextFloat() - 0.5F;
                par1World.spawnParticle("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
            }
    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
    {
        boolean isPrimed = ((TNTTileEntity) world.getBlockTileEntity(x, y, z)).getPrimed();

        if (world.getBlockMetadata(x, y, z) == 0 && !isPrimed)
        {
            world.setBlock(x, y, z, this.blockID);
            return;
        }

        super.onBlockExploded(world, x, y, z, explosion);
    }

    /*
     * TileEntity Stuff
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
        return new TNTTileEntity();
    }
}
