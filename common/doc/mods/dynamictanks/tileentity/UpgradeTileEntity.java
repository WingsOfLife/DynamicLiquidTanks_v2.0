package doc.mods.dynamictanks.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.common.ModConfig;
import doc.mods.dynamictanks.items.ItemManager;
import doc.mods.dynamictanks.packets.PacketHandler;

public class UpgradeTileEntity extends CountableTileEntity implements IInventory {

	/*
	 * Control Vars
	 */

	protected int upgradeSlots = 6;

	protected int capacityUpgrades = 0;
	protected int storageUpgrades = 0;

	protected ItemStack[] inventory;
	
	/*
	 * Click Sync Vars
	 */
	
	private final int LEFT = 0;
	private final int RIGHT = 1;
	
	private final int[] leftSlots = { 0, 2, 4 };
	private final int[] rightSlots = { 1, 3, 5 };
	
	public UpgradeTileEntity() {
		inventory = new ItemStack[upgradeSlots];
		maxTickCount = 400;
	}

	public UpgradeTileEntity(int slots) {
		inventory = new ItemStack[upgradeSlots];
		upgradeSlots = slots;
		maxTickCount = 400;
	}

	/*
	 * Self
	 */

	public void getInSlots() {
		ItemStack itemStack;
		capacityUpgrades = 0;
		storageUpgrades = 0;
		for (int i = 0; i < inventory.length; i++) {
			itemStack = inventory[i];
			if (itemStack != null) {
				if (itemStack.getItemDamage() == 0 && itemStack.itemID == ItemManager.upgradeItem.itemID)
					capacityUpgrades++;
				if (itemStack.getItemDamage() == 1 && itemStack.itemID == ItemManager.upgradeItem.itemID)
					storageUpgrades++;
			}
				
		}
	}

	public int getUpgradeSlots() {
		return upgradeSlots;
	}

	public void updateController(ControllerTileEntity contTE, int capUpgrade, int storageUpgrade) {
		if (contTE.containedLiquids.size() < (storageUpgrade + 1))
			contTE.addAdditionalTank((storageUpgrade + 1) - contTE.containedLiquids.size());
		if (contTE.containedLiquids.size() < (storageUpgrade + 1))
			//contTE.addAdditionalTank((storageUpgrade + 1) - contTE.containedLiquids.size()); //TODO
		contTE.powerOf = capUpgrade;
	}

