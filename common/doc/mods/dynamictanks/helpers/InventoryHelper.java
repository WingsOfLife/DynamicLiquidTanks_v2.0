package doc.mods.dynamictanks.helpers;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class InventoryHelper {

	public static boolean inventoryHasRoom(IInventory inventory) {
		if (inventory != null) {
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				if (inventory.getStackInSlot(i) == null)
					return true;
			}
		}

		return false;
	}

	public static void insertItem(ItemStack stack, IInventory inventory) {
		if(inventoryHasRoom(inventory) && stack != null ) {
			for (int i = 0; i < inventory.getSizeInventory(); i++) {				
				if (ItemStack.areItemStacksEqual(inventory.getStackInSlot(i), stack) && stack.isStackable()) {
					if ((inventory.getStackInSlot(i).stackSize + stack.stackSize) <= 64) {
						inventory.setInventorySlotContents(i, new ItemStack(stack.getItem(), inventory.getStackInSlot(i).stackSize + stack.stackSize));
						break;
					}
				}

				if (inventory.getStackInSlot(i) == null) {
					inventory.setInventorySlotContents(i, stack);
					break;				
				}
			}
		}
	}

	public static ItemStack getFilledAlternative(ItemStack stack, FluidStack fluidStack) {
		if (stack != null && FluidContainerRegistry.isEmptyContainer(stack)) {
			ItemStack fillStack = FluidContainerRegistry.fillFluidContainer(fluidStack, stack);
			return fillStack;
		}

		/*if (stack != null && FluidContainerRegistry.isBucket(stack)) {
			ItemStack fillStack = FluidContainerRegistry.fillFluidContainer(fluidStack, stack);
			return fillStack;
		}*/
		return null;
	}

	public static ItemStack consumeItem(ItemStack stack)
	{
		if (stack.stackSize == 1)
		{
			return null;
		}
		else
		{
			stack.splitStack(1);
			return stack;
		}
	}

}
