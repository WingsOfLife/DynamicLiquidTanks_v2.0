package doc.mods.dynamictanks.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.client.ClientProxy;
import doc.mods.dynamictanks.tileentity.DuctTileEntity;

public class BlockDuct extends BlockContainer {

	public BlockDuct(int par1) {
		super(par1, Material.cloth);
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
		setBlockBounds(0.375f, 0.35f, 0.375f, 0.625f, 0.6f, 0.625f);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new DuctTileEntity();
	}
	
	 @Override
	 public boolean renderAsNormalBlock() {
		 return false;
	 }

	 @Override
	 public int getRenderType() {
		 return ClientProxy.ductRender;
	 }

	 @Override
	 public boolean isOpaqueCube() {
		 return false;
	 }

	 @Override
	 public int getRenderBlockPass() {
		 return 1;
	 }

	 @Override
	 public boolean canRenderInPass(int pass) {
		 ClientProxy.renderPass = pass;
		 return true;
	 }
	 
	 /*@Override
	 public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		 return AxisAlignedBB.getAABBPool().getAABB((double) par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, 
				 (double)par2 + this.maxX, (double)par3 + this.maxY, (double)par4 + this.maxZ);
	 }*/

}
