package doc.mods.dynamictanks.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import doc.mods.dynamictanks.tileentity.ModifierTileEntity;

public class ContainerAsset extends Container {

	public ContainerAsset(InventoryPlayer inventoryPlayer, ModifierTileEntity modTile) {

		bindPlayerInventory(inventoryPlayer);
		bindFluidSlots(modTile);
		bindBucketSlots(modTile);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {

		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		if (slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			if (slot < 0)
				if (!this.mergeItemStack(stackInSlot, 0, 35, true))
				{
					return null;
				}
				else if (!this.mergeItemStack(stackInSlot, 0, 9, false))
				{
					return null;
				}

			if (stackInSlot.stackSize == 0)
			{
				slotObject.putStack(null);
			}
			else
			{
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize)
			{
				return null;
			}

			slotObject.onPickupFromSlot(player, stackInSlot);
		}

		return stack;
	}

	/*
	 * Misc Methods
	 */

	private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {

		for (int i = 0; i < 3; i++)
			for (int k = 0; k < 9; k++)
				addSlotToContainer(new Slot(inventoryPlayer, k + i * 9 + 9, 8 + k * 18, 111 + i * 18));

		for (int j = 0; j < 9; j++)
			addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 169));
	}

	private void bindFluidSlots(ModifierTileEntity modTile) {

		for (int i = 0; i < 5; i++)
			for (int k = 0; k < 6; k++) {
			
				int test = k + i * 6;
				addSlotToContainer(new FluidSlot(modTile, modTile, k + i * 6, 17 + k * 18, -20 + i * 18));
			}
	}
	
	private void bindBucketSlots(ModifierTileEntity modTile) {
		
		for (int i = 31; i < 37; i++)
			addSlotToContainer(new BucketSlot(modTile, i, 8 + (i - 30) * 18 - 9, 75));
	}
}
