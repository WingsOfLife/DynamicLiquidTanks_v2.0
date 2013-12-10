package doc.mods.dynamictanks.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import doc.mods.dynamictanks.DynamicLiquidTanksCore;

public class HammerItem extends Item
{
    private int ticksExisted = 0;

    public HammerItem(int itemId)
    {
        super(itemId);
        setMaxDamage(16);
        setMaxStackSize(1);
        setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
    {
        list.add("Uses: " + (stack.getMaxDamage() - stack.getItemDamage()));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return ("dynamictanks.items.hammer");
    }

    @Override
    public void registerIcons(IconRegister register)
    {
        itemIcon = register.registerIcon("dynamictanks:hammer");
    }

    @Override
    public Icon getIconFromDamage(int i)
    {
        return itemIcon;
    }
}
