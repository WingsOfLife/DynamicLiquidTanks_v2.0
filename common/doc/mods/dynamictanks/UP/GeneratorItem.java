package doc.mods.dynamictanks.UP;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class GeneratorItem extends ItemBlock {

	public GeneratorItem(int par1) {
		super(par1);
	}
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(1, EnumChatFormatting.RESET + "Hold " + EnumChatFormatting.GOLD + EnumChatFormatting.ITALIC + "SHIFT" + EnumChatFormatting.RESET + " for information.");
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			list.remove(1);
			
			list.add("This generator can convert Fluid -> Power or Power -> Fluid");
			list.add("Apply a redstone signal to extract power from the block.");
			list.add("Remove a redstone signal to start the creation of fluid");
			list.add("One must provide power for the creation of fluid.");
			list.add("");
			list.add(EnumChatFormatting.YELLOW + "Output: 320 RF/t" + EnumChatFormatting.GRAY + " :: " + EnumChatFormatting.BLUE + "Input: 32000 RF/t");
		}
	}

}