	public boolean shareInformation() {
		int currentX = xCoord;
		int currentY = yCoord;
		int currentZ = zCoord;
		int[] loc = new int[3];
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			loc[0] = currentX - dir.offsetX;
			loc[1] = currentY - dir.offsetY;
			loc[2] = currentZ - dir.offsetZ;
			TileEntity controllerTE = worldObj.getBlockTileEntity(loc[0], loc[1], loc[2]);
			if (controllerTE instanceof ControllerTileEntity) {
				updateController((ControllerTileEntity) controllerTE, capacityUpgrades, storageUpgrades);
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Click Sync
	 */

	public void setSlotViaClick(double xClick, double yClick, double zClick, UpgradeTileEntity upgradeTile, EntityPlayer player, ItemStack itemStack) {
		int direction =  MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		switch (direction) {
		case 0:
			if (xClick > .5) //left
				setSlotViaCoord(yClick, LEFT, upgradeTile, itemStack, player);
			if (xClick < .5) //right
				setSlotViaCoord(yClick, RIGHT, upgradeTile, itemStack, player);
			break;
		case 1: 
			if (zClick < .5) //left
				setSlotViaCoord(yClick, LEFT, upgradeTile, itemStack, player);
			if (zClick > .5) //right
				setSlotViaCoord(yClick, RIGHT, upgradeTile, itemStack, player);
			break;
		case 2: 
			if (xClick < .5) //left
				setSlotViaCoord(yClick, LEFT, upgradeTile, itemStack, player);
			if (xClick > .5) //right
				setSlotViaCoord(yClick, RIGHT, upgradeTile, itemStack, player);
			break;
		case 3: 
			if (zClick > .5) //left
				setSlotViaCoord(yClick, LEFT, upgradeTile, itemStack, player);
			if (zClick < .5) //right
				setSlotViaCoord(yClick, RIGHT, upgradeTile, itemStack, player);
			break;
		}
	}

	public void setSlotViaCoord(double yClick, int direction, UpgradeTileEntity upgradeTile, ItemStack itemStack, EntityPlayer player) {

		int[] sideSlotArray = (direction == LEFT) ? leftSlots : rightSlots;

		if (yClick <= 1.00 && yClick >= .66) {
			if (upgradeTile.getStackInSlot(sideSlotArray[0]) != null)
				PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.dropItem, 1, 
						upgradeTile.getStackInSlot(sideSlotArray[0]).itemID, upgradeTile.getStackInSlot(sideSlotArray[0]).getItemDamage(),
						sideSlotArray[0], 0, 0, upgradeTile.xCoord, upgradeTile.yCoord, upgradeTile.zCoord);
			else if (itemStack != null) {
				upgradeTile.setInventorySlotContents(sideSlotArray[0], itemStack); //empty
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
		}
		if (yClick <= .65 && yClick >= .33) {
			if (upgradeTile.getStackInSlot(sideSlotArray[1]) != null)
				PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.dropItem, 1, 
						upgradeTile.getStackInSlot(sideSlotArray[1]).itemID, upgradeTile.getStackInSlot(sideSlotArray[1]).getItemDamage(),
						sideSlotArray[1], 0, 0, upgradeTile.xCoord, upgradeTile.yCoord, upgradeTile.zCoord);
			else if (itemStack != null) {
				upgradeTile.setInventorySlotContents(sideSlotArray[1], itemStack); //empty
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
		}
		if (yClick <= .32 && yClick >= 0) {
			if (upgradeTile.getStackInSlot(sideSlotArray[2]) != null)
				PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.dropItem, 1, 
						upgradeTile.getStackInSlot(sideSlotArray[2]).itemID, upgradeTile.getStackInSlot(sideSlotArray[2]).getItemDamage(),
						sideSlotArray[2], 0, 0, upgradeTile.xCoord, upgradeTile.yCoord, upgradeTile.zCoord);
			else if (itemStack != null) {
				upgradeTile.setInventorySlotContents(sideSlotArray[2], itemStack); //empty
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
		}
	}

	@Override
	public boolean receiveClientEvent(int id, int value) {
		if (worldObj.isRemote) { //only execute on client side
			switch (id) {
			case BlockManager.blockEventIds.insertCapacityUpgrade:
				if (getStackInSlot(value) == null)
					setInventorySlotContents(value, new ItemStack(ItemManager.upgradeItem, 1, 1));
				break;
			}
		}
		return true;
	}
	
	/*
	 * TileEntity
	 */

	@Override
	public void updateEntity() {
		if (worldObj.isRemote) { //client side

		}

		if (!worldObj.isRemote) { //server side
			doCount();

			if (countMet()) {
				getInSlots();
				shareInformation();
				
				System.out.println("UP: " + storageUpgrades);
			}
		}
	}

	/*
	 * IInventory
	 */

	@Override
	public int getSizeInventory() {
		return upgradeSlots;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack itemStack = getStackInSlot(slot);
		if (itemStack != null) {
			if (itemStack.stackSize <= amount)
				setInventorySlotContents(slot, itemStack);
			else {
				itemStack = itemStack.splitStack(amount);
				if (itemStack.stackSize == 0)
					setInventorySlotContents(slot, null);
			}
		}
		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack itemStack = getStackInSlot(slot);
		if (itemStack != null)
			setInventorySlotContents(slot, null);
		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		inventory[slot] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
			itemStack.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInvName() {
		return "dynamictanks.tileEntity.UpgradeContainer";
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;	
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
				entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {		
	}

	@Override
	public void closeChest() {		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}
	
	/*
	 * Syncing Methods
	 */

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		capacityUpgrades = tagCompound.getInteger("capacityUpgrades");
		storageUpgrades = tagCompound.getInteger("storageUpgrades");
		
		NBTTagList tagList = tagCompound.getTagList("Inventory");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("capacityUpgrades", capacityUpgrades);
		tagCompound.setInteger("storageUpgrades", storageUpgrades);
		
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
	}

	@Override
	public Packet getDescriptionPacket () {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket (INetworkManager net, Packet132TileEntityData packet) {
		readFromNBT(packet.customParam1);
	}

}
