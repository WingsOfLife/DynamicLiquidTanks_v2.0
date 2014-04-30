package doc.mods.dynamictanks.tileentity;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.client.render.RendererHelper;
import doc.mods.dynamictanks.helpers.grapher.BlockPosition;
import doc.mods.dynamictanks.helpers.grapher.QueueSearch;
import doc.mods.dynamictanks.packets.PacketHandler;

public class TankTileEntity extends TickingTileEntity implements IFluidHandler
{
	/*
	 * Controller Sync Vars
	 */
	public int[] ControllerCoords = { -1, -1, -1 }; // coords of tank's
	// controllers

	/*
	 * Self Vars
	 */

	protected int[] camoMeta = { -1, 0 };
	protected int dyeIndex = -1;

	public TankTileEntity() {}

	public TankTileEntity(int maxTickCount)
	{
		this.maxTickCount = maxTickCount;
	}

	/*
	 * Self Methods
	 */

	public boolean hasCamo()
	{
		return camoMeta[0] != -1;
	}

	public int[] getCamo()
	{
		return camoMeta;
	}

	public int[] getControllerCoords()
	{
		return ControllerCoords;
	}

	public int getDye()
	{
		return dyeIndex;
	}

	public boolean hasController()
	{
		return ControllerCoords != null && ControllerCoords[0] != -1;
	}

	public void invalidate()
	{
		ControllerCoords[0] = -1;
		ControllerCoords[1] = -1;
		ControllerCoords[2] = -1;
	}

	public void setControllerPos(int[] locs)
	{
		if (!hasController())
		{
			for (int i = 0; i < 3; i++)
			{
				ControllerCoords[i] = locs[i];
			}
		}
	}

	public int getLayer()
	{
		return hasController() ? (yCoord - RendererHelper.smallestIndex(getControllerTE().getNeighbors())) + 1 : -1;
	}

	public float amntToRender()
	{
		if (!hasController() || getControllerTE().getAllLiquids().isEmpty())
		{
			return -1;
		}

		float amnt = getControllerTE().getAllLiquids().get(getControllerTE().getLiquidIndex()).getFluidAmount();
		float cap = getControllerTE().getPerLayer();

		if (amnt > (cap * getLayer()))
		{
			return worldObj.getBlockId(xCoord, yCoord + 1, zCoord) != 0 ? 1.00f : 0.999f;
		}

		if (amnt < (cap * getLayer()))
		{
			float leftOver = (cap * getLayer()) - amnt;
			return 1.0f - leftOver / cap;
		}

		return worldObj.getBlockId(xCoord, yCoord + 1, zCoord) != 0 ? 1.00f : 0.999f;
	}

	public void setCamo(int blockID)
	{
		camoMeta[0] = blockID;
	}

	public void setCamo(int blockID, int meta)
	{
		camoMeta[0] = blockID;
		camoMeta[1] = meta;
	}

	public void setDye(int meta)
	{
		dyeIndex = meta;
	}

	public boolean invalidateController()
	{
		TileEntity checkIn = worldObj.getBlockTileEntity(ControllerCoords[0], ControllerCoords[1], ControllerCoords[2]);

		if (!(checkIn instanceof ControllerTileEntity))
		{
			invalidate();
			return false;
		}

		return true;
	}

	public boolean searchForController() {
		TileEntity whoAmI = null;
		TankTileEntity tankTE = null;
		ControllerTileEntity controllerTE = null;
		int currentX = xCoord;
		int currentY = yCoord;
		int currentZ = zCoord;
		int BlockID = 0;
		int[] loc = new int[3];

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
		{
			loc[0] = currentX - dir.offsetX;
			loc[1] = currentY - dir.offsetY;
			loc[2] = currentZ - dir.offsetZ;
			whoAmI = worldObj.getBlockTileEntity(loc[0], loc[1], loc[2]);
			BlockID = worldObj.getBlockId(loc[0], loc[1], loc[2]);

			if (BlockID == BlockManager.BlockTankController.blockID && whoAmI instanceof ControllerTileEntity)
			{
				controllerTE = (ControllerTileEntity) worldObj.getBlockTileEntity(loc[0], loc[1], loc[2]);
				controllerTE.addNeighbor(new int[] { currentX, currentY, currentZ });

				if (worldObj.isRemote)
					PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.syncNeighbors, controllerTE.xCoord, controllerTE.yCoord, controllerTE.zCoord, currentX, currentY, currentZ, xCoord, yCoord, zCoord);

				setControllerPos(loc);
				return true;
			}
			else if (BlockID == BlockManager.BlockTank.blockID && !hasController() && whoAmI instanceof TankTileEntity)
			{
				tankTE = (TankTileEntity) worldObj.getBlockTileEntity(loc[0], loc[1], loc[2]);

				if (tankTE.hasController())
				{
					//if (controllerTE instanceof ControllerTileEntity)
					controllerTE = (ControllerTileEntity) worldObj.getBlockTileEntity(tankTE.getControllerCoords()[0], tankTE.getControllerCoords()[1], tankTE.getControllerCoords()[2]);
					/*else
						controllerTE = null;*/

					if (controllerTE != null)
					{
						if (worldObj.isRemote)
							PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.syncNeighbors, controllerTE.xCoord, controllerTE.yCoord, controllerTE.zCoord, currentX, currentY, currentZ, xCoord, yCoord, zCoord);

						setControllerPos(tankTE.ControllerCoords);
						controllerTE.addNeighbor(new int[] { currentX, currentY, currentZ });
						return true;
					}
				}
			}
		}

		return false;
	}

	public ControllerTileEntity getControllerTE()
	{
		if (hasController())
			if (worldObj.getBlockTileEntity(ControllerCoords[0], ControllerCoords[1], ControllerCoords[2]) instanceof ControllerTileEntity)
			{
				return (ControllerTileEntity) worldObj.getBlockTileEntity(ControllerCoords[0], ControllerCoords[1], ControllerCoords[2]);
			}

		return null;
	}

	/*
	 * TileEntity Methods
	 */
	@Override
	public void updateEntity()
	{
		doCount();

		if (countMet())   // perform events every maxTickCount
		{
			if (!hasController())   //check if already has controller
			{
				searchForController();
				invalidateController();
			}
			
			//ArrayList<ControllerTileEntity> pathed = new ArrayList<ControllerTileEntity>();
			//pathed = QueueSearch.queueSearch(worldObj, new BlockPosition(xCoord, yCoord, zCoord), pathed);
			//pathed = pathed;
		}
	}

	/*
	 * Syncing Methods
	 */

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		ControllerCoords = tagCompound.getIntArray("controllerLoc");
		camoMeta = tagCompound.getIntArray("camo");
		dyeIndex = tagCompound.getInteger("dye");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		tagCompound.setIntArray("controllerLoc", ControllerCoords);
		tagCompound.setIntArray("camo", camoMeta);
		tagCompound.setInteger("dye", dyeIndex);
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
	 * IFluidHandler
	 */

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		return getControllerTE() != null ? getControllerTE().fill(from, resource, doFill) : 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		return getControllerTE() != null ? getControllerTE().drain(from, resource, doDrain) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return getControllerTE() != null ? getControllerTE().drain(from, maxDrain, doDrain) : null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return getControllerTE() != null ? true : false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return null;
	}
}
