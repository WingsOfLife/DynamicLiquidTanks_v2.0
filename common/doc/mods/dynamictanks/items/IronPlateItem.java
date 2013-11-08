package doc.mods.dynamictanks.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class IronPlateItem extends Item {

	public IronPlateItem (int itemId) {
		super(itemId);
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ("dynamictanks.items.ironMass");
	}
	
	@Override
    public void registerIcons(IconRegister register) {
            itemIcon = register.registerIcon("dynamictanks:ironPlate");
    }
	
	@Override
    public Icon getIconFromDamage(int i) {
            return itemIcon;
    }
	
}
