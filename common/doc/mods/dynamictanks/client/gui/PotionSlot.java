package doc.mods.dynamictanks.client.gui;

import doc.mods.dynamictanks.items.ItemManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PotionSlot extends Slot {

	public PotionSlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		if (itemStack.itemID == Item.potion.itemID || itemStack.itemID == ItemManager.mixedPotion.itemID)
			return true;
		return false;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
}
