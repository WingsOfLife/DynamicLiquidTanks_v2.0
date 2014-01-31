package doc.mods.dynamictanks.Fluids;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FluidOmniPower extends BlockFluidClassic
{
    public FluidOmniPower(int id, Fluid fluid, Material material)
    {
        super(id, fluid, material);
        setUnlocalizedName("dynamictanks.fluids.omniPower");
    }

    @SideOnly(Side.CLIENT)
    protected Icon[] theIcon;

    @Override
    public Icon getIcon(int side, int meta)
    {
        return side != 0 && side != 1 ? this.theIcon[0] : this.theIcon[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        this.theIcon = new Icon[]
        {
            iconRegister.registerIcon("dynamictanks:" + "omniPower_still"),
            iconRegister.registerIcon("dynamictanks:" + "omniPower_flow")
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
    public void onBlockAdded(World world, int x, int y, int z)
    {
        for (int i = 0; i < 2; i++)
        {
            world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z));
        }

        world.createExplosion(null, x, y, z, 10.0f, true);
    }

    /*@Override
    public void onNeighborBlockChange(World par1World, int x, int y, int z, int par5) {
    	ArrayList<int[]> explosionLocations = new ArrayList<int[]>();
    	//par1World.spawnEntityInWorld(new EntityLightningBolt(par1World, x, y, z));
    	//par1World.createExplosion(null, x, y, z, 10.0f, true);

    	if (explosionLocations.isEmpty())
    		for (int i = 0; i <= 3; i++) {
    			int rndX = par1World.rand.nextInt(4);
    			int rndY = par1World.rand.nextInt(4);
    			int rndZ = par1World.rand.nextInt(4);
    			explosionLocations.add(new int[] { rndX, rndY, rndZ });
    			par1World.createExplosion(null, x + rndX, y + rndY, z + rndZ, 2f, true);
    		}

    	for (int i = 0; i <= 2; i++)
    		for (int[] loc : explosionLocations) {
    			int rndX = par1World.rand.nextInt(4);
    			int rndY = par1World.rand.nextInt(4);
    			int rndZ = par1World.rand.nextInt(4);
    			par1World.createExplosion(null, loc[0] + rndX, loc[1] + rndY, loc[2] + rndZ, 2f, true);
    		}
    }*/
}
