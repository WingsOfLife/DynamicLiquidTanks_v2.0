package doc.mods.dynamictanks.client.gui;

import org.lwjgl.input.Keyboard;

import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.tileentity.ModifierTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

class FluidSlot extends Slot {

	private ModifierTileEntity modTile;

	FluidSlot(ModifierTileEntity modTile, IInventory par1iInventory, int par2, int par3, int par4) {

		super(par1iInventory, par2, par3, par4);
		this.modTile = modTile;
	}

	public boolean canTakeStack(EntityPlayer par1EntityPlayer) {

		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			modTile.setDrainIndex(this.getSlotIndex());
		
		if (this.getSlotIndex() > modTile.controlledAssets.size())
			return false;

		return FluidHelper.getAmntFromHandler(modTile.controlledAssets.get(getSlotIndex()).getFluidHandler()) - 
				FluidContainerRegistry.BUCKET_VOLUME >= 0 && modTile.atLeastOneBucket();
	}

	public boolean isItemValid(ItemStack par1ItemStack) {

		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
		
		FluidHelper.setAmntForHandler(modTile.controlledAssets.get(getSlotIndex()).getFluidHandler(),
				 FluidHelper.getAmntFromHandler(modTile.controlledAssets.get(getSlotIndex()).getFluidHandler()) - FluidContainerRegistry.BUCKET_VOLUME);
	}
}
