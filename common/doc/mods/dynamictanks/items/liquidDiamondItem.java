package doc.mods.dynamictanks.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class liquidDiamondItem extends Item {

	private int ticksExisted = 0;

	public liquidDiamondItem (int itemId) {
		super(itemId);
		setMaxDamage(7);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Life left: " + (stack.getMaxDamage() - stack.getItemDamage()) + " seconds");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ("dynamictanks.items.softenedDiamond");
	}

	@Override
	public void registerIcons(IconRegister register) {
		itemIcon = register.registerIcon("dynamictanks:liquidDiamond");
	}

	@Override
	public Icon getIconFromDamage(int i) {
		return itemIcon;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		ticksExisted++;
		
		if (ticksExisted >= 20) {
			par1ItemStack.damageItem(1, (EntityLivingBase) par3Entity);
			if ((par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage()) == 0) {
				((EntityPlayer) par3Entity).inventory.consumeInventoryItem(par1ItemStack.itemID);
				((EntityPlayer) par3Entity).inventory.addItemStackToInventory(new ItemStack(Item.diamond));
			}
			ticksExisted = 0;
		}
	}
	
}
