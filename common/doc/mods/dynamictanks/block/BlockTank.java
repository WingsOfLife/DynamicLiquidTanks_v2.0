package doc.mods.dynamictanks.block;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.client.ClientProxy;
import doc.mods.dynamictanks.helpers.ConnectedTexturesHelper;
import doc.mods.dynamictanks.helpers.ItemHelper;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;

public class BlockTank extends BlockContainer
{
    protected BlockTank(int par1)
    {
        super(par1, Material.glass);
        setHardness(1.0F);
        setUnlocalizedName("dynamictanks.block.tank");
        setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TankTileEntity();
    }

    @Override
    public void breakBlock(World par1World, int x, int y, int z, int par5, int par6)
    {
        TileEntity tile = par1World.getBlockTileEntity(x, y, z);

        if (tile != null)
        {
            TankTileEntity tankTile = (TankTileEntity) tile;

            if (tankTile.getControllerCoords() != null || tankTile.getControllerCoords()[0] != -1)
            {
                TileEntity controller = par1World.getBlockTileEntity(tankTile.getControllerCoords()[0],
                                        tankTile.getControllerCoords()[1], tankTile.getControllerCoords()[2]);

                if (controller != null && controller instanceof ControllerTileEntity)
                {
                    ControllerTileEntity controllerTile = (ControllerTileEntity) controller;
                    int[] loc = { x, y, z };

                    if (!controllerTile.getNeighbors().isEmpty())
                        for (int i = 0; i < controllerTile.getNeighbors().size(); i++)
                            if (Arrays.equals(loc, controllerTile.getNeighbors().get(i)))
                            {
                                controllerTile.getNeighbors().remove(i);
                            }
                }
            }
        }

        super.breakBlock(par1World, x, y, z, par5, par6);
    }

    @Override
    public void onBlockClicked(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer)
    {
        ItemStack heldItem = par5EntityPlayer.inventory.getCurrentItem();
        TankTileEntity tank = (TankTileEntity) par1World.getBlockTileEntity(x, y, z);

        if (par5EntityPlayer.isSneaking())
        {
            if (heldItem != null && heldItem.itemID == Item.dyePowder.itemID)
            {
                tank.setDye(heldItem.getItemDamage());
            }
            else if (heldItem != null && Block.blocksList[heldItem.itemID] != null)
            {
                tank.setCamo(heldItem.itemID, heldItem.getItemDamage());
            }
            else if (heldItem == null)
            {
                tank.setCamo(-1, 0);
                tank.setDye(-1);
            }

            par1World.markBlockForUpdate(x, y, z);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ)
    {
        TankTileEntity tank = (TankTileEntity) world.getBlockTileEntity(x, y, z);
        ItemStack heldItem = player.inventory.getCurrentItem();
        /*
        if (heldItem == null || heldItem.itemID != blockID) {
        	if (tank != null && tank.hasController())
        		player.openGui(DynamicLiquidTanksCore.instance, 0, world, x, y, z);
        	return true;
        }
        return false;*/

        if (tank == null || !tank.hasController())
        {
            return false;
        }

        ControllerTileEntity conTE = (ControllerTileEntity) tank.getControllerTE();
        
        if (conTE == null)
        	return false;
        
        if (heldItem != null && heldItem.itemID == Item.potion.itemID)
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
        else if (heldItem != null && FluidContainerRegistry.isContainer(heldItem))
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
        }

        if (!FluidContainerRegistry.isContainer(heldItem) && heldItem == null || heldItem.itemID != blockID)
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

    /*@Override
    public int getLightValue (IBlockAccess world, int x, int y, int z) {
    	TileEntity logic = world.getBlockTileEntity(x, y, z);
    	if (logic != null && logic instanceof TankTileEntity)
    		if (((TankTileEntity) logic).hasController())
    			return ((TankTileEntity) logic).getControllerTE().getBrightness();
    	return 0;
    }*/

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
        return ClientProxy.tankRender;
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

    @Override
    @SideOnly(value = Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int dir)
    {
        TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile != null && tile instanceof TankTileEntity)
        {
            TankTileEntity tank = ((TankTileEntity) tile);

            try
            {
                switch (dir)
                {
                    case 0:
                        return tank.hasCamo() ? ConnectedTexturesHelper.blockIcon(tank.getCamo()[0], dir, tank.getCamo()[1])
                               : ConnectedTexturesHelper.getBlocksAroundToBo(world, x, y, z, dir, this.blockID);

                    case 1:
                        return tank.hasCamo() ? ConnectedTexturesHelper.blockIcon(tank.getCamo()[0], dir, tank.getCamo()[1])
                               : ConnectedTexturesHelper.getBlocksAroundToBo(world, x, y, z, dir, this.blockID);

                    case 2:
                        return tank.hasCamo() ? ConnectedTexturesHelper.blockIcon(tank.getCamo()[0], dir, tank.getCamo()[1])
                               : ConnectedTexturesHelper.getBlocksAroundNoSo(world, x, y, z, dir, this.blockID);

                    case 3:
                        return tank.hasCamo() ? ConnectedTexturesHelper.blockIcon(tank.getCamo()[0], dir, tank.getCamo()[1])
                               : ConnectedTexturesHelper.getBlocksAroundNoSo(world, x, y, z, dir, this.blockID);

                    case 4:
                        return tank.hasCamo() ? ConnectedTexturesHelper.blockIcon(tank.getCamo()[0], dir, tank.getCamo()[1])
                               : ConnectedTexturesHelper.getBlocksAroundEaWe(world, x, y, z, dir, this.blockID);

                    case 5:
                        return tank.hasCamo() ? ConnectedTexturesHelper.blockIcon(tank.getCamo()[0], dir, tank.getCamo()[1])
                               : ConnectedTexturesHelper.getBlocksAroundEaWe(world, x, y, z, dir, this.blockID);
                }
            }
            catch (Exception e)
            {
                return blockIcon;
            }
        }

        return blockIcon;
    }

