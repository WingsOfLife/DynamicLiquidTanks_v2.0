package doc.mods.dynamictanks.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.client.ClientProxy;
import doc.mods.dynamictanks.tileentity.DuctTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;

public class BlockDuct extends BlockContainer {

	public BlockDuct(int par1) {
		super(par1, Material.cloth);
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
		setHardness(0.5F);
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

	@SideOnly(Side.CLIENT)
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

	@Override
	public void onBlockClicked(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer) {
		DuctTileEntity duct = (DuctTileEntity) par1World.getBlockTileEntity(x, y, z);
		if (par5EntityPlayer.isSneaking()) {
			if (duct.extractor)
				duct.extractor = false;
			else if (!duct.extractor)
				duct.extractor = true;

			if (!par1World.isRemote) par5EntityPlayer.addChatMessage("Duct at: " + duct.xCoord + ", " + duct.yCoord + " , " + duct.zCoord + " extract mode is: " + duct.extractor);
		}
	}

}
