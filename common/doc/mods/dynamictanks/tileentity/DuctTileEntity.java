package doc.mods.dynamictanks.tileentity;

import doc.mods.dynamictanks.block.BlockManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public class DuctTileEntity extends TileEntity {

	public class directionValues {
		public static final int DOWN = 0;
		public static final int UP = 1;
		public static final int NORTH = 2;
		public static final int SOUTH = 3;
		public static final int WEST = 4;
		public static final int EAST = 5;
	}

	public ForgeDirection[] VALID_DIRECTIONS = { 
			ForgeDirection.DOWN, 
			ForgeDirection.UP,
			ForgeDirection.NORTH, 
			ForgeDirection.SOUTH, 
			ForgeDirection.WEST, 
			ForgeDirection.EAST
		};
	
	public final int TRUE = 1;
	public final int FALSE = -1;

	public int[] blockInDir = { FALSE, FALSE, FALSE, FALSE, FALSE, FALSE };

	public DuctTileEntity() {
	}

	public void updateEntity() {
		if (!worldObj.isRemote)
			for (int i = 0; i < 6; i++) 
				if (i < blockInDir.length)
					blockInDir[i] = blockInDirection(VALID_DIRECTIONS[i]);			
	}

	public int blockInDirection(ForgeDirection dir) {
		int blockId = worldObj.getBlockId(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
		TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
		
		if (blockId == BlockManager.BlockDuct.blockID )
			return TRUE;
		if (tile != null && tile instanceof IFluidHandler)
			return TRUE;
		return FALSE;
	}

	public boolean isBlockThere(int direction) {
		if (direction > blockInDir.length)
			return false;
		switch(direction) {
		case directionValues.DOWN: return blockInDir[directionValues.DOWN] == TRUE;
		case directionValues.UP: return blockInDir[directionValues.UP] == TRUE;
		case directionValues.NORTH: return blockInDir[directionValues.NORTH] == TRUE;
		case directionValues.SOUTH: return blockInDir[directionValues.SOUTH] == TRUE;
		case directionValues.WEST: return blockInDir[directionValues.WEST] == TRUE;
		case directionValues.EAST: return blockInDir[directionValues.EAST] == TRUE;
		}
		return false;
	}

	/*
	 * Sync
	 */

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		blockInDir = tagCompound.getIntArray("blocksWhere");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setIntArray("blocksWhere", blockInDir);
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
