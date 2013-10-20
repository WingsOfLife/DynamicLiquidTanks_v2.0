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
import doc.mods.dynamictanks.items.ItemManager;
import doc.mods.dynamictanks.packets.PacketHandler;
import doc.mods.dynamictanks.tileentity.UpgradeTileEntity;

public class BlockUpgrade extends BlockContainer {

	private final int LEFT = 0;
	private final int RIGHT = 1;

	private final int[] leftSlots = { 0, 2, 4 };
	private final int[] rightSlots = { 1, 3, 5 };

	@SideOnly(Side.CLIENT)
	private Icon faceIcon;

	protected BlockUpgrade(int par1) {
		super(par1, Material.iron);
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
		if(side == 0 || side == 1) 
			return blockIcon;
		else if(side != meta)
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

		player.openGui(DynamicLiquidTanksCore.instance, 0, world, x, y, z);
		
		/*if ((heldItem == null || heldItem.itemID == ItemManager.upgradeItem.itemID) && player.rayTrace(200, 1.0F) != null && world.isRemote) {
			double xCoord = player.rayTrace(200, 1.0F).hitVec.xCoord;
			double yCoord = player.rayTrace(200, 1.0F).hitVec.yCoord;
			double zCoord = player.rayTrace(200, 1.0F).hitVec.zCoord;

			double xRound = xCoord - MathHelper.floor_double(xCoord);
			double zRound = zCoord - MathHelper.floor_double(zCoord);
			double yRound = yCoord - MathHelper.floor_double(yCoord);

			setSlotViaClick(xRound, yRound, zRound, upgradeTE, player, heldItem);
			PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.spotClick, 1, 
					heldItem.itemID, heldItem.getItemDamage(), 
					xRound, yRound, zRound, x, y, z);
		}*/

		return true;
	}
	
	/*
	 * Self
	 */

	public void setSlotViaClick(double xClick, double yClick, double zClick, UpgradeTileEntity upgradeTile, EntityPlayer player, ItemStack itemStack) {
		int direction =  MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		switch (direction) {
		case 0:
			if (xClick > 50) { //left
				setSlotViaCoord(yClick, LEFT, upgradeTile, itemStack, player);
			}
			if (xClick < 50) { //right
				setSlotViaCoord(yClick, RIGHT, upgradeTile, itemStack, player);
			}
			break;
		case 1: 
			if (zClick < 50) { //left
				setSlotViaCoord(yClick, LEFT, upgradeTile, itemStack, player);
			}
			if (zClick > 50) { //right
				setSlotViaCoord(yClick, RIGHT, upgradeTile, itemStack, player);
			}
			break;
		case 2: 
			if (xClick < 50) { //left
				setSlotViaCoord(yClick, LEFT, upgradeTile, itemStack, player);
			}
			if (xClick > 50) { //right
				setSlotViaCoord(yClick, RIGHT, upgradeTile, itemStack, player);
			}
			break;
		case 3: 
			if (zClick > 50) { //left
				setSlotViaCoord(yClick, LEFT, upgradeTile, itemStack, player);
			}
			if (zClick < 50) { //right
				setSlotViaCoord(yClick, RIGHT, upgradeTile, itemStack, player);
			}
			break;
		}
	}

	public void setSlotViaCoord(double yClick, int direction, UpgradeTileEntity upgradeTile, ItemStack itemStack, EntityPlayer player) {

		int[] sideSlotArray = direction == LEFT ? leftSlots : rightSlots;

		if (yClick <= 1.00 && yClick >= .66) {
			if (upgradeTile.getStackInSlot(sideSlotArray[0]) != null)
				PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.dropItem, 1, 
						upgradeTile.getStackInSlot(sideSlotArray[0]).itemID, upgradeTile.getStackInSlot(sideSlotArray[0]).getItemDamage(),
						0, 0, 0, upgradeTile.xCoord, upgradeTile.yCoord, upgradeTile.zCoord);
			else if (itemStack != null) {
				upgradeTile.setInventorySlotContents(sideSlotArray[0], itemStack); //empty
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
		}
		if (yClick <= .65 && yClick >= .33) {
			if (upgradeTile.getStackInSlot(sideSlotArray[1]) != null)
				PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.dropItem, 1, 
						upgradeTile.getStackInSlot(sideSlotArray[1]).itemID, upgradeTile.getStackInSlot(sideSlotArray[1]).getItemDamage(),
						0, 0, 0, upgradeTile.xCoord, upgradeTile.yCoord, upgradeTile.zCoord);
			else if (itemStack != null) {
				upgradeTile.setInventorySlotContents(sideSlotArray[1], itemStack); //empty
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
		}
		if (yClick <= .32 && yClick >= 0) {
			if (upgradeTile.getStackInSlot(sideSlotArray[2]) != null)
				PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.dropItem, 1, 
						upgradeTile.getStackInSlot(sideSlotArray[2]).itemID, upgradeTile.getStackInSlot(sideSlotArray[2]).getItemDamage(),
						0, 0, 0, upgradeTile.xCoord, upgradeTile.yCoord, upgradeTile.zCoord);
			else if (itemStack != null) {
				upgradeTile.setInventorySlotContents(sideSlotArray[2], itemStack); //empty
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
		}
	}

}
