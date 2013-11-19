package doc.mods.dynamictanks.tileentity;

import java.util.Arrays;
import java.util.LinkedList;

import net.minecraft.block.Block;
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
import doc.mods.dynamictanks.api.IPowerEater;
import doc.mods.dynamictanks.api.IStorageUnit;
import doc.mods.dynamictanks.api.PowerController;
import doc.mods.dynamictanks.helpers.FluidHelper;

public class ControllerTileEntity extends CountableTileEntity implements IFluidHandler, IPowerEater, IStorageUnit {

	/*
	 * list vars
	 */	
	protected LinkedList<FluidTank> containedLiquids = new LinkedList<FluidTank>();
	protected LinkedList<int[]> neighborLocations = new LinkedList<int[]>(); 

	/*
	 * Commands vars
	 */
	public LinkedList<String> recentSent = new LinkedList<String>();
	public LinkedList<String> recentDisplayed = new LinkedList<String>();

	/*
	 * misc vars
	 */
	protected int[] camoMeta = { -1, -1 }; // first var camo, second var meta	
	protected int dyeColorMeta = -1; //dyeColor
	protected int potionMeta = -1;

	protected double BONUS_MULT = 1.05;
	protected int INTERNAL_SIZE = 16;


	/*
	 * liquid vars
	 */
	protected int numLiquids = 1;
	protected int tankCapacity = INTERNAL_SIZE * FluidContainerRegistry.BUCKET_VOLUME;
	protected int toExtractFromTank = 0;

	private int maxNumAllowed = 6;

	/*
	 * Power Vars
	 */

	protected PowerController powerController = null;

	/* 
	 * Upgrade Variables
	 */

	protected double upgradeMult = 1.1;
	protected int powerOf = 0;

	/* 
	 * Commandsline commands/GUI Information
	 */

	public ControllerTileEntity() {
		if (containedLiquids.isEmpty())
			containedLiquids.add(new FluidTank(getTankCapacity()));

		powerController = new PowerController();
		powerController.modify(0, 6000, 15);
	}

	/* 
	 * Setters
	 */

	public void addAdditionalTank(int numToAdd) {
		for (int i = 0; i < numToAdd; i++)
			containedLiquids.add(new FluidTank(getTankCapacity()));
		numLiquids += numToAdd;
	}

	public void removeAdditionalTank(int numToRemove) {
		for (int i = 0; i < numToRemove; i++)
			containedLiquids.removeLast();
		numLiquids -= numToRemove;
	}

	public boolean addNeighbor(int[] loc) {
		for(int i = 0; i < neighborLocations.size(); i++)
			if (Arrays.equals(loc, neighborLocations.get(i)))
				return false;

		neighborLocations.add(loc);
		return true;
	}

	public void resizeLiqInventory(int newSize) {
		this.numLiquids = newSize;
	}

	public void setCamo(int blockID) {
		camoMeta[0] = blockID;
	}

	public void setCamo(int blockID, int meta) {
		camoMeta[0] = blockID;
		camoMeta[1] = meta;
	}

	public void setDyeColor(int meta) {
		dyeColorMeta = meta;
	}

	public void setPotion(int meta) {
		potionMeta = meta;
	}

	public void setLiquidIndex(int val) {
		toExtractFromTank = val;
	}

	public void resizeTank(int newSize) {
		for (FluidTank fluidTank : containedLiquids) {
			fluidTank.setCapacity(newSize * FluidContainerRegistry.BUCKET_VOLUME);
		}
	}

	/*
	 * Getters
	 */

	public LinkedList<FluidTank> getAllLiquids() {
		return containedLiquids;
	}

	public int getCamo() {
		return camoMeta[0];
	}

	public int getCamoMeta() {
		return camoMeta[1];
	}

	public int getDyeColor() {
		return dyeColorMeta;
	}

	public int getPotion() {
		return potionMeta;
	}

