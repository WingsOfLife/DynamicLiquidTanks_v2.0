package doc.mods.dynamictanks.UP;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.common.ModConfig;

public class GeneratorTileEntity extends TileEntity implements IEnergyHandler, IFluidHandler {

	protected EnergyStorage storage = new EnergyStorage(32000, 320);
	protected FluidTank     tank    = new FluidTank(3 * FluidContainerRegistry.BUCKET_VOLUME);

	public GeneratorTileEntity() {
		//storage.setEnergyStored(32000);
	}

	@Override
	public void updateEntity() {
		for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
			int x = xCoord + d.offsetX;
			int y = yCoord + d.offsetY;
			int z = zCoord + d.offsetZ;

			if (worldObj.getBlockTileEntity(x, y, z) instanceof IEnergyHandler) {
				IEnergyHandler sendEnergy = (IEnergyHandler) worldObj.getBlockTileEntity(x, y, z);
				if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
					if (extractEnergy(d, storage.getMaxExtract(), true) > 0 && sendEnergy.getEnergyStored(d) + storage.getMaxExtract() <= sendEnergy.getMaxEnergyStored(d)) {
						extractEnergy(d, storage.getMaxExtract(), false);
						sendEnergy.receiveEnergy(d.getOpposite(), storage.getMaxExtract(), false);
					} else if (storage.getEnergyStored() + ModConfig.omniPowerSettings.RFPerMiliB <= storage.getMaxEnergyStored() && tank.getFluidAmount() - 1 >= 0) {
						storage.setEnergyStored(storage.getEnergyStored() + ModConfig.omniPowerSettings.RFPerMiliB);
						tank.drain(1, true);
					}
				}
				else {
					if (extractEnergy(d, storage.getMaxExtract(), true) > 0 && tank.getFluidAmount() + 1 <= tank.getCapacity()) {
						extractEnergy(d, ModConfig.omniPowerSettings.RFPerMiliB, false);
						tank.fill(new FluidStack(FluidManager.omniFluid, 1), true);
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);
		storage.writeToNBT(nbt);
	}

	/* IEnergyHandler */
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if (storage.getEnergyStored() - maxExtract < 0)
			return storage.extractEnergy(0, simulate);
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {

		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {

		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {

		return storage.getMaxEnergyStored();
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		if (resource == null)
		{
			return 0;
		}

		resource = resource.copy();
		int totalUsed = 0;
		FluidTank tankToFill = null;

		if (tank.getFluid() != null && tank.getFluid().isFluidEqual(resource))
		{
			tankToFill = tank;
		}

		if (tankToFill == null)
			if (tank.getFluid() == null)
			{
				tankToFill = tank;
			}

		if (tankToFill == null)
		{
			return 0;
		}

		FluidStack liquid = tankToFill.getFluid();

		if (liquid != null && liquid.amount > 0 && !liquid.isFluidEqual(resource))
		{
			return 0;
		}

		while (tankToFill != null && resource.amount > 0 && tankToFill.getFluidAmount() + resource.amount <= tankToFill.getCapacity())
		{
			int used = tankToFill.fill(resource, doFill);
			resource.amount -= used;

			if (used > 0)
			{
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}

			totalUsed += used;
		}

		return totalUsed;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		if (tank.getFluid().isFluidEqual(resource))
			return tank.drain(resource.amount, doDrain);

		return null;
	}
	
	@Override
	 public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	 {
		 return tank.drain(maxDrain, doDrain);
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
		return new FluidTankInfo[] { tank.getInfo() };
	}


}
