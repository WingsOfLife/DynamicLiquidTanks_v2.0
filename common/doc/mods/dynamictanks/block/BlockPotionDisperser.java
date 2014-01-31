package doc.mods.dynamictanks.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.items.ItemManager;
import doc.mods.dynamictanks.tileentity.PotionDisperserTileEntity;

public class BlockPotionDisperser extends BlockContainer
{
    Icon topBottom;

    protected BlockPotionDisperser(int par1)
    {
        super(par1, Material.iron);
        setHardness(1.0f);
        setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
        setUnlocalizedName("dynamictanks.blocks.blockPotionDisperser");
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new PotionDisperserTileEntity();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ)
    {
        ItemStack heldItem = player.inventory.getCurrentItem();
        PotionDisperserTileEntity potionDTE = (PotionDisperserTileEntity) world.getBlockTileEntity(x, y, z);

        if (potionDTE.getStackInSlot(0) == null && heldItem != null && (heldItem.itemID == Item.potion.itemID || heldItem.itemID == ItemManager.mixedPotion.itemID))
        {
            potionDTE.setInventorySlotContents(0, heldItem);
            heldItem = heldItem.splitStack(1);
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconReg)
    {
        blockIcon = iconReg.registerIcon("dynamictanks:" + "potionDisperser");
        topBottom = iconReg.registerIcon("dynamictanks:" + "potionDisperserTB");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        if (side == 0 || side == 1)
        {
            return topBottom;
        }

        return blockIcon;
    }
}
