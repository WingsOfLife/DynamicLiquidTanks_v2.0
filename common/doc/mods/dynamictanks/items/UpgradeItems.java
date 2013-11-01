package doc.mods.dynamictanks.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgradeItems extends Item {

	Icon[] icons = new Icon[2];
	
	public static String[] names = { "Capacity Upgrade", "Storage Upgrade" };
	public static String[] info = { "Increase the capacity of the tanks.", 
		"Increase the number of liquids a tank can hold." };
	
	public UpgradeItems(int par1) {
		super(par1);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(info[stack.getItemDamage()]);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ("dynamictanks.items." + names[stack.getItemDamage()]);
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemId, CreativeTabs tab, List list) {
		for (int i = 0; i < names.length; i++) {
			list.add(new ItemStack(itemId, 1, i));
		}
	}
	
	@Override
    public void registerIcons(IconRegister register) {
            icons[0] = register.registerIcon("dynamictanks:blaze_chip");
            icons[1] = register.registerIcon("dynamictanks:pearl_chip");
    }
    
    @Override
    public Icon getIconFromDamage(int i) {
            return icons[i];
    }

}
