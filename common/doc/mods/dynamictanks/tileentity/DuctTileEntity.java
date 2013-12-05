package doc.mods.dynamictanks.tileentity;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidHandler;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.block.BlockManager;

public class DuctTileEntity extends CountableTileEntity {

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

	public final int maxExtract = FluidContainerRegistry.BUCKET_VOLUME;

	public int[] blockInDir = { FALSE, FALSE, FALSE, FALSE, FALSE, FALSE };

	public int[] previousLocation = { -1, -1, -1 };
	public int[] lastCheck = { -1, -1, -1 };

	public boolean extractor = false;

	public FluidStack movingFluid = null;

	public int sentTo = 0;
	public int pathCheck = 0;
	public final int MAX_AMNT = 5 * FluidContainerRegistry.BUCKET_VOLUME;

	public DuctTileEntity() {
		maxTickCount = 61;
		maxTickCountSec = 5;
	}

	public void updateEntity() {		
		/*
		 * Render Update
		 */

		for (int i = 0; i < 6; i++) 
			if (i < blockInDir.length)
				blockInDir[i] = blockInDirection(VALID_DIRECTIONS[i]);

		/*
		 * Liquid Pathing
		 */

		if (lastCheck[0] == -1)
			lastCheck[0] = xCoord;
		if (lastCheck[1] == -1)
			lastCheck[1] = yCoord;
		if (lastCheck[2] == -1)
			lastCheck[2] = zCoord;

		doCountSec();
		if (countMetSec() && extractor) {
			extractorSearch();
			if (numberOfPaths().isEmpty())
				return;
			int[] randomPath = numberOfPaths().get(0);
			if (randomPath.length == 3) {
				TileEntity tile = worldObj.getBlockTileEntity(randomPath[0], randomPath[1],	randomPath[2]);
				if (tile instanceof DuctTileEntity) {
					DuctTileEntity duct = (DuctTileEntity) tile;
					lastCheck[0] = duct.xCoord;
					lastCheck[1] = duct.yCoord;
					lastCheck[2] = duct.zCoord;
				} 
				else if (tile instanceof IFluidHandler) {
					IFluidHandler handler = (IFluidHandler) tile;
					if(handler.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid == null) {
						if (movingFluid != null)
							handler.fill(ForgeDirection.UNKNOWN, movingFluid, true);
						previousLocation = new int[] { -1, -1, -1 };
						lastCheck = new int[] { xCoord, yCoord, zCoord };
						movingFluid = null;
						return;
					}
					if (handler.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.amount + 1 * FluidContainerRegistry.BUCKET_VOLUME <= handler.getTankInfo(ForgeDirection.UNKNOWN)[0].capacity)
						if (movingFluid != null)
							handler.fill(ForgeDirection.UNKNOWN, movingFluid, true);
					previousLocation = new int[] { -1, -1, -1 };
					lastCheck = new int[] { xCoord, yCoord, zCoord };
					movingFluid = null;
				}
			}
		}
	}

	public ArrayList<int[]> numberOfPaths() {
		ArrayList<int[]> validPaths = new ArrayList<int[]>();

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = worldObj.getBlockTileEntity(lastCheck[0] + dir.offsetX, lastCheck[1] + dir.offsetY, lastCheck[2] + dir.offsetZ);
			if (tile instanceof DuctTileEntity)
				if (!((DuctTileEntity) tile).extractor)
					if (tile.xCoord != previousLocation[0] || tile.yCoord != previousLocation[1] || tile.zCoord != previousLocation[2])
						validPaths.add(new int[] { lastCheck[0] + dir.offsetX, lastCheck[1] + dir.offsetY, lastCheck[2] + dir.offsetZ });
			if (tile instanceof IFluidHandler && (xCoord != lastCheck[0] || yCoord != lastCheck[1] || zCoord != lastCheck[2]))
				validPaths.add(new int[] { lastCheck[0] + dir.offsetX, lastCheck[1] + dir.offsetY, lastCheck[2] + dir.offsetZ });
		}

		Collections.shuffle(validPaths);
		return validPaths;
	}

	/*public ArrayList<int[]> findNearTank() {
		ArrayList<int[]> validPaths = new ArrayList<int[]>();

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = worldObj.getBlockTileEntity(lastCheck[0] + dir.offsetX, lastCheck[1] + dir.offsetY, lastCheck[2] + dir.offsetZ);
			if (tile instanceof IFluidHandler)
				if (tile.xCoord != previousLocation[0] && tile.yCoord != previousLocation[1] && tile.zCoord != previousLocation[2])
					validPaths.add(new int[] { lastCheck[0] + dir.offsetX, lastCheck[1] + dir.offsetY, lastCheck[2] + dir.offsetZ });				
		}

		Collections.shuffle(validPaths);
		return validPaths;
	}*/

	public ArrayList<IFluidHandler> extractorSearch() {
		ArrayList<IFluidHandler> extractorTanks = new ArrayList<IFluidHandler>();
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int xCord = xCoord - dir.offsetX;
			int yCord = yCoord - dir.offsetY;
			int zCord = zCoord - dir.offsetZ;

			TileEntity search = worldObj
					.getBlockTileEntity(xCord, yCord, zCord);
			if (search instanceof IFluidHandler && movingFluid == null) {
				IFluidHandler extractHere = (IFluidHandler) search;
				extractorTanks.add(extractHere);
				if (extractHere.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.amount
						- 1 * FluidContainerRegistry.BUCKET_VOLUME > 0) {
					extractHere.drain(ForgeDirection.UNKNOWN,
							new FluidStack(extractHere.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid,
									1 * FluidContainerRegistry.BUCKET_VOLUME), true);
					movingFluid = new FluidStack(extractHere.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid,
							1 * FluidContainerRegistry.BUCKET_VOLUME);
				}
			}
		}
		return extractorTanks;
	}

	/*
	 * Rendering Helper
	 */

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
		extractor = tagCompound.getBoolean("extractor");
		previousLocation = tagCompound.getIntArray("prev");
		sentTo = tagCompound.getInteger("amntSent");

		if (tagCompound.getBoolean("hasLiquid"))
			movingFluid = new FluidStack(tagCompound.getInteger("itemID"), tagCompound.getInteger("amount"));
		else
			movingFluid = null;
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setIntArray("blocksWhere", blockInDir);
		tagCompound.setBoolean("extractor", extractor);
		tagCompound.setIntArray("prev", previousLocation);
		tagCompound.setInteger("amntSent", sentTo);

		tagCompound.setBoolean("hasLiquid", movingFluid != null);
		if (movingFluid != null) {
			tagCompound.setInteger("itemID", movingFluid.fluidID);
			tagCompound.setInteger("amount", movingFluid.amount);
		}
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
