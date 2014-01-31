package doc.mods.dynamictanks.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.PotionMixerTileEntity;

public class ContainerMixer extends Container
{
    protected PotionMixerTileEntity mixerTile;

    public ContainerMixer(InventoryPlayer inventoryPlayer, PotionMixerTileEntity upgradeTE)
    {
        mixerTile = upgradeTE;
        addSlotToContainer(new PotionSlot(upgradeTE, 0, 64, 14));
        addSlotToContainer(new PotionSlot(upgradeTE, 1, 64, 44));
        addSlotToContainer(new Slot(upgradeTE, 2, 96, 29));
        //addSlotToContainer(new PotionSlot(upgradeTE, 3, 112, 29));
        bindPlayerInventory(inventoryPlayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return true; //controllerTile.isUseableByPlayer(entityPlayer);
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

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        for (int i = 0; i < 3; i++)
            for (int k = 0; k < 9; k++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, k + i * 9 + 9, 8 + k * 18, 78 + i * 18));
            }

        for (int j = 0; j < 9; j++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 136));
        }
    }
}
