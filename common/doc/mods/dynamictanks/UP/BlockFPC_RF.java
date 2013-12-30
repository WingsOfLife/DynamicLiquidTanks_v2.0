package doc.mods.dynamictanks.UP;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.client.ClientProxy;

public class BlockFPC_RF extends BlockContainer {

	public BlockFPC_RF(int blockID)
	{
		super(blockID, Material.iron);
		setUnlocalizedName("dynamictanks.blocks.blockFluidPowerCondenserRF");
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
		setBlockBounds(0f, 0f, 0f, 1f, .75f, 1f);
		setHardness(1.5f);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new FPCTileEntity_RF();
	}

	@Override
	public void onBlockClicked(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer)
	{
		if (par1World.isRemote) {
			FPCTileEntity_RF fpcTile = (FPCTileEntity_RF) par1World.getBlockTileEntity(x, y, z);
			par5EntityPlayer.addChatMessage("Fluid: " + fpcTile.fluidPower.getFluidAmount() + " mB");
			par5EntityPlayer.addChatMessage("Power: " + fpcTile.storage.getEnergyStored() + " RF");
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
		
		ItemStack heldItem = player.inventory.getCurrentItem();
        if (heldItem != null)
        {
            FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(player.getCurrentEquippedItem());
            FPCTileEntity_Basic logic = (FPCTileEntity_Basic) world.getBlockTileEntity(x, y, z);
            if (liquid != null)
            {
                int amount = logic.fill(ForgeDirection.UNKNOWN, liquid, false);
                if (amount == liquid.amount)
                {
                    logic.fill(ForgeDirection.UNKNOWN, liquid, true);
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, consumeItem(heldItem));
                    return true;
                }
                else
                    return true;
            }
            else if (FluidContainerRegistry.isBucket(heldItem))
            {
                FluidStack fillFluid = logic.fluidPower.getFluid();//getFluid();
                ItemStack fillStack = FluidContainerRegistry.fillFluidContainer(fillFluid, heldItem);
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
	 * Custom Render
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
        return ClientProxy.ductRender;
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
    
    /*
     * Metadata
     */
    @Override
	public int damageDropped (int metadata) {
		return metadata;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		for (int ix = 0; ix < 2; ix++) {
			subItems.add(new ItemStack(this, 1, ix));
		}
	}
}
