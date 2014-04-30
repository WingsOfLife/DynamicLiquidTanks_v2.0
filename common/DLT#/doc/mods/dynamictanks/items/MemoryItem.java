package doc.mods.dynamictanks.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.block.BlockTankController;
import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.helpers.StringHelper;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class MemoryItem extends Item {

	@SideOnly(Side.CLIENT)
	Icon fullIcon;
	
	public MemoryItem(int itemID) {

		super(itemID);
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heldItem, World world, EntityPlayer player) {

		if (!player.isSneaking())
			return heldItem;

		MovingObjectPosition position = this.getMovingObjectPositionFromPlayer(player.worldObj, player, false);
		TileEntity checkAgainst = null;

		if (position == null)			
			return heldItem;


		int clickX = position.blockX;
		int clickY = position.blockY;
		int clickZ = position.blockZ;

		TileEntity compareTile = world.getBlockTileEntity(clickX, clickY, clickZ);

		if (compareTile != null && (compareTile instanceof IFluidHandler)) {

			IFluidHandler conTE = (IFluidHandler) compareTile;

			if (heldItem.stackTagCompound == null) {

				ItemStack returnItem = heldItem.copy();

				BlockTankController.consumeItem(player.inventory.mainInventory[player.inventory.currentItem]);
				returnItem.stackTagCompound = new NBTTagCompound();

				FluidStack containedFluid = FluidHelper.getFluidFromHandler(conTE);
				
				if (containedFluid == null)
					return heldItem;
				
				containedFluid.writeToNBT(returnItem.stackTagCompound);
				FluidHelper.setAmntForHandler(conTE, 0);
				world.markBlockForUpdate(clickX, clickY, clickZ);

				returnItem.setItemDamage(1);
				
				return returnItem;
			} else if (heldItem.stackTagCompound != null) {

				FluidStack fillStack = FluidStack.loadFluidStackFromNBT(heldItem.stackTagCompound);
				FluidStack tempFill = new FluidStack(fillStack.fluidID, 1);
				FluidStack contained = FluidHelper.getFluidFromHandler(conTE);
				
				if (contained == null && fillStack.amount <= FluidHelper.getHandlerCapacity(conTE)) {
					
					conTE.fill(ForgeDirection.UNKNOWN, tempFill, true); //check same fluid. Merge amounts
					FluidHelper.setAmntForHandler(conTE, fillStack.amount);
					
					player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(ItemManager.memoryItem);
					
					return heldItem;
				}
				
				if ((contained != null && !(contained.isFluidEqual(fillStack))) ||
						FluidHelper.getAmntFromHandler(conTE) + fillStack.amount > FluidHelper.getHandlerCapacity(conTE))
					return heldItem;
				
				conTE.fill(ForgeDirection.UNKNOWN, tempFill, true); //check same fluid. Merge amounts
				FluidHelper.setAmntForHandler(conTE, fillStack.amount + FluidHelper.getAmntFromHandler(conTE) - 1);
				
				player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(ItemManager.memoryItem);
			}
		}

		return heldItem;
	}

	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {

		ControllerTileEntity controller = new ControllerTileEntity();

		if (itemstack.stackTagCompound != null) {
			Object name = list.get(0);
			list.set(0, "" + EnumChatFormatting.YELLOW + "(Full) " + EnumChatFormatting.RESET + name);
			list.add(1, EnumChatFormatting.RESET + "Hold " + EnumChatFormatting.GOLD + EnumChatFormatting.ITALIC + "SHIFT" + EnumChatFormatting.RESET + " for contents.");
		}

		if (itemstack.stackTagCompound != null && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {

			list.remove(1);

			FluidStack fillStack = FluidStack.loadFluidStackFromNBT(itemstack.stackTagCompound);

			if (fillStack != null) {

				list.add("Contains: " + StringHelper.Cap(fillStack.getFluid().getName()));
				list.add("Amount: " + fillStack.amount + " mB");
			}
		}
	}
	
	@Override
    public void registerIcons(IconRegister register) {
		
        itemIcon = register.registerIcon("dynamictanks:storageCell");
        fullIcon = register.registerIcon("dynamictanks:storageCell_Full");
    }
	
	@Override
    public Icon getIconFromDamage(int i) {
		
		if (i == 1)
			return fullIcon;
		
		return itemIcon;
	}
}
