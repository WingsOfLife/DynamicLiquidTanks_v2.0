package doc.mods.dynamictanks.client.gui;

import doc.mods.dynamictanks.tileentity.ModifierTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class BucketSlot extends Slot {
	
	public BucketSlot(IInventory par1iInventory, int par2, int par3, int par4) {

		super(par1iInventory, par2, par3, par4);
	}

	public boolean canTakeStack(EntityPlayer par1EntityPlayer) {

		return true;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {

		return FluidContainerRegistry.isBucket(par1ItemStack);
	}
}
