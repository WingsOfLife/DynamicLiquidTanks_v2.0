package doc.mods.dynamictanks.Fluids.tileentity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import doc.mods.dynamictanks.helpers.CPotionHelper;
import doc.mods.dynamictanks.helpers.NumberHelper;
import doc.mods.dynamictanks.tileentity.CountableTileEntity;

public class ClensingTileEntity extends CountableTileEntity {

	protected float damageHealed = 0;
	public static final float maxHealing = CPotionHelper.maxExistance / 4;
	public Random par5Random = new Random();
	
	public ClensingTileEntity() {
		maxTickCount = 40;
	}

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
			if(worldObj.isRemote) {
				float f =  (float) xCoord + par5Random.nextFloat() * 0.1F;
                float f1 = (float) yCoord + par5Random.nextFloat();
                float f2 = (float) zCoord + par5Random.nextFloat();
                worldObj.spawnParticle("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
			}
		}

		doCount();
		if (countMet()) {
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				int coordX = xCoord - dir.offsetX;
				int coordY = yCoord - dir.offsetY;
				int coordZ = zCoord - dir.offsetZ;

				TileEntity inventory = worldObj.getBlockTileEntity(coordX, coordY, coordZ);
				if (inventory instanceof IInventory) {
					IInventory search = (IInventory) inventory;
					for (int i = 0; i < search.getSizeInventory(); i++) {
						ItemStack item = search.getStackInSlot(i);
						if (item != null && item.getItemDamage() < item.getMaxDamage()) {
							ItemStack newItem = new ItemStack(item.itemID, 1, item.getItemDamage() - NumberHelper.inRange(1, 3));
							damageHealed = damageHealed + NumberHelper.inRange(1, 15);
							//System.out.println(damageHealed + " / " + maxHealing);
							search.setInventorySlotContents(i, newItem);
						}
					}
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
