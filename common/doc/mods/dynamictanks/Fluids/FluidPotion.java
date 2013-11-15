package doc.mods.dynamictanks.Fluids;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.client.particle.ParticleEffects;
import doc.mods.dynamictanks.helpers.PotionEffectHelper;
import doc.mods.dynamictanks.helpers.PotionIdHelper;
import doc.mods.dynamictanks.helpers.StringHelper;

public class FluidPotion extends BlockFluidClassic {

	protected int mainMeta = 0;

	public FluidPotion(int id, Fluid fluid, Material material, String name, int metaAssociation) {
		super(id, fluid, material);
		setUnlocalizedName("dynamictanks.fluids." + StringHelper.removeSpaces(name));
		//setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
		mainMeta = metaAssociation;
	}

	@SideOnly(Side.CLIENT)
	protected Icon[] theIcon;

	@Override
	public Icon getIcon(int side, int meta) {
		return side != 0 && side != 1 ? this.theIcon[0] : this.theIcon[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.theIcon = new Icon[] { 
				iconRegister.registerIcon("dynamictanks:" + "potion_still"),
				iconRegister.registerIcon("dynamictanks:" + "potion_flowing") 
		};
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if (world.getBlockMaterial(x,  y,  z).isLiquid()) return false;
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if (world.getBlockMaterial(x,  y,  z).isLiquid()) return false;
		return super.displaceIfPossible(world, x, y, z);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		if (par5Entity instanceof EntityPlayer)
			PotionEffectHelper.applyPotionEffects((EntityPlayer) par5Entity, mainMeta, false);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0)
			super.updateTick(world, x, y, z, rand);

		int rndValue = rand.nextInt(100);
		if (isSourceBlock(world, x, y, z)) {
			if (rndValue <= 30)
				if (meta == 0)
					this.drain(world, x, y, z, true);
		}
	}
	
	@Override
    public boolean canDrain(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0)
			return isSourceBlock(world, x, y, z);
		return false;
    }

	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		ParticleEffects.spawnParticle("coloredSwirl", (double)((float)par2 + par5Random.nextFloat()), (double)((float)par3 + par5Random.nextFloat()), (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
		//par1World.spawnParticle("suspended", (double)((float)par2 + par5Random.nextFloat()), (double)((float)par3 + par5Random.nextFloat()), (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
	}
}
