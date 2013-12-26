package doc.mods.dynamictanks.UP;

import net.minecraft.nbt.NBTTagCompound;
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
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.tileentity.CountableTileEntity;

public class FPCTileEntity_Basic extends CountableTileEntity implements IFluidHandler
{
	public final int MAX_CAP = 2 * FluidContainerRegistry.BUCKET_VOLUME;

	protected FluidTank fluidPower = new FluidTank(MAX_CAP);

	public FPCTileEntity_Basic() {
		tickCount = 10000;
	}

	@Override
	public void updateEntity() {
		doCount();
	}
	
	public FluidTank getFP() {
		return fluidPower;
	}
	
	/*
	 * IFluidHandler
	 */

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{		
		if (resource == null)
			return 0;

		if (resource.fluidID != FluidManager.omniFluid.getID())
			return 0;
		
		resource = resource.copy();
		int totalUsed = 0;
		FluidTank tankToFill = null;

		if (fluidPower.getFluid() != null && fluidPower.getFluid().isFluidEqual(resource))
		{
			tankToFill = fluidPower;
		}

		if (tankToFill == null)
			if (fluidPower.getFluid() == null)
			{
				tankToFill = fluidPower;
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
		if (fluidPower.getFluid() != null && fluidPower.getFluid().isFluidEqual(resource))
		{
			return fluidPower.drain(resource.amount, doDrain);
		}

		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		if (fluidPower.getFluid() == null)
			return null;

		return fluidPower.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return new FluidTankInfo[] { new FluidTankInfo(fluidPower) };
	}
}
