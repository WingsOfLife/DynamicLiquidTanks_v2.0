package doc.mods.dynamictanks.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.Fluids.tileentity.ClensingTileEntity;
import doc.mods.dynamictanks.Fluids.tileentity.PotionTileEntity;
import doc.mods.dynamictanks.apiMe.TickingBucket;
import doc.mods.dynamictanks.common.BucketHandler;
import doc.mods.dynamictanks.helpers.CPotionHelper;

public class ChalicePotion extends TickingBucket
{
    public static String[] names =
    {
        "Empty", "Regeneration", "Swiftness", "Fire Resistance",
        "Poison", "Instant Health", "Night Vision",
        "Weakness", "Strength", "Slowness",
        "Harming", "Water Breathing", "Invisibility",
        "Cleansing"
    };

    private final float ticksPerSec  = CPotionHelper.ticksPerSec;
    private final float maxExistance = CPotionHelper.maxExistance;

    private Icon filled;

    public ChalicePotion(int itemID)
    {
        super(itemID);
        setHasSubtypes(true);
        setContainerItem(this);
        setMaxStackSize(1);
        setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
        
        addMonitoredElement("damage",        "Life Force", 1200, tickingType.DECREASE  );
        addMonitoredElement("lengthExisted", "Stability" , 1200, tickingType.INCREASE  );
        addMonitoredElement("damageHealed",  ""          , 1200, tickingType.NOMOVEMENT);
        
        addIgnoredDamage(0);
        addDependantVar("lengthExisted", "damage");
        
        cancelTooltipDisplay("damageHealed");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return ("dynamictanks.items.chalice." + names[stack.getItemDamage()]);
    }

    @Override
    public void registerIcons(IconRegister register)
    {
        itemIcon = register.registerIcon("dynamictanks:potionChalice");
        filled = register.registerIcon("dynamictanks:potionChalice_Filled");
    }

    @Override
    public Icon getIconFromDamage(int i)
    {
        if (i == 0)
        {
            return itemIcon;
        }

        return filled;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        MovingObjectPosition position = this.getMovingObjectPositionFromPlayer(world, player, true);
        float damage = maxExistance;

        if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("damage"))
        {
            damage = stack.stackTagCompound.getInteger("damage");
        }

        if (stack.getItemDamage() == 0)
        {
            if (position == null)
            {
                return stack;
            }
            else
            {
                FillBucketEvent event = new FillBucketEvent(player, stack, world, position);
                int id = world.getBlockId(event.target.blockX, event.target.blockY, event.target.blockZ);
                event.result = BucketHandler.INSTANCE.chalice.get(Block.blocksList[id]);

                if (event.result != null)
                {
                    event.result = event.result.copy();
                    event.setResult(Event.Result.ALLOW);
                }
                else
                {
                    event.setResult(Event.Result.DENY);
                }

                if (event.getResult() == Event.Result.ALLOW && event.result != null)
                {
                    TileEntity potionTile = world.getBlockTileEntity(event.target.blockX, event.target.blockY, event.target.blockZ);

                    if (event.result.stackTagCompound == null)
                    {
                        event.result.setTagCompound(new NBTTagCompound());
                    }

                    if (potionTile instanceof PotionTileEntity)
                    {
                        event.result.stackTagCompound.setInteger("lengthExisted", (int) ((PotionTileEntity) potionTile).getExistance());
                    }

                    if (potionTile instanceof ClensingTileEntity)
                    {
                        event.result.stackTagCompound.setInteger("damageHealed", (int) ((ClensingTileEntity) potionTile).getHealed());
                    }

                    event.result.stackTagCompound.setInteger("damage", (int) damage);
                    world.setBlock(event.target.blockX, event.target.blockY, event.target.blockZ, 0);

                    if (player.capabilities.isCreativeMode)
                    {
                        return stack;
                    }

                    if (--stack.stackSize <= 0)
                    {
                        return event.result;
                    }

                    if (!player.inventory.addItemStackToInventory(event.result))
                    {
                        player.dropPlayerItem(event.result);
                    }

                    return stack;
                }
            }

            return stack;
        }

        position = this.getMovingObjectPositionFromPlayer(world, player, false);

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

        if (tryPlaceContainedLiquid(world, stack, clickX, clickY, clickZ, (stack.getItemDamage() - 1)) && !player.capabilities.isCreativeMode)
        {
            ItemStack emptyChalice = null;//new ItemStack(ItemManager.chalice);
            emptyChalice.setTagCompound(new NBTTagCompound());
            emptyChalice.stackTagCompound.setInteger("damage", (int) damage);
            return emptyChalice;
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
                    potionTile.setExistance(stack.stackTagCompound.getInteger("lengthExisted"));
                }
            }

            if (stack.stackTagCompound != null && world.getBlockTileEntity(clickX, clickY, clickZ) instanceof ClensingTileEntity)
            {
                ClensingTileEntity potionTile = (ClensingTileEntity) world.getBlockTileEntity(clickX, clickY, clickZ);

                if (stack.stackTagCompound.hasKey("damageHealed"))
                {
                    potionTile.setHealed(stack.stackTagCompound.getInteger("damageHealed"));
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
