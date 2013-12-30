package doc.mods.dynamictanks.UP;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemFPC_MJ extends ItemBlock {
	
	public ItemFPC_MJ(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (itemstack.getItemDamage() == 0)
			list.add(EnumChatFormatting.YELLOW + "Power to Fluid Generator");
		else {
			list.add(EnumChatFormatting.YELLOW + "Fluid to Power Generator");
			list.add(EnumChatFormatting.RESET + "Generates Minecraft Joules");
		}
	}
}
