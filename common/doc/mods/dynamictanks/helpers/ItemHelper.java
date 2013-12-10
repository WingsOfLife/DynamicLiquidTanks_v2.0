package doc.mods.dynamictanks.helpers;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class ItemHelper
{
    public static void spawnItem(ItemStack itemStack, World worldObj, int x, int y, int z)
    {
        Random rand = new Random();
        float rx = rand.nextFloat() * 0.8F + 0.1F;
        float ry = rand.nextFloat() * 0.8F + 0.1F;
        float rz = rand.nextFloat() * 0.8F + 0.1F;
        EntityItem entityItem = new EntityItem(worldObj,
                                               x + rx, y + ry, z + rz,
                                               new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage()));

        if (itemStack.hasTagCompound())
        {
            entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
        }

        float factor = 0.05F;
        entityItem.motionX = rand.nextGaussian() * factor;
        entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
        entityItem.motionZ = rand.nextGaussian() * factor;
        worldObj.spawnEntityInWorld(entityItem);
        itemStack.stackSize = 0;
    }

    public static void dropItem(int itemID, int meta, World world, int x, int y, int z)
    {
        EntityItem el = new EntityItem(world, x, y, z, new ItemStack(itemID, 1, meta));
        world.spawnEntityInWorld(el);
    }

    public static boolean removeBlockByPlayer(World world, int x, int y, int z)
    {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            ItemStack itemStack = new ItemStack(BlockManager.BlockTankController);
            ControllerTileEntity controller = (ControllerTileEntity) world.getBlockTileEntity(x, y, z);

            if (controller != null)
            {
                if (FluidHelper.hasLiquid(controller))
                {
                    itemStack.setItemDamage(Math.round(controller.getAllLiquids().get(0).getFluidAmount() * 16) + controller.getAllLiquids().size() * 16);
                }

                NBTTagCompound nbt = new NBTTagCompound();
                controller.writeToNBT(nbt);
                itemStack.setTagCompound(nbt);
            }

            float f = 0.7F;
            double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, itemStack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }

        return world.setBlockToAir(x, y, z);
    }

    public static ItemStack removeSingleItem(ItemStack is)
    {
        is.stackSize--;
        return is;
    }
}
