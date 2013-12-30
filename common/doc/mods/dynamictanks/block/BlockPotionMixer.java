package doc.mods.dynamictanks.block;

import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.tileentity.PotionMixerTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPotionMixer extends BlockContainer {

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
	
}
