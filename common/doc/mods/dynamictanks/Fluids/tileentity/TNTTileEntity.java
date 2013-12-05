package doc.mods.dynamictanks.Fluids.tileentity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import doc.mods.dynamictanks.Fluids.FluidTNT;
import doc.mods.dynamictanks.helpers.CPotionHelper;

public class TNTTileEntity extends TileEntity {
	
	private final float ticksPerSec = CPotionHelper.ticksPerSec;
	private final float maxExistance = 10 * ticksPerSec;

	protected float ticksExisted = 0;
	protected boolean primed = false;

	public TNTTileEntity() {}

	public TNTTileEntity(int existance) {
		ticksExisted = existance;
	}

	public int getPotency() {
		return (int) (100 - ((ticksExisted / maxExistance) * 100));
	}
	
	public void setExistance(float newExistance) {
		ticksExisted = newExistance;
	}
	
	public void setPrimed(boolean prime) {
		primed = prime;
	}
	
	public float getExistance() {
		return ticksExisted;
	}
	
	public boolean getPrimed() {
		return primed;
	}
	
	public void removeRndStability() {
		ticksExisted += (new Random().nextInt(25) + new Random().nextInt(50) + 20) * ticksPerSec;
	}
	
	@Override
	public void updateEntity() {
		if (primed) ticksExisted++;

		if (ticksExisted >= maxExistance) {
			worldObj.newExplosion(null, xCoord, yCoord, zCoord, 4F, true, true);
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
	}

	/*
	 * Syncing Methods
	 */

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		ticksExisted = tagCompound.getFloat("alive");
		primed = tagCompound.getBoolean("primed");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setFloat("alive", ticksExisted);
		tagCompound.setBoolean("primed", primed);
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
}