    @Override
    @SideOnly(value = Side.CLIENT)
    public final int colorMultiplier(IBlockAccess world, int x, int y, int z)
    {
        TileEntity dye = world.getBlockTileEntity(x, y, z);

        if (dye instanceof TankTileEntity)
        {
            TankTileEntity casted = ((TankTileEntity) dye);

            if (casted.getDye() != -1)
            {
                return ItemDye.dyeColors[casted.getDye()];
            }
            else
            {
                try
                {
                    return Block.blocksList[casted.getCamo()[0]].colorMultiplier(world, x, y, z);
                }
                catch (Throwable t)
                {
                    return super.colorMultiplier(world, x, y, z);
                }
            }
        }

        return super.colorMultiplier(world, x, y, z);
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        ConnectedTexturesHelper.leftRight = iconRegister.registerIcon("dynamictanks:leftRight");
        ConnectedTexturesHelper.upAndDown = iconRegister.registerIcon("dynamictanks:topBottom");
        ConnectedTexturesHelper.left = iconRegister.registerIcon("dynamictanks:left");
        ConnectedTexturesHelper.right = iconRegister.registerIcon("dynamictanks:right");
        ConnectedTexturesHelper.top = iconRegister.registerIcon("dynamictanks:top");
        ConnectedTexturesHelper.bottom = iconRegister.registerIcon("dynamictanks:bottom");
        ConnectedTexturesHelper.leftBottom = iconRegister.registerIcon("dynamictanks:leftBottom");
        ConnectedTexturesHelper.leftTop = iconRegister.registerIcon("dynamictanks:leftTop");
        ConnectedTexturesHelper.rightBottom = iconRegister.registerIcon("dynamictanks:rightBottom");
        ConnectedTexturesHelper.rightTop = iconRegister.registerIcon("dynamictanks:rightTop");
        ConnectedTexturesHelper.onlyTop = iconRegister.registerIcon("dynamictanks:onlyTop");
        ConnectedTexturesHelper.onlyBottom = iconRegister.registerIcon("dynamictanks:onlyBottom");
        ConnectedTexturesHelper.onlyRight = iconRegister.registerIcon("dynamictanks:onlyRight");
        ConnectedTexturesHelper.onlyLeft = iconRegister.registerIcon("dynamictanks:onlyLeft");
        ConnectedTexturesHelper.empty = iconRegister.registerIcon("dynamictanks:empty");
        this.blockIcon = iconRegister.registerIcon("dynamictanks:single");
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        int blockID = world.getBlockId(x, y, z);
        TileEntity tank = world.getBlockTileEntity(x, y, z);

        if (tank instanceof TankTileEntity)
            if (((TankTileEntity) tank).hasCamo())
            {
                return true;
            }

        return blockID == this.blockID ? false : super.shouldSideBeRendered(world, x, y, z, side);
        //return ConnectedTexturesHelper.shouldSideRender(world, x, y, z, side, this);
    }

    public boolean shouldSideRender(IBlockAccess world, int x, int y, int z, int side)
    {
        int blockID = world.getBlockId(x, y, z);
        return blockID == this.blockID ? false : super.shouldSideBeRendered(world, x, y, z, side);
    }
}
