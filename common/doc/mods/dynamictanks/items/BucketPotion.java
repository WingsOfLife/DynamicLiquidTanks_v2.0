package doc.mods.dynamictanks.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.Fluids.FluidManager;

public class BucketPotion extends ItemBucket {

	public static String[] names = {
		"Regeneration", "Swiftness", "Fire Resistance",
		"Poison", "Instant Health", "Night Vision",
		"Weakness", "Strength", "Slowness",
		 "Harming", "Water Breathing", "Invisibility"
	};
	
	public BucketPotion(int itemID) {
		super(itemID, 0);
		setContainerItem(Item.bucketEmpty);
		this.setHasSubtypes(true);
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		//list.add("Uses: " + (stack.getMaxDamage() - stack.getItemDamage()));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ("dynamictanks.items.bucket." + names[stack.getItemDamage()]);
	}

	@Override
	public void registerIcons(IconRegister register) {
		itemIcon = register.registerIcon("dynamictanks:potionBucket");
	}

	@Override
	public Icon getIconFromDamage(int i) {
		return itemIcon;
	}	

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		MovingObjectPosition position = this.getMovingObjectPositionFromPlayer(world, player, false);
		if (position == null)
			return stack;
		
		int clickX = position.blockX;
        int clickY = position.blockY;
        int clickZ = position.blockZ;
        if (!world.canMineBlock(player, clickX, clickY, clickZ))
            return stack;

        if (position.sideHit == 0)
            --clickY;

        if (position.sideHit == 1)
            ++clickY;

        if (position.sideHit == 2)
            --clickZ;

        if (position.sideHit == 3)
            ++clickZ;

        if (position.sideHit == 4)
            --clickX;

        if (position.sideHit == 5)
            ++clickX;

        if (!player.canPlayerEdit(clickX, clickY, clickZ, position.sideHit, stack))
            return stack;        
        
		if (tryPlaceContainedLiquid(world, clickX, clickY, clickZ, stack.getItemDamage()) && !player.capabilities.isCreativeMode)
			return new ItemStack(Item.bucketEmpty);
		return stack;
	}

	public boolean tryPlaceContainedLiquid(World world, int clickX, int clickY, int clickZ, int type)
	{
		if (!world.isAirBlock(clickX, clickY, clickZ) && world.getBlockMaterial(clickX, clickY, clickZ).isSolid())
			return false;
		else {
			int id = 0;
			int metadata = 0;
			world.setBlock(clickX, clickY, clickZ, FluidManager.blockType.get(type).blockID, metadata, 3);
			return true;
		}
	}
	
	@Override
    public void getSubItems(int id, CreativeTabs tab, List list) {
        for (int i = 0; i < 12; i++)
            list.add(new ItemStack(id, 1, i));
    }
}
