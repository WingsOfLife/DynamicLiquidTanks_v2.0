package doc.mods.dynamictanks.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.client.ClientProxy;
 

public class BlockPiping extends Block {
	
	public static final int SEARCHED = 1;
	
	
	BlockPiping(int blockID) {

		super(blockID, Material.iron);
		setCreativeTab(DynamicLiquidTanksCore.tabDynamicTanks);
		setBlockBounds(0.30f, 0.30f, 0.30f, 0.70f, 0.70f, 0.70f);
		setUnlocalizedName("dynamictanks.block.blockPiping");
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {

		this.blockIcon = iconRegister.registerIcon("dynamictanks:cable");
	}

	@Override
	public int getRenderType() {

		return ClientProxy.pipeRender;
	}

	@Override
	public boolean renderAsNormalBlock() {

		return false;
	}

	@Override
	public boolean isOpaqueCube() {

		return false;
	}
	
	public static void setSearched(World world, int x, int y, int z) {
		
		world.setBlockMetadataWithNotify(x, y, z, SEARCHED, 3);
	}
}
