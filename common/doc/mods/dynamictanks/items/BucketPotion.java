package doc.mods.dynamictanks.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.Fluids.tileentity.ClensingTileEntity;
import doc.mods.dynamictanks.Fluids.tileentity.PotionTileEntity;
import doc.mods.dynamictanks.helpers.CPotionHelper;

public class BucketPotion extends ItemBucket
{
    public static String[] names =
    {
        "Regeneration", "Swiftness", "Fire Resistance",
        "Poison", "Instant Health", "Night Vision",
        "Weakness", "Strength", "Slowness",
        "Harming", "Water Breathing", "Invisibility",
        "Cleansing", "TNT"
    };

    private final float ticksPerSec = CPotionHelper.ticksPerSec;
    private final float maxExistance = CPotionHelper.maxExistance;
    private Icon cleansing;
    private Icon nitro;

    public BucketPotion(int itemID)
    {
        super(itemID, 0);
        setContainerItem(Item.bucketEmpty);
        setHasSubtypes(true);
        setMaxStackSize(1);
        setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
    {
        if (stack.stackTagCompound == null)
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        list.add("Stability: " + (100 - (int)((stack.stackTagCompound.getFloat("lengthExisted") / maxExistance) * 100)) + "%");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return ("dynamictanks.items.bucket." + names[stack.getItemDamage()]);
    }

    @Override
    public void registerIcons(IconRegister register)
    {
        itemIcon = register.registerIcon("dynamictanks:potionBucket");
        cleansing = register.registerIcon("dynamictanks:cleansingBucket");
        nitro =  register.registerIcon("dynamictanks:nitroBucket");
    }

    @Override
    public Icon getIconFromDamage(int i)
    {
        if (i == 12)
        {
            return cleansing;
        }
        else if (i == 13)
        {
            return nitro;
        }

        return itemIcon;
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        float ticksExisted = 0;

        if (par1ItemStack.getItemDamage() == 12 || par1ItemStack.getItemDamage() == 13)
        {
            return;
        }

        if (par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("lengthExisted"))
            if ((100 - (int)((par1ItemStack.stackTagCompound.getFloat("lengthExisted") / maxExistance) * 10)) <= -100)
            {
                return;
            }

        if (par1ItemStack.stackTagCompound == null)
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
            par1ItemStack.stackTagCompound.setFloat("lengthExisted", 0);
            ticksExisted = 0;
        }

        if (par1ItemStack.stackTagCompound.hasKey("lengthExisted"))
        {
            ticksExisted = par1ItemStack.stackTagCompound.getFloat("lengthExisted");
            ticksExisted++;
        }

        par1ItemStack.stackTagCompound.setFloat("lengthExisted", ticksExisted);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        MovingObjectPosition position = this.getMovingObjectPositionFromPlayer(world, player, false);

        if (position == null)
        {
            return stack;
        }

        int clickX = position.blockX;
        int clickY = position.blockY;
        int clickZ = position.blockZ;

        if (!world.canMineBlock(player, clickX, clickY, clickZ))
        {
            return stack;
        }

        if (position.sideHit == 0)
        {
            --clickY;
        }

        if (position.sideHit == 1)
        {
            ++clickY;
        }

        if (position.sideHit == 2)
        {
            --clickZ;
        }

        if (position.sideHit == 3)
        {
            ++clickZ;
        }

        if (position.sideHit == 4)
        {
            --clickX;
        }

        if (position.sideHit == 5)
        {
            ++clickX;
        }

        if (!player.canPlayerEdit(clickX, clickY, clickZ, position.sideHit, stack))
        {
            return stack;
        }

        if (tryPlaceContainedLiquid(world, stack, clickX, clickY, clickZ, stack.getItemDamage()) && !player.capabilities.isCreativeMode)
        {
            return new ItemStack(Item.bucketEmpty);
        }

        return stack;
    }

    public boolean tryPlaceContainedLiquid(World world, ItemStack stack, int clickX, int clickY, int clickZ, int type)
    {
        if (!world.isAirBlock(clickX, clickY, clickZ) && world.getBlockMaterial(clickX, clickY, clickZ).isSolid())
        {
            return false;
        }
        else
        {
            int id = 0;
            int metadata = 0;
            world.setBlock(clickX, clickY, clickZ, FluidManager.blockType.get(type).blockID, metadata, 3);

            if (stack.stackTagCompound != null && world.getBlockTileEntity(clickX, clickY, clickZ) instanceof PotionTileEntity)
            {
                PotionTileEntity potionTile = (PotionTileEntity) world.getBlockTileEntity(clickX, clickY, clickZ);

                if (stack.stackTagCompound.hasKey("lengthExisted"))
                {
                    potionTile.setExistance(stack.stackTagCompound.getFloat("lengthExisted"));
                }
            }

            if (stack.stackTagCompound != null && world.getBlockTileEntity(clickX, clickY, clickZ) instanceof ClensingTileEntity)
            {
                ClensingTileEntity clenseTile = (ClensingTileEntity) world.getBlockTileEntity(clickX, clickY, clickZ);

                if (stack.stackTagCompound.hasKey("damageHealed"))
                {
                    clenseTile.setHealed(stack.stackTagCompound.getFloat("damageHealed"));
                }
            }

            return true;
        }
    }

    @Override
    public void getSubItems(int id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < 14; i++)
        {
            list.add(new ItemStack(id, 1, i));
        }
    }
}
