package doc.mods.dynamictanks.block;

import java.util.Arrays;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;

public class BlockTank extends BlockContainer {

	protected BlockTank(int par1) {
		super(par1, Material.glass);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TankTileEntity();
	}
	
	@Override
	public void breakBlock(World par1World, int x, int y, int z, int par5, int par6) {		
		TankTileEntity tankTile = (TankTileEntity) par1World.getBlockTileEntity(x, y, z);
		ControllerTileEntity controllerTile = (ControllerTileEntity) par1World.getBlockTileEntity(tankTile.getControllerCoords()[0],
				tankTile.getControllerCoords()[1], tankTile.getControllerCoords()[2]);
		int[] loc = { x, y, z };
		
		for (int i = 0; i < controllerTile.getNeighbors().size(); i++)
			if (Arrays.equals(loc, controllerTile.getNeighbors().get(i)))
				controllerTile.getNeighbors().remove(i);
		
		super.breakBlock(par1World, x, y, z, par5, par6);
	}
	
	@Override
	public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
		ItemStack heldItem = player.inventory.getCurrentItem();
		TankTileEntity conTE = (TankTileEntity) world.getBlockTileEntity(x, y, z);

		if (heldItem != null)
		{
			FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(player.getCurrentEquippedItem());
			if (liquid != null)
			{
				int amount = conTE.fill(ForgeDirection.UNKNOWN, liquid, false);
				if (amount == liquid.amount)
				{
					conTE.fill(ForgeDirection.UNKNOWN, liquid, true);
					if (!player.capabilities.isCreativeMode)
						player.inventory.setInventorySlotContents(player.inventory.currentItem, consumeItem(heldItem));
					return true;
				}
				else
					return true;
			}
			/*else if (FluidContainerRegistry.isBucket(heldItem) && conTE != null)
			{
				//ILiquidTank[] tanks = logic.getTanks(ForgeDirection.UNKNOWN);
				FluidStack fillLiquid = conTE.tank.getFluid();
				ItemStack fillStack = FluidContainerRegistry.fillFluidContainer(fillLiquid, heldItem);
				if (fillStack != null)
				{
					logic.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getFluidForFilledItem(fillStack).amount, true);
					if (!player.capabilities.isCreativeMode)
					{
						if (heldItem.stackSize == 1)
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, fillStack);
						}
						else
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemUtils.consumeItem(heldItem));

							if (!player.inventory.addItemStackToInventory(fillStack))
							{
								player.dropPlayerItem(fillStack);
							}
						}
					}
					return true;
				}
				else
				{
					return true;
				}
			}*/
		}
		return false;
	}

	public static ItemStack consumeItem (ItemStack stack)
	{
		if (stack.stackSize == 1)
		{
			if (stack.getItem().hasContainerItem())
				return stack.getItem().getContainerItemStack(stack);
			else
				return null;
		}
		else
		{
			stack.splitStack(1);
			return stack;
		}
	}

}
