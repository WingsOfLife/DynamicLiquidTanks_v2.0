package doc.mods.dynamictanks.helpers;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.items.ItemManager;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class ItemHelper
{
	public static void spawnItem(ItemStack itemStack, World worldObj, int x, int y, int z)
	{
		Random rand = new Random();
		float rx = rand.nextFloat() * 0.8F + 0.1F;
		float ry = rand.nextFloat() * 0.8F + 0.1F;
		float rz = rand.nextFloat() * 0.8F + 0.1F;
		EntityItem entityItem = new EntityItem(worldObj,
				x + rx, y + ry, z + rz,
				new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage()));

		if (itemStack.hasTagCompound())
		{
			entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
		}

		float factor = 0.05F;
		entityItem.motionX = rand.nextGaussian() * factor;
		entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
		entityItem.motionZ = rand.nextGaussian() * factor;
		worldObj.spawnEntityInWorld(entityItem);
		itemStack.stackSize = 0;
	}

	public static void dropItem(int itemID, int meta, World world, int x, int y, int z)
	{
		EntityItem el = new EntityItem(world, x, y, z, new ItemStack(itemID, 1, meta));
		
		if (itemID == BlockManager.BlockTankController.blockID)
			el.getEntityItem().setItemName("Empty Tank Structure");
		world.spawnEntityInWorld(el);
	}

	public static boolean removeBlockByPlayer(World world, int x, int y, int z)
	{
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
		{
			ItemStack itemStack = new ItemStack(BlockManager.BlockTankController);
			ControllerTileEntity controller = (ControllerTileEntity) world.getBlockTileEntity(x, y, z);

			if (controller != null)
			{
				if (FluidHelper.hasLiquid(controller))
					itemStack.setItemDamage(Math.round(controller.getAllLiquids().get(0).getFluidAmount() * 16) + controller.getAllLiquids().size() * 16);

				NBTTagCompound nbt = new NBTTagCompound();
				controller.writeToNBT(nbt);
				itemStack.setTagCompound(nbt);

				itemStack.stackTagCompound.setInteger("xLoc", x);
				itemStack.stackTagCompound.setInteger("yLoc", y);
				itemStack.stackTagCompound.setInteger("zLoc", z);
			}

			float f = 0.7F;
			double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, itemStack);
			
			entityitem.getEntityItem().setItemName("Primed Tank Structure");
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}

		return world.setBlockToAir(x, y, z);
	}

	public static ItemStack getControllerInformation(World world, int x, int y, int z) {

		ItemStack itemStack = new ItemStack(ItemManager.memoryItem);
		ControllerTileEntity controller = (ControllerTileEntity) world.getBlockTileEntity(x, y, z);

		if (controller != null) {

			if (FluidHelper.hasLiquid(controller))
				itemStack.setItemDamage(Math.round(controller.getAllLiquids().get(0).getFluidAmount() * 16) * controller.getAllLiquids().size() * 16);

			NBTTagCompound nbt = new NBTTagCompound();
			controller.writeToNBT(nbt);
			itemStack.setTagCompound(nbt);


			return itemStack;
		}
		
		return null;
	}

	public static ItemStack writeFluidHandler(World world, int x, int y, int z) {

		ItemStack itemStack = new ItemStack(ItemManager.memoryItem);
		TileEntity controller = world.getBlockTileEntity(x, y, z);

		if (controller != null) {

			if (FluidHelper.fluidHandlerFilled((IFluidHandler) controller));
				itemStack.setItemDamage(Math.round(((IFluidHandler) controller).getTankInfo(ForgeDirection.UP)[0].fluid.amount * 16) * 16);

			NBTTagCompound nbt = new NBTTagCompound();
			controller.writeToNBT(nbt);
			itemStack.setTagCompound(nbt);

			((IFluidHandler) controller).drain(ForgeDirection.UP, ((IFluidHandler) controller).getTankInfo(ForgeDirection.UP)[0].fluid.amount, true);
			
			return itemStack;
		}
		
		return null;
	}
}
