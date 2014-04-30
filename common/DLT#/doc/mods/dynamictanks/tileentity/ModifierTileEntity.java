package doc.mods.dynamictanks.tileentity;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import doc.mods.dynamictanks.helpers.ArrayHelper;
import doc.mods.dynamictanks.helpers.FluidHandlerPart;
import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.helpers.grapher.BlockPosition;
import doc.mods.dynamictanks.helpers.grapher.QueueSearch;

public class ModifierTileEntity extends TickingTileEntity implements IInventory, IEnergyHandler, IFluidHandler {

	public ArrayList<FluidHandlerPart> controlledAssets = new ArrayList<FluidHandlerPart>();
	private ItemStack[] inventory;


	private EnergyStorage energyStored;

	private int capacity     = 25000;
	private int maxReceive   = 40;
	private int maxExtract   = 10;

	public int RFPerTick       = 10;
	private int LavaPerTick    = 1;
	private int drainIndex     = 0;   
	
	private boolean debug      = false;
	private boolean mergeCommon = false;
	private boolean everyOtherTick = false;

	public ModifierTileEntity() {

		this.maxTickCount = 150;
		inventory         = new ItemStack[37];
		energyStored      = new EnergyStorage(capacity, maxReceive, maxExtract);
		//energyStored.setEnergyStored(25000);
	}

	public void updateEntity() {

		if (!debug)
			if (!isRunnable()) {

				ArrayHelper.nullArray(inventory, 0, 31);
				return;
			}

		doCount();

		if (countMet())
			rescan();
	}

	private ArrayList<FluidHandlerPart> mergeCommon(ArrayList<FluidHandlerPart> toMerge) {

		ArrayList<FluidHandlerPart> newOut = new ArrayList<FluidHandlerPart>();

		if (!toMerge.isEmpty())
			newOut.add(toMerge.get(0));

		while (toMerge.iterator().hasNext()) {
			while (newOut.iterator().hasNext()) {

				FluidHandlerPart iteratorPart = toMerge.iterator().next();
				FluidHandlerPart fhPart       = newOut.iterator().next();

				if (FluidHelper.getFluidFromHandler(fhPart.getFluidHandler()).isFluidEqual(FluidHelper.getFluidFromHandler(iteratorPart.getFluidHandler()))) {

					FluidHelper.setAmntForHandler(iteratorPart.getFluidHandler(), FluidHelper.combinedAmount(fhPart, iteratorPart));
					newOut.add(iteratorPart);
					toMerge.remove(iteratorPart);
				} else if (!newOut.contains(fhPart))
					newOut.add(fhPart);
			}			
		}

		return newOut;
	}

	public boolean isRunnable() {

		if (energyStored.getEnergyStored() - RFPerTick >= 0) {

			this.extractEnergy(ForgeDirection.UP, 15, false);
			return true;
		}

		return canRunOnFuel();
	}

	private boolean canRunOnFuel() {

		for (ForgeDirection fDir : ForgeDirection.VALID_DIRECTIONS) {

			int x = xCoord + fDir.offsetX;
			int y = yCoord + fDir.offsetY;
			int z = zCoord + fDir.offsetZ;

			if (worldObj.getBlockTileEntity(x, y, z) instanceof IFluidHandler)
				if (FluidHelper.fluidHandlerFilledWithFuel(((IFluidHandler) worldObj.getBlockTileEntity(x, y, z))) && ((IFluidHandler) worldObj.getBlockTileEntity(x, y, z)).drain(ForgeDirection.UNKNOWN, LavaPerTick, false).amount >= 0) {

					if (everyOtherTick) {
					
						((IFluidHandler) worldObj.getBlockTileEntity(x, y, z)).drain(ForgeDirection.UNKNOWN, LavaPerTick, true);
						everyOtherTick = !everyOtherTick;
					} else 
						everyOtherTick = !everyOtherTick;
					return true;
				}
		}

		return false;
	}

	public boolean isEnabled() {

		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0;
	}

	public void rescan() {

		controlledAssets.clear();
		ArrayHelper.nullArray(inventory, 0, 31);
		
		controlledAssets = QueueSearch.queueSearch(worldObj, new BlockPosition(xCoord, yCoord, zCoord), controlledAssets);
		
		for (int i = 0; i < controlledAssets.size(); i++)
			if (FluidHelper.fluidHandlerFilled(controlledAssets.get(i).getFluidHandler()))
				inventory[i] = FluidContainerRegistry.fillFluidContainer(FluidHelper.getFluidFromHandler(controlledAssets.get(i).getFluidHandler()), new ItemStack(Item.bucketEmpty));//new ItemStack(Block.blocksList[controlledAssets.get(i).getTankInfo(ForgeDirection.UP)[0].fluid.getFluid().getBlockID()]);
			else 
				inventory[i] = new ItemStack(Item.bucketEmpty);
	}

