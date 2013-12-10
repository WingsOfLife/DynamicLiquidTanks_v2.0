package doc.mods.dynamictanks.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;

public class BlockHauntedDirt extends Block
{
    @SideOnly(Side.CLIENT)
    Icon iconGrassTop;
    @SideOnly(Side.CLIENT)
    Icon iconGrassSide;
    @SideOnly(Side.CLIENT)
    Icon iconGrassBottom;

    public BlockHauntedDirt(int par1)
    {
        super(par1, Material.grass);
        setHardness(0.5F);
        setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
        setUnlocalizedName("dynamictanks.blocks.blockHauntedDirt");
        setStepSound(soundGrassFootstep);
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.iconGrassTop : (par1 == 0 ? iconGrassBottom : this.blockIcon);
    }

    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 1)
        {
            return iconGrassTop;
        }
        else if (par5 == 0)
        {
            return iconGrassBottom;
        }
        else
        {
            Material material = par1IBlockAccess.getBlockMaterial(par2, par3 + 1, par4);
            return material != Material.snow && material != Material.craftedSnow ? blockIcon : iconGrassSide;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("dynamictanks:creepyDirt_Side");
        this.iconGrassTop = par1IconRegister.registerIcon("dynamictanks:creepyDirt_Top");
        this.iconGrassSide = par1IconRegister.registerIcon("dynamictanks:creepyDirt_Side");
        this.iconGrassBottom = par1IconRegister.registerIcon("dynamictanks:creepyDirt");
    }
}
