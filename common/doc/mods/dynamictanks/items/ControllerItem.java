package doc.mods.dynamictanks.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidTank;
import doc.mods.dynamictanks.helpers.StringHelper;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class ControllerItem extends ItemBlock
{
    public ControllerItem(int par1)
    {
        super(par1);
    }

    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
    {
        ControllerTileEntity controller = new ControllerTileEntity();

        if (itemstack.stackTagCompound != null)
        {
            Object name = list.get(0);
            list.set(0, "" + name + EnumChatFormatting.YELLOW + " (Pre-Filled)");
            list.add(1, EnumChatFormatting.RESET + "Hold " + EnumChatFormatting.GOLD + EnumChatFormatting.ITALIC + "SHIFT" + EnumChatFormatting.RESET + " for information.");
        }

        if (itemstack.stackTagCompound != null && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
            list.remove(1);
            controller.readFromNBT(itemstack.stackTagCompound);

            for (FluidTank fT : controller.getAllLiquids())
            {
                list.add("" + EnumChatFormatting.RESET + StringHelper.Cap(fT.getFluid().getFluid().getName()));
                list.add(EnumChatFormatting.GRAY + " -" + StringHelper.parseCommas(fT.getFluidAmount() + "", "", " mB"));
            }
        }
    }
}
