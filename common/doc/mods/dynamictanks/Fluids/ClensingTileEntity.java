package doc.mods.dynamictanks.Fluids;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import doc.mods.dynamictanks.helpers.CPotionHelper;
import doc.mods.dynamictanks.potionrecipe.PotionInverse;

public class ClensingTileEntity extends TileEntity {

	protected float damageHealed = 0;
	public static final float maxHealing = CPotionHelper.maxExistance / 4;
	
	public ClensingTileEntity() {}
	
	public float getHealed() {
		return damageHealed;
	}
	
	public void setHealed(float damage) {
		damageHealed = damage;
	}
	
	@Override
	public void updateEntity() {
		if (damageHealed >= maxHealing) {
			worldObj.setBlock(xCoord, yCoord, zCoord, Block.waterStill.blockID);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}			
	}
	
	/*
	 * Syncing Methods
	 */

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		damageHealed = tagCompound.getFloat("healed");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setFloat("healed", damageHealed);
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
