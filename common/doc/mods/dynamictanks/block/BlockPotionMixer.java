package doc.mods.dynamictanks.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.client.ClientProxy;
import doc.mods.dynamictanks.tileentity.PotionMixerTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockPotionMixer extends BlockContainer {

	Icon topBottom;
	
	protected BlockPotionMixer(int par1) {
		super(par1, Material.iron);
		setHardness(1.0f);
        setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
        setUnlocalizedName("dynamictanks.blocks.blockPotionMixer");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new PotionMixerTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ)
    {
		player.openGui(DynamicLiquidTanksCore.instance, 0, world, x, y, z);
        return true;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconReg)
	{
		blockIcon = iconReg.registerIcon("dynamictanks:" + "potionMixer");
		topBottom = iconReg.registerIcon("dynamictanks:" + "potionDisperserTB");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		if (side == 0 || side == 1)
			return topBottom;
		return blockIcon;
	}
}
