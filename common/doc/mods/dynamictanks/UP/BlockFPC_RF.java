package doc.mods.dynamictanks.UP;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;

public class BlockFPC_RF extends BlockContainer {

	public BlockFPC_RF(int blockID)
	{
		super(blockID, Material.iron);
		setUnlocalizedName("dynamictanks.blocks.blockFluidPowerCondenser");
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
		setHardness(1.5f);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new FPCTileEntity_RF();
	}

	@Override
	public void onBlockClicked(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer)
	{
		if (par1World.isRemote) {
			FPCTileEntity_RF fpcTile = (FPCTileEntity_RF) par1World.getBlockTileEntity(x, y, z);
			par5EntityPlayer.addChatMessage("Fluid: " + fpcTile.fluidPower.getFluidAmount());
			par5EntityPlayer.addChatMessage("Power: " + fpcTile.storage.getEnergyStored());
		}
	}
}