	public boolean atLeastOneBucket() {

		for (int i = 31; i < inventory.length; i++)
			if (inventory[i] != null && FluidContainerRegistry.isBucket(inventory[i])) {

				this.decrStackSize(i, 1);
				return true;
			}

		return false;
	}

	public void setDrainIndex(int newIndex) {
		
		if (newIndex < controlledAssets.size())
			drainIndex = newIndex;
	}
	
	public int getDrain() {
		
		return drainIndex;
	}
	
	/*
	 * IInventory
	 */

	@Override
	public ItemStack getStackInSlot (int slot)
	{
		return inventory[slot];
	}

	@Override
	public int getSizeInventory ()
	{
		return inventory.length;
	}

	@Override
	public int getInventoryStackLimit ()
	{
		return 64;
	}

	@Override
	public void setInventorySlotContents (int slot, ItemStack itemstack)
	{
		inventory[slot] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int quantity)
	{
		if (slot <= 30)
			return inventory[slot];

		if (inventory[slot] != null)
		{
			if (inventory[slot].stackSize <= quantity)
			{
				ItemStack stack = inventory[slot];
				inventory[slot] = null;
				return stack;
			}
			ItemStack split = inventory[slot].splitStack(quantity);
			if (inventory[slot].stackSize == 0)
			{
				inventory[slot] = null;
			}
			return split;
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean isUseableByPlayer (EntityPlayer entityplayer)
	{
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
			return false;

		else
			return entityplayer.getDistance((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {

		return null;
	}

	@Override
	public String getInvName() {

		return "dyanmicmods.inventory.modifier";
	}

	@Override
	public boolean isInvNameLocalized() {

		return false;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {

		if (slot < getSizeInventory())
		{
			if (inventory[slot] == null || itemstack.stackSize + inventory[slot].stackSize <= getInventoryStackLimit())
				return true;
		}
		return false;
	}

	/*
	 * Syncing Methods
	 */

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {

		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {

			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length)
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
		}

		energyStored = energyStored.readFromNBT(tagCompound);
		drainIndex = tagCompound.getInteger("drainIndex");
		tickCount = tagCompound.getInteger("currentTick");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {

		super.writeToNBT(tagCompound);
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

		energyStored.writeToNBT(tagCompound);
		
		tagCompound.setInteger("drainIndex", drainIndex);
		tagCompound.setInteger("currentTick", tickCount);
	}

	@Override
	public Packet getDescriptionPacket() {

		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {

		readFromNBT(packet.data);
	}

	/*
	 * Energy Handler
	 */

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

		//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return energyStored.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

		//worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return energyStored.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {

		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {

		return energyStored.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {

		return energyStored.getMaxEnergyStored();
	}

	/*
	 * IFluidHandler
	 */

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

		if (resource == null)
			return 0;

		resource = resource.copy();
		int totalUsed = 0;
		IFluidHandler tankToFill = null;

		for (FluidHandlerPart fhP : controlledAssets)
			if (FluidHelper.getFluidFromHandler(fhP.getFluidHandler()) != null && FluidHelper.getFluidFromHandler(fhP.getFluidHandler()).isFluidEqual(resource) 
					&& FluidHelper.getFluidFromHandler(fhP.getFluidHandler()).amount + FluidContainerRegistry.BUCKET_VOLUME <= FluidHelper.getHandlerCapacity(fhP.getFluidHandler())) {
				
				tankToFill = fhP.getFluidHandler();
				break;
			}

		if (tankToFill == null)
			for (FluidHandlerPart fhP : controlledAssets)
				if (FluidHelper.getFluidFromHandler(fhP.getFluidHandler()) == null) {
					
					tankToFill = fhP.getFluidHandler();
					break;
				}

		if (tankToFill == null)
			return 0;

		FluidStack liquid = FluidHelper.getFluidFromHandler(tankToFill);

		if (liquid != null && liquid.amount > 0 && !liquid.isFluidEqual(resource))
			return 0;

		while (tankToFill != null && resource.amount > 0) {
			
			int used = tankToFill.fill(ForgeDirection.UP, resource, doFill);
			resource.amount -= used;

			if (used > 0)
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

			totalUsed += used;
		}

		return totalUsed;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

		for (FluidHandlerPart fluidTank : controlledAssets)
			 if (FluidHelper.getFluidFromHandler(fluidTank.getFluidHandler()) != null && FluidHelper.getFluidFromHandler(fluidTank.getFluidHandler()).isFluidEqual(resource))
				 return fluidTank.getFluidHandler().drain(ForgeDirection.UNKNOWN, resource.amount, doDrain);

		 worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		 return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {


		if (controlledAssets.isEmpty())
			return null;
		
		 worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		 return controlledAssets.get(drainIndex).getFluidHandler().drain(ForgeDirection.UNKNOWN, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {

		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {

		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {

		return new FluidTankInfo[] { controlledAssets.get(drainIndex).getFluidHandler().getTankInfo(ForgeDirection.UNKNOWN)[0] }; 
	}
}
