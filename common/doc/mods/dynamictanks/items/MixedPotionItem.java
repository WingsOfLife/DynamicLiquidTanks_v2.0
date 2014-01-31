package doc.mods.dynamictanks.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.helpers.PotionEffectHelper;

public class MixedPotionItem extends Item
{
    public MixedPotionItem(int par1)
    {
        super(par1);
        setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        ArrayList<List> effectsList = new ArrayList<List>();

        if (par1ItemStack.stackTagCompound != null)
        {
            if (par1ItemStack.stackTagCompound != null)
                for (int effects : par1ItemStack.stackTagCompound.getIntArray("PotionEffects"))
                {
                    effectsList.add(PotionHelper.getPotionEffects(effects, false));
                }

            if (!effectsList.isEmpty())
                for (List types : effectsList)
                {
                    if (types != null)
                    {
                        for (Object pEff : types)
                            par3List.add(StatCollector.translateToLocal(((PotionEffect) pEff).getEffectName().trim()) +
                                         " :: " + Potion.getDurationString(((PotionEffect) pEff)) + " Mins.");
                    }
                }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return ("dynamictanks.items.mixedPotion");
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        ArrayList<List> effectsList = new ArrayList<List>();

        if (par1ItemStack.stackTagCompound != null)
        {
            if (par1ItemStack.stackTagCompound != null)
                for (int effects : par1ItemStack.stackTagCompound.getIntArray("PotionEffects"))
                {
                    effectsList.add(PotionHelper.getPotionEffects(effects, false));
                }

            if (!effectsList.isEmpty())
                for (List types : effectsList)
                {
                    if (types != null)
                    {
                        for (Object pEff : types)
                        {
                            par3EntityPlayer.addPotionEffect(((PotionEffect) pEff));
                        }
                    }
                }
        }

        par1ItemStack.stackSize = par1ItemStack.stackSize - 1;
        return par1ItemStack;
    }

    public int effectCount(ItemStack itemStack)
    {
        ArrayList<List> effectsList = new ArrayList<List>();

        if (itemStack.stackTagCompound != null)
        {
            if (itemStack.stackTagCompound != null)
                for (int effects : itemStack.stackTagCompound.getIntArray("PotionEffects"))
                {
                    effectsList.add(PotionHelper.getPotionEffects(effects, false));
                }
        }

        return effectsList.size();
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 24 * effectCount(par1ItemStack);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    @Override
    public void registerIcons(IconRegister register)
    {
        itemIcon = register.registerIcon("dynamictanks:potion");
    }
}
