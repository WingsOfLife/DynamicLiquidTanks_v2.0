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

	public boolean extractor = false;

	public FluidStack movingFluid = null;
	
	public int sentTo = 0;

	public DuctTileEntity() {
		maxTickCount = 61;
		maxTickCountSec = 5;
	}

	public void updateEntity() {
		if (!worldObj.isRemote) {
			doCount();
			doCountSec();
			/*
			 * Render Update
			 */

			for (int i = 0; i < 6; i++) 
				if (i < blockInDir.length)
					blockInDir[i] = blockInDirection(VALID_DIRECTIONS[i]);


			/*
			 * Extract Liquid From Inventory
			 */

			if (countMet()) {
				if (findNearTank().size() > 0 && extractor) {
					int[] randomTank = findNearTank().get(0);
					if (randomTank.length == 3) {
						TileEntity tile = worldObj.getBlockTileEntity(randomTank[0], randomTank[1],	randomTank[2]);
						if (tile instanceof IFluidHandler) {
							FluidStack tankFluid = ((IFluidHandler) tile).getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
							if (tankFluid != null) {
								int amountInTank = tankFluid.amount;
								if (tankFluid != null && (movingFluid == null || tankFluid.isFluidEqual(movingFluid))) { 
									if ((amountInTank - maxExtract) >= 0) {
										FluidStack fluidMove = new FluidStack(((IFluidHandler) tile).getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.getFluid(), maxExtract);
										fill(fluidMove, fluidMove.amount, new int[] { -1, -1, -1 });
										((IFluidHandler) tile).drain(ForgeDirection.UNKNOWN, maxExtract, true);
									} else { 
										FluidStack fluidMove  = new FluidStack(((IFluidHandler) tile).getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.getFluid(), maxExtract + ((amountInTank - maxExtract)));
										fill(fluidMove, movingFluid.amount, new int[] { -1, -1, -1 });
										((IFluidHandler) tile).drain(ForgeDirection.UNKNOWN, maxExtract + ((amountInTank - maxExtract)), true);
									}
								}
							}
						}
					}
				}
			}

			/*
			 * Liquid Pathing
			 */
			if (movingFluid == null)
				return;

			if (numberOfPaths().size() > 0 && countMetSec()) {
				int[] randomPath = numberOfPaths().get(0);
				if (randomPath.length == 3 && movingFluid != null) {
					TileEntity tile = worldObj.getBlockTileEntity(randomPath[0], randomPath[1],	randomPath[2]);
					if (tile instanceof DuctTileEntity) {
						DuctTileEntity duct = (DuctTileEntity) tile;
						if (movingFluid != null) {
							//duct.passOn(movingFluid, new int[] { xCoord, yCoord, zCoord });
							duct.fill(movingFluid, movingFluid.amount, new int[] { xCoord, yCoord, zCoord });
							previousLocation = new int[] { -1, -1, -1 };
							//movingFluid = null;
							drain(movingFluid.amount);
						}
					} 
					else if (tile instanceof IFluidHandler && !extractor) {
						IFluidHandler handler = (IFluidHandler) tile;
						if (movingFluid != null) {
							handler.fill(ForgeDirection.UNKNOWN, movingFluid, true);
							previousLocation = new int[] { -1, -1, -1 };
							//movingFluid = null;
							drain(movingFluid.amount);
						}
					}
				}
			}
		}
	}

	/*
	 * Transfer Liquid
	 */

	public boolean fill(FluidStack fluidStack, int passAmnt, int[] prevLoc) {
		if (movingFluid != null) {
			if (movingFluid.isFluidEqual(fluidStack)) {
				int newAmount = passAmnt + movingFluid.amount;
				movingFluid.amount = newAmount;
				sentTo = passAmnt;
				previousLocation = prevLoc;
				return true;
			}
			return false;
		} else {
			previousLocation = prevLoc;
			movingFluid = fluidStack;
			sentTo = passAmnt;
			return true;
		}
	}
	
	public void drain(int amount) {
		if (movingFluid == null)
			return;
		
		int newAmount = movingFluid.amount - amount;
		
		movingFluid = new FluidStack(movingFluid.getFluid(), newAmount);
		sentTo = 0;
		
		if (movingFluid != null && movingFluid.amount <= 0)
			movingFluid = null;
	}
	
	public void passOn(FluidStack toPass, int[] comingFrom) {
		movingFluid = toPass;
		previousLocation = comingFrom;
	}

	public ArrayList<int[]> numberOfPaths() {
		ArrayList<int[]> validPaths = new ArrayList<int[]>();

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			if (tile instanceof DuctTileEntity)
				if (tile.xCoord != previousLocation[0] || tile.yCoord != previousLocation[1] || tile.zCoord != previousLocation[2])
					validPaths.add(new int[] { xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ });
			if (tile instanceof IFluidHandler)
				validPaths.add(new int[] { xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ });
		}

		Collections.shuffle(validPaths);
		return validPaths;
	}

	public ArrayList<int[]> findNearTank() {
		ArrayList<int[]> validPaths = new ArrayList<int[]>();

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			if (tile instanceof IFluidHandler)
				if (tile.xCoord != previousLocation[0] && tile.yCoord != previousLocation[1] && tile.zCoord != previousLocation[2])
					validPaths.add(new int[] { xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ });				
		}

		Collections.shuffle(validPaths);
		return validPaths;
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
