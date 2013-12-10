package doc.mods.dynamictanks.UP;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
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

public class FPCTileEntity_RF extends FPCTileEntity_Basic implements IEnergyHandler, IFluidHandler {

	protected EnergyStorage storage = new EnergyStorage(20000, ModConfig.omniPowerSettings.RFPerTick, 100);

	public FPCTileEntity_RF() {}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
		fluidPower.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		storage.writeToNBT(nbt);
		fluidPower.writeToNBT(nbt);
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

	/* IEnergyHandler */

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		if (fluidPower.getFluid() != null) 
			drain(from, fluidPower.getFluidAmount(), true);
		fill(from, new FluidStack(FluidManager.potionFluid, (storage.getEnergyStored() / ModConfig.omniPowerSettings.RFPerMiliB)), true);
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		if (fluidPower.getFluid() != null) 
			drain(from, fluidPower.getFluidAmount(), true);
		fill(from, new FluidStack(FluidManager.potionFluid, (storage.getEnergyStored() / ModConfig.omniPowerSettings.RFPerMiliB)), true);
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return storage.getMaxEnergyStored();
	}
}
