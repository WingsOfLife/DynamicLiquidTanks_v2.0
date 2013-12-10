package doc.mods.dynamictanks.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;
import doc.mods.dynamictanks.items.ItemManager;

public class CraftingHandler implements ICraftingHandler
{
    @Override
    public void onCrafting(EntityPlayer player, ItemStack item,	IInventory craftMatrix)
    {
        boolean containsDiamond = false;

        for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
            if (craftMatrix.getStackInSlot(i) != null && craftMatrix.getStackInSlot(i).itemID == Item.diamond.itemID)
            {
                containsDiamond = true;
            }

        for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
        {
            if (craftMatrix.getStackInSlot(i) != null)
            {
                ItemStack j = craftMatrix.getStackInSlot(i);

                if (j.getItem() != null && j.getItem() == ItemManager.hammerItem)
                {
                    ItemStack k = new ItemStack(ItemManager.hammerItem, 2, (j.getItemDamage() + 1));

                    if (k.getItemDamage() >= k.getMaxDamage() || containsDiamond)
                    {
                        k.stackSize--;
                    }

                    craftMatrix.setInventorySlotContents(i, k);
                }
            }
        }
    }

    @Override
    public void onSmelting(EntityPlayer player, ItemStack item)
    {
    }
}