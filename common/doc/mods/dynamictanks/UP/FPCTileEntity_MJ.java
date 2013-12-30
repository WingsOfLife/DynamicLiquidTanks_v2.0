package doc.mods.dynamictanks.UP;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.common.ModConfig;

public class FPCTileEntity_MJ extends FPCTileEntity_Basic implements IPowerReceptor, IPowerEmitter {

	PowerHandler storage = null;
	public int energyStored = -1;

	public FPCTileEntity_MJ() {
		storage = new PowerHandler(this, Type.STORAGE);
		storage.configure(1, ModConfig.omniPowerSettings.MJPerTick, 1, 2000);
	}

	@Override
	public void updateEntity() {
		if (fluidPower.getFluid() != null)
			fluidPower.drain(fluidPower.getFluidAmount(), true);
		fluidPower.fill(new FluidStack(FluidManager.potionFluid, energyStored + 1), true);

		if (!worldObj.isRemote) {
			energyStored = (int) storage.getEnergyStored();
			//worldObj.addBlockEvent(xCoord, yCoord, zCoord, BlockManager.BlockFPC_MJ.blockID, 0, energyStored);
		}
		storage.setEnergy(fluidPower.getFluidAmount() * ModConfig.omniPowerSettings.MJPerMiliB);
	}

	@Override
	public boolean receiveClientEvent(int id, int value) {
		if (id == 0)
			energyStored = value;         
		return true;
	}

	/* 
	 * Sync
	 */

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
		storage.configure(1, 1, 1, 2000);

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

	/*
	 * BC API
	 */

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return storage.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {}

	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public boolean canEmitPowerFrom(ForgeDirection side) {
		return true;
	}

}
