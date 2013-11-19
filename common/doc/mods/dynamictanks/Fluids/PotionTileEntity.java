package doc.mods.dynamictanks.Fluids;

import java.util.Random;

import doc.mods.dynamictanks.helpers.PotionHelper;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.BlockFluidBase;

public class PotionTileEntity extends TileEntity {

	private final float ticksPerSec = PotionHelper.ticksPerSec;
	private final float maxExistance = PotionHelper.maxExistance;

	protected float ticksExisted = 0;
	

	public PotionTileEntity() {}

	public PotionTileEntity(int existance) {
		ticksExisted = existance;
	}

	public int getPotency() {
		return (int) ((ticksExisted / maxExistance) * 100);
	}
	
	public void setExistance(float newExistance) {
		ticksExisted = newExistance;
	}
	
	public float getExistance() {
		return ticksExisted;
	}
	
	public void removeRndStability() {
		ticksExisted += (new Random().nextInt(25) + new Random().nextInt(50) + 20) * ticksPerSec;
	}
	
	@Override
	public void updateEntity() {
		ticksExisted++;

		if (ticksExisted >= maxExistance) {
			worldObj.setBlock(xCoord, yCoord, zCoord, Block.waterStill.blockID);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			/*BlockFluidBase myBlock = (BlockFluidBase) Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)];
			myBlock.drain(worldObj, xCoord, yCoord, zCoord, true);*/
		}
	}

	/*
	 * Syncing Methods
	 */

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		ticksExisted = tagCompound.getFloat("alive");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setFloat("alive", ticksExisted);
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
