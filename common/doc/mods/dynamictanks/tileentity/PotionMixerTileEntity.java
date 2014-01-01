package doc.mods.dynamictanks.tileentity;

import java.awt.Color;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.potion.PotionHelper;

import org.apache.commons.lang3.ArrayUtils;

import doc.mods.dynamictanks.client.particle.ParticleEffects;
import doc.mods.dynamictanks.helpers.ItemHelper;
import doc.mods.dynamictanks.items.ItemManager;

public class PotionMixerTileEntity extends CountableTileEntity implements IInventory, ISidedInventory {

	protected int numSlots = 4;

	protected ItemStack[] inventory;

	public PotionMixerTileEntity() {
		inventory = new ItemStack[numSlots];
	}

	@Override
	public void updateEntity() {
		/*if (worldObj.isRemote) {
			int color = PotionHelper.calcPotionLiquidColor(PotionHelper.getPotionEffects(8204, false));
			String nextColor = ParticleEffects.int2Rgb(color);
			Color toConvert = ParticleEffects.hex2Rgb(nextColor);
			for (int i = 0; i < 35; i++) {
				ParticleEffects.spawnParticle("coloredSmoke", (double)((float)xCoord + worldObj.rand.nextInt(5) + worldObj.rand.nextFloat()), (double)((float)yCoord + worldObj.rand.nextInt(2) - worldObj.rand.nextFloat()), (double)((float)zCoord + worldObj.rand.nextInt(5) + worldObj.rand.nextFloat()), 0.0D, 0.02D, 0.0D, toConvert.getRed(), toConvert.getGreen(), toConvert.getBlue());
				ParticleEffects.spawnParticle("coloredSmoke", (double)((float)xCoord - worldObj.rand.nextInt(5) + worldObj.rand.nextFloat()), (double)((float)yCoord - worldObj.rand.nextInt(2) + worldObj.rand.nextFloat()), (double)((float)zCoord - worldObj.rand.nextInt(5) + worldObj.rand.nextFloat()), 0.0D, 0.02D, 0.0D, toConvert.getRed(), toConvert.getGreen(), toConvert.getBlue());
				ParticleEffects.spawnParticle("coloredSmoke", (double)((float)xCoord + worldObj.rand.nextInt(5) + worldObj.rand.nextFloat()), (double)((float)yCoord - worldObj.rand.nextInt(2) + worldObj.rand.nextFloat()), (double)((float)zCoord - worldObj.rand.nextInt(5) + worldObj.rand.nextFloat()), 0.0D, 0.02D, 0.0D, toConvert.getRed(), toConvert.getGreen(), toConvert.getBlue());
				ParticleEffects.spawnParticle("coloredSmoke", (double)((float)xCoord - worldObj.rand.nextInt(5) + worldObj.rand.nextFloat()), (double)((float)yCoord + worldObj.rand.nextInt(2) - worldObj.rand.nextFloat()), (double)((float)zCoord + worldObj.rand.nextInt(5) + worldObj.rand.nextFloat()), 0.0D, 0.02D, 0.0D, toConvert.getRed(), toConvert.getGreen(), toConvert.getBlue());
			}
			
		}*/
		
		if (!worldObj.isRemote) {
			if (getStackInSlot(2) != null && getStackInSlot(2).itemID == Item.netherStalkSeeds.itemID) {

				if (getStackInSlot(0) == null || getStackInSlot(1) == null)
					return;

				setInventorySlotContents(2, null);

				NBTTagCompound nbt = new NBTTagCompound();
				ItemStack outputItem = new ItemStack(ItemManager.mixedPotion);
				int[] appendTo = new int[2];

				if (getStackInSlot(0).itemID == ItemManager.mixedPotion.itemID)
					appendTo = ArrayUtils.addAll(appendTo, getStackInSlot(0).stackTagCompound.getIntArray("PotionEffects"));
				else
					appendTo[0] = getStackInSlot(0).getItemDamage();
				if (getStackInSlot(1).itemID == ItemManager.mixedPotion.itemID)
					appendTo = ArrayUtils.addAll(appendTo, getStackInSlot(1).stackTagCompound.getIntArray("PotionEffects"));
				else
					appendTo[1] = getStackInSlot(1).getItemDamage();

				nbt.setIntArray("PotionEffects", appendTo);
				outputItem.setTagCompound(nbt);

				setInventorySlotContents(0, null);
				setInventorySlotContents(1, null);

				ItemHelper.spawnItem(outputItem, worldObj, xCoord, yCoord, zCoord);
			}
		}
	}

	/*
	 * IInventory
	 */

	@Override
	public int getSizeInventory()
	{
		return numSlots;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack itemStack = getStackInSlot(slot);

		if (itemStack != null)
		{
			if (itemStack.stackSize <= amount)
			{
				setInventorySlotContents(slot, itemStack);
			}
			else
			{
				itemStack = itemStack.splitStack(amount);

				if (itemStack.stackSize == 0)
				{
					setInventorySlotContents(slot, null);
				}
			}
		}

		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack itemStack = getStackInSlot(slot);

		if (itemStack != null)
		{
			setInventorySlotContents(slot, null);
		}

		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		inventory[slot] = itemStack;

		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName()
	{
		return "dynamictanks.tileEntity.UpgradeContainer";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
				entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}

	/*
	 * Syncing Methods
	 */

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		NBTTagList tagList = tagCompound.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length)
			{
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < inventory.length; i++)
		{
			ItemStack stack = inventory[i];

			if (stack != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}

		tagCompound.setTag("Inventory", itemList);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
	{
		readFromNBT(packet.data);
	}

	/*
	 * ISided
	 */
	
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[] { 0, 1, 2 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

}
