package doc.mods.dynamictanks.block;

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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.ModifierTileEntity;

public class BlockModifier extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private Icon faceIcon;

	BlockModifier(int blockID) {

		super(blockID, Material.iron);
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
		setUnlocalizedName("dynamictanks.block.blockModifier");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {

		return new ModifierTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {

		ItemStack heldItem = player.inventory.getCurrentItem();
		ModifierTileEntity modTE = (ModifierTileEntity) world.getBlockTileEntity(x, y, z);
		
		if (heldItem != null && FluidContainerRegistry.isContainer(heldItem)) {
			
        	FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(player.getCurrentEquippedItem());

        	if (liquid != null) {
        		
        		int amount = modTE.fill(ForgeDirection.UNKNOWN, liquid, false);

        		if (amount == liquid.amount) {
        			
        			modTE.fill(ForgeDirection.UNKNOWN, liquid, true);

        			if (!player.capabilities.isCreativeMode)
        				player.inventory.setInventorySlotContents(player.inventory.currentItem, BlockTankController.consumeItem(heldItem));

        			return true;
        		}
        		else
        			return true;
        	}
		} 
		if (FluidContainerRegistry.isBucket(heldItem) && modTE != null) {
    		if (modTE.getDrain() > modTE.controlledAssets.size())
    			return false;

    		FluidStack fillLiquid = FluidHelper.getFluidFromHandler(modTE.controlledAssets.get(modTE.getDrain()).getFluidHandler());
    		ItemStack fillStack = FluidContainerRegistry.fillFluidContainer(fillLiquid, heldItem);

    		if (fillStack != null) {
    			modTE.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getFluidForFilledItem(fillStack).amount, true);

    			if (!player.capabilities.isCreativeMode) {
    				if (heldItem.stackSize == 1)
    					player.inventory.setInventorySlotContents(player.inventory.currentItem, fillStack);
    				else {
    					player.inventory.setInventorySlotContents(player.inventory.currentItem, BlockTankController.consumeItem(heldItem));

    					if (!player.inventory.addItemStackToInventory(fillStack))
    						player.dropPlayerItem(fillStack);
    				}
    			}

    			return true;
    		}
    		else
    			return true;
    	}
		
		player.openGui(DynamicLiquidTanksCore.instance, 0, world, x, y, z);
		return true;
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		int i1 = par1World.getBlockMetadata(par2, par3, par4);
		boolean flag = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
		boolean flag1 = (i1 & 8) != 0;
		
		ModifierTileEntity currentTile = (ModifierTileEntity) par1World.getBlockTileEntity(par2, par3, par4);

		if (flag && !flag1) {

			par1World.setBlockMetadataWithNotify(par2, par3, par4, i1 | 8, 4);
			currentTile.rescan();
		} else if (!flag && flag1)
			par1World.setBlockMetadataWithNotify(par2, par3, par4, i1 & -9, 4);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconReg) {

		blockIcon = iconReg.registerIcon("dynamictanks:potionDisperserTB");
		faceIcon = iconReg.registerIcon("dynamictanks:" + "managerFront");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		if (meta == 0 && side == 4)
			return faceIcon;

		if (side == 0 || side == 1)
			return blockIcon;
		else if (side != meta)
			return blockIcon;
		else
			return faceIcon;

		//return blockIcon;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {

		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
		
		if (world.getBlockTileEntity(x, y, z) instanceof ModifierTileEntity) {
		
			world.markBlockForUpdate(x, y, z);
			((ModifierTileEntity) world.getBlockTileEntity(x, y, z)).rescan();
		}
	}

	private void setDefaultDirection(World world, int x, int y, int z) {
		
		if (!world.isRemote) {

			int zNeg = world.getBlockId(x, y, z - 1);
			int zPos = world.getBlockId(x, y, z + 1);
			int xNeg = world.getBlockId(x - 1, y, z);
			int xPos = world.getBlockId(x + 1, y, z);
			byte meta = 3;

			if (Block.opaqueCubeLookup[xNeg] && !Block.opaqueCubeLookup[xPos])
				meta = 5;

			if (Block.opaqueCubeLookup[xPos] && !Block.opaqueCubeLookup[xNeg])
				meta = 4;

			if (Block.opaqueCubeLookup[zNeg] && !Block.opaqueCubeLookup[zPos])
				meta = 3;

			if (Block.opaqueCubeLookup[zPos] && !Block.opaqueCubeLookup[zNeg])
				meta = 2;

			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
		
		int rotation = MathHelper.floor_double((double)(entity.rotationYaw * 4F / 360F) + 0.5D) & 3;

		//set rotation
		if (rotation == 0)
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);

		if (rotation == 1)
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);

		if (rotation == 2)
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);

		if (rotation == 3)
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		
		super.onBlockPlacedBy(world, x, y, z, entity, itemstack);
	}
}
