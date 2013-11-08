package doc.mods.dynamictanks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.helpers.BlockHelper;
import doc.mods.dynamictanks.items.ItemManager;
import doc.mods.dynamictanks.packets.PacketHandler;
import doc.mods.dynamictanks.tileentity.UpgradeTileEntity;

public class BlockUpgrade extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon faceIcon;

	protected BlockUpgrade(int par1) {
		super(par1, Material.iron);
		setHardness(2.0F);
		setUnlocalizedName("Upgrade Module");		
	}

	/* 
	 * Icons
	 */

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconReg) {
		blockIcon = iconReg.registerIcon("dynamictanks:" + "Controller_Sides");
		faceIcon = iconReg.registerIcon("dynamictanks:" + "Upgrade_Front");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (meta == 0 && side == 4)
			return faceIcon;
		if(side == 0 || side == 1) 
			return blockIcon;
		else if (side != meta)
			return blockIcon;
		else 
			return faceIcon;

	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
	}

	private void setDefaultDirection(World world, int x, int y, int z) {
		if(!world.isRemote) {
			int zNeg = world.getBlockId(x, y, z - 1);
			int zPos = world.getBlockId(x, y, z + 1);
			int xNeg = world.getBlockId(x - 1, y, z);
			int xPos = world.getBlockId(x + 1, y, z);
			byte meta = 3;

			if(Block.opaqueCubeLookup[xNeg] && !Block.opaqueCubeLookup[xPos]) meta = 5;
			if(Block.opaqueCubeLookup[xPos] && !Block.opaqueCubeLookup[xNeg]) meta = 4;
			if(Block.opaqueCubeLookup[zNeg] && !Block.opaqueCubeLookup[zPos]) meta = 3;
			if(Block.opaqueCubeLookup[zPos] && !Block.opaqueCubeLookup[zNeg]) meta = 2;

			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
		int rotation = MathHelper.floor_double((double)(entity.rotationYaw * 4F / 360F) + 0.5D) & 3;

		if(rotation == 0)
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);

		if(rotation == 1)
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);

		if(rotation == 2)
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);

		if(rotation == 3)
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	/*
	 * Misc
	 */

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new UpgradeTileEntity();
	}

	@Override
	public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
		ItemStack heldItem = player.inventory.getCurrentItem();
		UpgradeTileEntity upgradeTE = (UpgradeTileEntity) world.getBlockTileEntity(x, y, z);
		
		if ((heldItem == null || heldItem.itemID == ItemManager.upgradeItem.itemID) && player.rayTrace(200, 1.0F) != null && world.isRemote) {
			double xCoord = player.rayTrace(200, 1.0F).hitVec.xCoord;
			double yCoord = player.rayTrace(200, 1.0F).hitVec.yCoord;
			double zCoord = player.rayTrace(200, 1.0F).hitVec.zCoord;

			double xRound = xCoord - MathHelper.floor_double(xCoord);
			double zRound = zCoord - MathHelper.floor_double(zCoord);
			double yRound = yCoord - MathHelper.floor_double(yCoord);

			PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.spotClick, 1, 
					heldItem != null ? heldItem.itemID : -1, heldItem != null ? heldItem.getItemDamage() : -1, 
					xRound, yRound, zRound, x, y, z);
		}
		return true;
	}
	
	@Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		BlockHelper.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);;
	}
}
