package doc.mods.dynamictanks.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class BlockTankController extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon faceIcon;
	
	public BlockTankController(int par1) {
		super(par1, Material.iron);
		setUnlocalizedName("Tank Controller");
	}
	
	/* 
	 * Icons
	 */

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconReg) {
		blockIcon = iconReg.registerIcon("dynamictanks:" + "Controller_Sides");
		faceIcon = iconReg.registerIcon("dynamictanks:" + "ControllerFront");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if(side == 0 || side == 1) 
			return blockIcon;
		else if(side != meta)
			return blockIcon;
		else 
			return faceIcon;

	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
	}

	private void setDefaultDirection(World world, int x, int y, int z) {
		if(!world.isRemote) {
			int zNeg = world.getBlockId(x, y, z - 1);
			int zPos = world.getBlockId(x, y, z + 1);
			int xNeg = world.getBlockId(x - 1, y, z);
			int xPos = world.getBlockId(x + 1, y, z);
			byte meta = 3;

			if(Block.opaqueCubeLookup[xNeg] && !Block.opaqueCubeLookup[xPos]) meta = 5;
			if(Block.opaqueCubeLookup[xPos] && !Block.opaqueCubeLookup[xNeg]) meta = 4;
			if(Block.opaqueCubeLookup[zNeg] && !Block.opaqueCubeLookup[zPos]) meta = 3;
			if(Block.opaqueCubeLookup[zPos] && !Block.opaqueCubeLookup[zNeg]) meta = 2;

			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
		int rotation = MathHelper.floor_double((double)(entity.rotationYaw * 4F / 360F) + 0.5D) & 3;

		if(rotation == 0)
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);

		if(rotation == 1)
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);

		if(rotation == 2)
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);

		if(rotation == 3)
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}
	
	/*
	 * Misc
	 */
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new ControllerTileEntity();
	}

	@Override
	public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
		ItemStack heldItem = player.inventory.getCurrentItem();
		ControllerTileEntity conTE = (ControllerTileEntity) world.getBlockTileEntity(x, y, z);

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
		if (stack.stackSize == 1) {
			if (stack.getItem().hasContainerItem())
				return stack.getItem().getContainerItemStack(stack);
			else
				return null;
		}
		else {
			stack.splitStack(1);
			return stack;
		}
	}
}