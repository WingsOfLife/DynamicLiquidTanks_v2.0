package doc.mods.dynamictanks.Fluids;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.client.particle.ParticleEffects;
import doc.mods.dynamictanks.helpers.CPotionHelper;
import doc.mods.dynamictanks.helpers.PotionEffectHelper;
import doc.mods.dynamictanks.helpers.StringHelper;

public class FluidPotion extends BlockFluidClassic implements ITileEntityProvider {

	protected int mainMeta = 0;
	//protected int[] existanceMult = { 1, 3, 5, 10, 15, 20, 25, 30, 35, 40 };

	public FluidPotion(int id, Fluid fluid, Material material, String name, int metaAssociation) {
		super(id, fluid, material);
		setUnlocalizedName("dynamictanks.fluids." + StringHelper.removeSpaces(name));
		mainMeta = metaAssociation;

		isBlockContainer = true;
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
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		if (par5Entity instanceof EntityPlayer && tile instanceof PotionTileEntity && isSourceBlock(par1World, par2, par3, par4)) {
			PotionTileEntity potionTile = (PotionTileEntity) tile;

			if (!((EntityPlayer) par5Entity).getActivePotionEffects().isEmpty())
				return;			

			potionTile.removeRndStability();
			PotionEffectHelper.applyPotionEffects((EntityPlayer) par5Entity, mainMeta, CPotionHelper.getDuration(potionTile.getPotency()), false);
		}
	}

	/*@Override
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
	}*/

	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		for (int i = 0; i < 21; i++) 
			ParticleEffects.spawnParticle("coloredSwirl", (double)((float)par2 + par5Random.nextFloat()), (double)((float)par3 + par5Random.nextFloat()), (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D, 0.0D);
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
	public TileEntity createNewTileEntity(World world) {
		return new PotionTileEntity();
	}
}