	public int getStored() {
		int count = 0;
		for (FluidTank tank : containedLiquids)
			if (tank.getFluid() != null)
				count++;
		return count;
	}

	public FluidTank getTankObj(FluidStack fluidStack) {
		for (FluidTank tank : containedLiquids) {
			if (tank.getFluid().isFluidEqual(fluidStack))
				return tank;
		}

		return null;
	}

	public int getTankCapacity() {
		return this.tankCapacity;
	}

	public int getAlwdLiquids() {
		return numLiquids;
	}

	public int getTotalAmount() {
		int amount = 0;
		for (FluidTank tank : containedLiquids) {
			amount += tank.getFluidAmount();
		}
		return amount;
	}

	public LinkedList<int[]> getNeighbors() {
		return neighborLocations;
	}

	public int getLiquidIndex() {
		return toExtractFromTank;
	}

	public int getBrightness() {
		if (FluidHelper.hasLiquid(this)) {
			FluidStack liquid = containedLiquids.get(toExtractFromTank).getFluid();
			if (liquid.getFluid().canBePlacedInWorld())
				return Block.lightValue[liquid.getFluid().getBlockID()];
		}
		return 0;
	}

	public int getNumLayers() {
		int count = 0;
		LinkedList<Integer> counted = new LinkedList<Integer>();
		for (int[] arr : neighborLocations)
			if (!counted.contains(arr[1])) {
				counted.add(arr[1]);
				count++;
			}
		return count;		
	}

	public int getPerLayer() {
		if (getNumLayers() == 0)
			return getTankCapacity();
		return getTankCapacity() / getNumLayers();
	}

	/*
	 * Misc Methods
	 */
	public void nextLiquidIndex() {
		if (toExtractFromTank + 1 > (containedLiquids.size() - 1)) {
			toExtractFromTank = 0;
			return;
		}
		toExtractFromTank++;
	}

	public void refreshTankCapacity() {
		for (FluidTank fluidTank : containedLiquids)
			fluidTank.setCapacity(getTankCapacity());
	}

	public void overflowingCheck() {
		for (FluidTank fT : containedLiquids)
			if (fT.getFluid() != null)
				if (fT.getFluidAmount() > fT.getCapacity())
					fT.getFluid().amount = fT.getCapacity();
	}

	public void refresh() {
		if (toExtractFromTank > containedLiquids.size() - 1)
			toExtractFromTank = 0;
		
		int newCap = (int) ((((neighborLocations.size() + 1) * INTERNAL_SIZE) * (BONUS_MULT)) * (Math.pow(upgradeMult, powerOf)) * FluidContainerRegistry.BUCKET_VOLUME);
		tankCapacity = newCap < (INTERNAL_SIZE * FluidContainerRegistry.BUCKET_VOLUME) ? INTERNAL_SIZE * FluidContainerRegistry.BUCKET_VOLUME : newCap;
		refreshTankCapacity();

		overflowingCheck();

		if (getLiquidIndex() > containedLiquids.size())
			toExtractFromTank = containedLiquids.size(); 
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

		if (!FluidHelper.hasPotion(this))
			potionMeta = -1;
	}

	/*
	 * TileEntity Methods
	 */
	@Override
	public void updateEntity() {		
		if (worldObj.isRemote) { //client side
			doCount();

			if (countMet()) 
				worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		}
		doCount();

		if (countMet()) //perform events every maxTickCount
			refresh(); //resize capacity of FluidTank Array*/	
	}

	/*
	 * Syncing Methods
	 */

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		numLiquids = tagCompound.getInteger("numLiquids");
		tankCapacity = tagCompound.getInteger("tankCapacity");
		toExtractFromTank = tagCompound.getInteger("index");
		camoMeta[0] = tagCompound.getInteger("blockID");
		camoMeta[1] = tagCompound.getInteger("meta");
		dyeColorMeta = tagCompound.getInteger("dye");
		potionMeta = tagCompound.getInteger("potion");

