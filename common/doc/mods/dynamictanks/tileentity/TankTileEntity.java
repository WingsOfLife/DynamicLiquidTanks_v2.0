package doc.mods.dynamictanks.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.client.render.RendererHelper;

public class TankTileEntity extends CountableTileEntity implements IFluidHandler {

	/*
	 * Controller Sync Vars
	 */
	protected int[] ControllerCoords = { -1, -1, -1 }; // coords of tank's
	// controllers

	/*
	 * Self Vars
	 */

	protected int[] camoMeta = { -1, 0 };	
	protected int dyeIndex = -1;

	public TankTileEntity() {}

	public TankTileEntity(int maxTickCount) {
		this.maxTickCount = maxTickCount;
	}

	/*
	 * Self Methods
	 */

	public boolean hasCamo() {
		return camoMeta[0] != -1;
	}

	public int[] getCamo() {
		return camoMeta;
	}

	public int[] getControllerCoords() {
		return ControllerCoords;
	}

	public int getDye() {
		return dyeIndex;
	}

	public boolean hasController() {
		return ControllerCoords != null && ControllerCoords[0] != -1;
	}

	public void setControllerPos(int[] locs) {
		if (!hasController()) {
			for (int i = 0; i < 3; i++)
				ControllerCoords[i] = locs[i];				
		}
	}

	public int getLayer() {
		return hasController() ? (yCoord - RendererHelper.smallestIndex(getControllerTE().getNeighbors())) + 1 : -1;
	}

	public float amntToRender() {
		if (!hasController() || getControllerTE().getAllLiquids().isEmpty()) 
			return -1;

		float amnt = getControllerTE().getAllLiquids().get(getControllerTE().getLiquidIndex()).getFluidAmount();
		float cap = getControllerTE().getPerLayer();

		if (amnt > (cap * getLayer()))
			return worldObj.getBlockId(xCoord, yCoord + 1, zCoord) != 0 ? 1.00f : 0.999f;

		if (amnt < (cap * getLayer())) {
			float leftOver = (cap * getLayer()) - amnt;
			return 1.0f - leftOver / cap;
		}

		return worldObj.getBlockId(xCoord, yCoord + 1, zCoord) != 0 ? 1.00f : 0.999f;
	}

	public void setCamo(int blockID) {
		camoMeta[0] = blockID;
	}

	public void setCamo(int blockID, int meta) {
		camoMeta[0] = blockID;
		camoMeta[1] = meta;
	}

	public void setDye(int meta) {
		dyeIndex = meta;
	}

	public boolean searchForController() {
		TankTileEntity tankTE = null;
		ControllerTileEntity controllerTE = null;

		int currentX = xCoord;
		int currentY = yCoord;
		int currentZ = zCoord;
		int BlockID = 0;
		int[] loc = new int[3];
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			loc[0] = currentX - dir.offsetX;
			loc[1] = currentY - dir.offsetY;
			loc[2] = currentZ - dir.offsetZ;
			BlockID = worldObj.getBlockId(loc[0], loc[1], loc[2]);
			if (BlockID == BlockManager.BlockTankController.blockID) {
				controllerTE = (ControllerTileEntity) worldObj.getBlockTileEntity(loc[0], loc[1], loc[2]);				
				controllerTE.addNeighbor(new int[] { currentX, currentY, currentZ });
				setControllerPos(loc);
				return true;
			} else if (BlockID == BlockManager.BlockTank.blockID && !hasController()) {
				tankTE = (TankTileEntity) worldObj.getBlockTileEntity(loc[0], loc[1], loc[2]);
				if (tankTE.hasController()) {
					controllerTE = (ControllerTileEntity) worldObj.getBlockTileEntity(tankTE.getControllerCoords()[0], tankTE.getControllerCoords()[1], tankTE.getControllerCoords()[2]);
					if (controllerTE != null) {
						setControllerPos(tankTE.ControllerCoords);
						controllerTE.addNeighbor(new int[] { currentX, currentY, currentZ });
						return true;
					}
				}
			}
		}
		return false;
	}

	public ControllerTileEntity getControllerTE() {
		return hasController() ? (ControllerTileEntity) worldObj.getBlockTileEntity(ControllerCoords[0], ControllerCoords[1], ControllerCoords[2]) : null;
	}

	/*
	 * TileEntity Methods
	 */
	@Override
	public void updateEntity() {		
		if (worldObj.isRemote) { // client side
			doCount();

			if (countMet()) { // perform events every maxTickCount
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				/*if (!hasController()) { //check if already has controller
					searchForController();
				}*/
			}
		}

		if (!worldObj.isRemote) { // server side
			doCount();

			if (countMet()) { // perform events every maxTickCount
				if (!hasController()) { //check if already has controller
					searchForController();
				}
			}
		}
	}

	/*
	 * Syncing Methods
	 */

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		ControllerCoords = tagCompound.getIntArray("controllerLoc");
		camoMeta = tagCompound.getIntArray("camo");
		dyeIndex = tagCompound.getInteger("dye");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setIntArray("controllerLoc", ControllerCoords);
		tagCompound.setIntArray("camo", camoMeta);
		tagCompound.setInteger("dye", dyeIndex);
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

	/*
	 * IFluidHandler
	 */

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return getControllerTE() != null ? getControllerTE().fill(from, resource, doFill) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return getControllerTE() != null ? getControllerTE().drain(from, resource, doDrain) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return getControllerTE() != null ? getControllerTE().drain(from, maxDrain, doDrain) : null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return null;
	}

}
