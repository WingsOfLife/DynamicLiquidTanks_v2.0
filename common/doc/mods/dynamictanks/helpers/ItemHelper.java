package doc.mods.dynamictanks.helpers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHelper {

	public static void spawnItem(ItemStack itemStack, World worldObj, int x, int y, int z) {
		int rndX = NumberHelper.inRange(x, x + 3);
		int rndY = NumberHelper.inRange(y, y + 3);
		int rndZ = NumberHelper.inRange(z, z + 3);
		
		while (!worldObj.isAirBlock(rndX, rndY, rndZ)) {
			rndX = NumberHelper.inRange(x, x + 3); 
			rndY = NumberHelper.inRange(y, y + 3);
			rndZ = NumberHelper.inRange(z, z + 3);
		}
		
		EntityItem entityitem = new EntityItem(worldObj, rndX, rndY, rndZ, itemStack);
        entityitem.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem);
	}
	
}
