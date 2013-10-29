package doc.mods.dynamictanks.helpers;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemHelper {

	public static void spawnItem(ItemStack itemStack, World worldObj, int x, int y, int z) {
		Random rand = new Random();
		
		float rx = rand.nextFloat() * 0.8F + 0.1F;
        float ry = rand.nextFloat() * 0.8F + 0.1F;
        float rz = rand.nextFloat() * 0.8F + 0.1F;

        EntityItem entityItem = new EntityItem(worldObj,
                        x + rx, y + ry, z + rz,
                        new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage()));

        if (itemStack.hasTagCompound()) {
                entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
        }

        float factor = 0.05F;
        entityItem.motionX = rand.nextGaussian() * factor;
        entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
        entityItem.motionZ = rand.nextGaussian() * factor;
        worldObj.spawnEntityInWorld(entityItem);
        itemStack.stackSize = 0;
	}
	
}
