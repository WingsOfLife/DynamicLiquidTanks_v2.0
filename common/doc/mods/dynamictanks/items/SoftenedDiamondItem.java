package doc.mods.dynamictanks.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class SoftenedDiamondItem extends Item {

	public SoftenedDiamondItem (int itemId) {
		super(itemId);
		setMaxDamage(5);
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ("dynamictanks.items.softDiamond");
	}

	@Override
	public void registerIcons(IconRegister register) {
		itemIcon = register.registerIcon("dynamictanks:diamond");
	}

	@Override
	public Icon getIconFromDamage(int i) {
		return itemIcon;
	}

	
}
