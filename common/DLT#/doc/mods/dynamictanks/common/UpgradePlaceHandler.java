package doc.mods.dynamictanks.common;

import doc.mods.dynamictanks.block.BlockManager;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class UpgradePlaceHandler {

	/*@ForgeSubscribe
	public void cancelAllBlockPlaced(PlayerInteractEvent event) {
		if(event.action == event.action.RIGHT_CLICK_BLOCK 
				&& event.entityPlayer.inventory.getStackInSlot(event.entityPlayer.inventory.currentItem) != null &&
				event.entityPlayer.inventory.getStackInSlot(event.entityPlayer.inventory.currentItem).itemID 
				== BlockManager.BlockUpgrade.blockID) {
		
			boolean hasUpgrade = false;
			
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				
				int xFind = event.x + dir.offsetX;
				int yFind = event.y + dir.offsetY;
				int zFind = event.z + dir.offsetZ;
				
				if (event.face == 0)
		            --yFind;

		        if (event.face == 1)
		            ++yFind;

		        if (event.face == 2)
		            --zFind;

		        if (event.face == 3)
		            ++zFind;

		        if (event.face == 4)
		            --xFind;

		        if (event.face == 5)
		            ++xFind;
				
				if (event.entity.worldObj.getBlockId(xFind, yFind, zFind) == BlockManager.BlockTankController.blockID)
					hasUpgrade = true;
			}
			
			if (!hasUpgrade)
				event.setCanceled(true);
		}
	}*/
}
