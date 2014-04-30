package doc.mods.dynamictanks.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.client.ClientProxy;
import doc.mods.dynamictanks.helpers.ConnectedTexturesHelper;
import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.helpers.ItemHelper;
import doc.mods.dynamictanks.items.ItemManager;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;

public class BlockTankController extends BlockContainer
{
	//@SideOnly(Side.CLIENT)
	//private Icon faceIcon;

	BlockTankController(int par1)
	{
		super(par1, Material.rock);
		setHardness(2.0F);
		setUnlocalizedName("dynamictanks.block.controller");
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
	}

	/*
	 * Icons
	 */

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconReg)
	{
		blockIcon = iconReg.registerIcon("dynamictanks:" + "valve_still");
		//faceIcon = iconReg.registerIcon("dynamictanks:" + "ControllerFront");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		/*if (meta == 0 && side == 4)
		{
			return faceIcon;
		}

		if (side == 0 || side == 1)
		{
			return blockIcon;
		}
		else if (side != meta)
		{
			return blockIcon;
		}
		else
		{
			return faceIcon;
		}*/
		
		return blockIcon;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
	}

	@Override
	public void breakBlock(World par1World, int x, int y, int z, int par5, int par6)
	{
		//Drop Item
		ControllerTileEntity controller = (ControllerTileEntity) par1World.getBlockTileEntity(x, y, z);

		if (controller != null && FluidHelper.hasLiquid(controller))
		{
			ItemHelper.removeBlockByPlayer(par1World, x, y, z);
		}
		else
		{
			ItemHelper.dropItem(BlockManager.BlockTankController.blockID, 0, par1World, x, y, z);
		}

		//clear neighbors
		if (controller != null && !controller.getNeighbors().isEmpty())
		{
			for (int[] arr : controller.getNeighbors())
			{
				TileEntity tile = par1World.getBlockTileEntity(arr[0], arr[1], arr[2]);

				if (tile != null && tile instanceof TankTileEntity)
				{
					((TankTileEntity) tile).invalidate();
				}//setControllerPos(new int[] { -1, -1, -1 });
			}
		}

		//break
		super.breakBlock(par1World, x, y, z, par5, par6);;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.clear();
		return ret;
	}

	private void setDefaultDirection(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			int zNeg = world.getBlockId(x, y, z - 1);
			int zPos = world.getBlockId(x, y, z + 1);
			int xNeg = world.getBlockId(x - 1, y, z);
			int xPos = world.getBlockId(x + 1, y, z);
			byte meta = 3;

			if (Block.opaqueCubeLookup[xNeg] && !Block.opaqueCubeLookup[xPos])
			{
				meta = 5;
			}

			if (Block.opaqueCubeLookup[xPos] && !Block.opaqueCubeLookup[xNeg])
			{
				meta = 4;
			}

			if (Block.opaqueCubeLookup[zNeg] && !Block.opaqueCubeLookup[zPos])
			{
				meta = 3;
			}

			if (Block.opaqueCubeLookup[zPos] && !Block.opaqueCubeLookup[zNeg])
			{
				meta = 2;
			}

			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
	{
		int rotation = MathHelper.floor_double((double)(entity.rotationYaw * 4F / 360F) + 0.5D) & 3;

		//set rotation
		if (rotation == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (rotation == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (rotation == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (rotation == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}

		//System.out.println(x + ", " + y + ", " + z);

		//read NBT
		if (itemstack.stackTagCompound != null) {

			if (itemstack.stackTagCompound.hasKey("xLoc"))
				if (itemstack.stackTagCompound.getInteger("xLoc") == x && 
					itemstack.stackTagCompound.getInteger("yLoc") == y &&
					itemstack.stackTagCompound.getInteger("zLoc") == z) {
					
					ControllerTileEntity setInfo = (ControllerTileEntity) world.getBlockTileEntity(x, y, z);
					setInfo.readFromNBT(itemstack.stackTagCompound);
				}
		}

		super.onBlockPlacedBy(world, x, y, z, entity, itemstack);
	}

	/*
	 * Misc
	 */
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new ControllerTileEntity();
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		int i1 = par1World.getBlockMetadata(par2, par3, par4);
		boolean flag = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
		boolean flag1 = (i1 & 8) != 0;
		ControllerTileEntity currentTile = (ControllerTileEntity) par1World.getBlockTileEntity(par2, par3, par4);

		/*if (flag && !flag1)
		{
			currentTile.nextLiquidIndex();
			par1World.setBlockMetadataWithNotify(par2, par3, par4, i1 | 8, 4);
		}
		else if (!flag && flag1)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, i1 & -9, 4);
		}*/
	}

	@Override
	public void onBlockClicked(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer)
	{
		ItemStack heldItem = par5EntityPlayer.inventory.getCurrentItem();
		ControllerTileEntity controller = (ControllerTileEntity) par1World.getBlockTileEntity(x, y, z);
		/* Potion Collision */
		FluidStack currentLiquid = controller.getAllLiquids().get(controller.getLiquidIndex()).getFluid();

		/*if (heldItem == null && currentLiquid != null && currentLiquid.isFluidEqual(new FluidStack(FluidManager.potionFluid, FluidContainerRegistry.BUCKET_VOLUME)))
        {
            if (par5EntityPlayer != null && controller.getPotion() != -1 &&
                    (currentLiquid.amount - (FluidContainerRegistry.BUCKET_VOLUME / 10)) >= 0)
            {
                //PotionEffectHelper.applyPotionEffects((EntityPlayer) par5EntityPlayer, controller.getPotion(), 10, false);
                controller.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.BUCKET_VOLUME / 10, true);

                if (currentLiquid.amount == 0)
                {
                    controller.setPotion(-1);
                }
            }
        }*/

		/* Dye Or Camo */
		if (par5EntityPlayer.isSneaking())
		{
			if (heldItem != null && heldItem.itemID == Item.dyePowder.itemID)
			{
				controller.setDyeColor(heldItem.getItemDamage());
			}
			else if (heldItem != null && heldItem.itemID < 4096 && Block.blocksList[heldItem.itemID] != null)
			{
				controller.setCamo(heldItem.itemID, heldItem.getItemDamage());
			}
			else if (heldItem == null)
			{
				controller.setCamo(-1, 0);
				controller.setDyeColor(-1);
			}

			par1World.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ)
	{
		ItemStack heldItem = player.inventory.getCurrentItem();
		ControllerTileEntity conTE = (ControllerTileEntity) world.getBlockTileEntity(x, y, z);

		/*if (heldItem != null && heldItem.itemID == Item.potion.itemID)
        {
            FluidStack potion = new FluidStack(FluidManager.potionFluid, FluidContainerRegistry.BUCKET_VOLUME);
            int amount = conTE.fill(ForgeDirection.UNKNOWN, potion, false);

            if (amount == potion.amount && (conTE.getPotion() == -1 || conTE.getPotion() == heldItem.getItemDamage()))
            {
                conTE.fill(ForgeDirection.UNKNOWN, potion, true);
                ItemHelper.removeSingleItem(heldItem);
                player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle, 1));
                conTE.setPotion(heldItem.getItemDamage());
                return true;
            }
        }
        else if (heldItem != null && heldItem.itemID == Item.glassBottle.itemID && conTE.getPotion() != -1 && conTE.getAllLiquids().get(conTE.getLiquidIndex()).getFluid().isFluidEqual(new FluidStack(FluidManager.potionFluid, FluidContainerRegistry.BUCKET_VOLUME)))
        {
            FluidStack fillLiquid = conTE.getAllLiquids().get(conTE.getLiquidIndex()).getFluid();

            if (conTE.getAllLiquids().get(conTE.getLiquidIndex()).getFluidAmount() < 1000)
            {
                return false;
            }

            conTE.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.BUCKET_VOLUME, true);
            ItemHelper.removeSingleItem(heldItem);
            player.inventory.addItemStackToInventory(new ItemStack(Item.potion, 1, conTE.getPotion()));

            if (conTE.getAllLiquids().get(conTE.getLiquidIndex()).getFluidAmount() == 0)
            {
                conTE.setPotion(-1);
            }
        }
        else */if (heldItem != null && FluidContainerRegistry.isContainer(heldItem))
        {
        	FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(player.getCurrentEquippedItem());

        	if (liquid != null)
        	{
        		int amount = conTE.fill(ForgeDirection.UNKNOWN, liquid, false);

        		if (amount == liquid.amount)
        		{
        			conTE.fill(ForgeDirection.UNKNOWN, liquid, true);

        			if (!player.capabilities.isCreativeMode)
        			{
        				player.inventory.setInventorySlotContents(player.inventory.currentItem, consumeItem(heldItem));
        			}

        			return true;
        		}
        		else
        		{
        			return true;
        		}
        	}
        	else if (FluidContainerRegistry.isBucket(heldItem) && conTE != null)
        	{
        		if (conTE.getLiquidIndex() > conTE.getAllLiquids().size())
        		{
        			conTE.setLiquidIndex(conTE.getAllLiquids().size());
        		}

        		FluidStack fillLiquid = conTE.getAllLiquids().get(conTE.getLiquidIndex()).getFluid();
        		ItemStack fillStack = FluidContainerRegistry.fillFluidContainer(fillLiquid, heldItem);

        		if (fillStack != null)
        		{
        			conTE.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getFluidForFilledItem(fillStack).amount, true);

        			if (!player.capabilities.isCreativeMode)
        			{
        				if (heldItem.stackSize == 1)
        				{
        					player.inventory.setInventorySlotContents(player.inventory.currentItem, fillStack);
        				}
        				else
        				{
        					player.inventory.setInventorySlotContents(player.inventory.currentItem, consumeItem(heldItem));

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
        	}
        } /*else if (heldItem != null && heldItem.itemID == ItemManager.memoryItem.itemID) {

    		if (conTE != null && FluidHelper.hasLiquid(conTE) && heldItem.stackTagCompound == null) {    			
    		
    			player.inventory.mainInventory[player.inventory.currentItem] = null;
    			player.inventory.addItemStackToInventory(ItemHelper.getControllerInformation(world, x, y, z));
    			
    			for (FluidTank fluid : conTE.getAllLiquids())
    				if (fluid.getFluid() != null)
    					fluid.drain(fluid.getFluidAmount(), true);
    		} else if (conTE != null && !FluidHelper.hasLiquid(conTE)) {
    			
    			if (heldItem.stackTagCompound == null)
    				return false;
    			
    			ControllerTileEntity setInfo = (ControllerTileEntity) world.getBlockTileEntity(x, y, z);
				setInfo.readFromNBT(heldItem.stackTagCompound);
				
				player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(ItemManager.memoryItem);
    		}
    		
    		return true;
        } */

        if (!FluidContainerRegistry.isContainer(heldItem))
        {
        	player.openGui(DynamicLiquidTanksCore.instance, 0, world, x, y, z);
        	return true;
        }

        return false;
	}

	public static ItemStack consumeItem(ItemStack stack)
	{
		if (stack.stackSize == 1)
		{
			if (stack.getItem().hasContainerItem())
			{
				return stack.getItem().getContainerItemStack(stack);
			}
			else
			{
				return null;
			}
		}
		else
		{
			stack.splitStack(1);
			return stack;
		}
	}

	/*
	 * Camo
	 */

	@Override
	@SideOnly(value = Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int dir)
	{
		TileEntity getTextures = world.getBlockTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		if (((ControllerTileEntity) getTextures).getCamo() != -1)
		{
			try
			{
				return ConnectedTexturesHelper.blockIcon(((ControllerTileEntity) getTextures).getCamo(), dir, ((ControllerTileEntity) getTextures).getCamoMeta());
			}
			catch (Exception e)
			{
				return setDefaultIcon(dir, meta);
			}
		}

		return setDefaultIcon(dir, meta);
	}

	@Override
	@SideOnly(value = Side.CLIENT)
	public final int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		TileEntity getTextures = world.getBlockTileEntity(x, y, z);
		ControllerTileEntity casted = ((ControllerTileEntity) getTextures);

		if (casted.getDyeColor() != -1)
		{
			return ItemDye.dyeColors[casted.getDyeColor()];
		}
		else
		{
			try
			{
				return Block.blocksList[casted.getCamo()].colorMultiplier(world, x, y, z);
			}
			catch (Throwable t)
			{
				return super.colorMultiplier(world, x, y, z);
			}
		}
	}

	public Icon setDefaultIcon(int dir, int meta) {
		
		return blockIcon;
	}
	
	/*
     * Textures
     */

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderType()
    {
        return ClientProxy.controlPass;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }

    @Override
    public boolean canRenderInPass(int pass)
    {
        ClientProxy.renderPass = pass;
        return true;
    }
}