		NBTTagList tankTag = tagCompound.getTagList("Tanks");
		containedLiquids.clear();

		for (int iter = 0; iter < tankTag.tagCount(); iter++) {
			NBTTagCompound nbt = (NBTTagCompound) tankTag.tagAt(iter);
			FluidTank tank = new FluidTank(tankCapacity);
			tank.readFromNBT(nbt);
			containedLiquids.add(tank);
		}

		NBTTagList neighborTag = tagCompound.getTagList("Neighbor");
		neighborLocations.clear();

		for (int iter = 0; iter < neighborTag.tagCount(); iter++) {
			NBTTagCompound nbt = (NBTTagCompound) neighborTag.tagAt(iter);
			int[] i = nbt.getIntArray("neighbor" + iter);
			neighborLocations.add(i);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setInteger("numLiquids", numLiquids);
		tagCompound.setInteger("tankCapacity", tankCapacity);
		tagCompound.setInteger("index", toExtractFromTank);
		tagCompound.setInteger("blockID", camoMeta[0]);
		tagCompound.setInteger("meta", camoMeta[1]);
		tagCompound.setInteger("dye", dyeColorMeta);
		tagCompound.setInteger("potion", potionMeta);

		NBTTagList taglist = new NBTTagList();
		for (FluidTank tank : containedLiquids) {
			NBTTagCompound nbt = new NBTTagCompound();
			tank.writeToNBT(nbt);
			taglist.appendTag(nbt);
		}

		tagCompound.setTag("Tanks", taglist);

		NBTTagList neighborsList = new NBTTagList();
		for (int i = 0; i < neighborLocations.size(); i++) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setIntArray("neighbor" + i, neighborLocations.get(i));
			neighborsList.appendTag(nbt);
		}

		tagCompound.setTag("Neighbor", neighborsList);
	}

	@Override
	public Packet getDescriptionPacket () {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket (INetworkManager net, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}

	/*
	 * ITankHandler
	 */

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource == null)
			return 0;

		resource = resource.copy();
		int totalUsed = 0;
		FluidTank tankToFill = null;

		for (FluidTank fluidTank : containedLiquids) 
			if (fluidTank.getFluid() != null && fluidTank.getFluid().isFluidEqual(resource)) {
				tankToFill = fluidTank;
				break;
			}

		if (tankToFill == null)
			for (FluidTank fluidTank : containedLiquids)
				if (fluidTank.getFluid() == null) {
					tankToFill = fluidTank;
					break;
				}

		if (tankToFill == null)
			return 0;

		FluidStack liquid = tankToFill.getFluid();
		if (liquid != null && liquid.amount > 0 && !liquid.isFluidEqual(resource)) {
			return 0;
		}

		while (tankToFill != null && resource.amount > 0 && tankToFill.getFluidAmount() + resource.amount <= tankToFill.getCapacity()) {
			int used = tankToFill.fill(resource, doFill);
			resource.amount -= used;
			if (used > 0) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			totalUsed += used;
		}

		return totalUsed;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		for (FluidTank fluidTank : containedLiquids)
			if (fluidTank.getFluid().isFluidEqual(resource))
				return fluidTank.drain(resource.amount, doDrain);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (containedLiquids.isEmpty())
			return null;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return containedLiquids.get(toExtractFromTank).drain(maxDrain, doDrain);
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
		return new FluidTankInfo[] { new FluidTankInfo(containedLiquids.get(toExtractFromTank).getFluid(), containedLiquids.get(toExtractFromTank).getCapacity()) };
	}


	/*
	 * @IPowerEater
	 */

	@Override
	public int consume() {
		// TODO Auto-generated method stub
		return 0;
	}


	/*
	 * @IStorageUnit
	 */

	@Override
	public int fillUnit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int drainUnit() {
		// TODO Auto-generated method stub
		return 0;
	}
}
